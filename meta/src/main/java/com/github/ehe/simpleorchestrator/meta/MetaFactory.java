/*
 *
 *  * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *  *
 *  * This software is licensed under
 *  *
 *  * MIT license
 *  *
 *
 */

package com.github.ehe.simpleorchestrator.meta;

import com.github.ehe.simpleorchestrator.Orchestrator;
import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.impl.AbstractListTask;
import com.github.ehe.simpleorchestrator.impl.Channel;
import com.github.ehe.simpleorchestrator.impl.Selector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provide capabilities to create meta data for orchestration components at run time.
 * for orchestrator design visualization and inspection purpose.
 */
public class MetaFactory {

    private NameProvider nameProvider;
    private Map<Object, Meta> map = new HashMap<>();

    public MetaFactory(NameProvider nameProvider) {
        this.nameProvider = nameProvider;
    }

    public Meta createMeta(Object obj) {
        Meta newMeta = map.get(obj);
        if (null != newMeta) {
            return newMeta;
        }

        String beanName = nameProvider.getName(obj);
        String className = obj.getClass().getSimpleName();

        if (obj instanceof Selector) {
            SelectorMeta meta = new SelectorMeta(beanName, className);
            meta.setMap(this.getSelectorTaskMetaMap((Selector) obj));
            newMeta = meta;
        } else if (obj instanceof Channel) {
            ChannelMeta meta = new ChannelMeta(beanName, className);
            meta.setTaskList(this.getTaskMetaList((AbstractListTask) obj));
            newMeta = meta;
        } else if (obj instanceof Orchestrator) {
            OrchestratorMeta meta = new OrchestratorMeta(beanName, className);
            meta.setTaskList(this.getTaskMetaList((AbstractListTask) obj));
            newMeta = meta;
        } else if (obj instanceof Task) {
            newMeta = new TaskMeta(beanName, className);
        }

        if (null != newMeta) {
            map.put(obj, newMeta);
        }

        return newMeta;
    }

    public List<Meta> getTaskMetaList(AbstractListTask obj) {
        try {
            Field f = AbstractListTask.class.getDeclaredField("tasks");
            f.setAccessible(true);
            return ((List<Task>) f.get(obj)).stream()
                    .map(task -> this.createMeta(task))
                    .collect(Collectors.toList());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new OrchestratorException("getTaskMetaList", e);
        }
    }

    public Map<String, Meta> getSelectorTaskMetaMap(Selector obj) {
        try {
            Field f = Selector.class.getDeclaredField("taskMap");
            f.setAccessible(true);
            Map<Enum, Task> map = (Map<Enum, Task>) f.get(obj);
            return map.keySet().stream()
                    .collect(Collectors.toMap(key -> key.name(),
                            key -> this.createMeta(map.get(key))));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new OrchestratorException("getSelectorTaskMetaMap", e);
        }
    }
}
