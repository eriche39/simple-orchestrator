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
import com.github.ehe.simpleorchestrator.context.Context;
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.impl.AbstractListTask;
import com.github.ehe.simpleorchestrator.impl.Channel;
import com.github.ehe.simpleorchestrator.impl.Selector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        return createMeta(obj, new ArrayList<>());
    }

    public Meta createMeta(Object obj, List<String> parents) {
        Meta newMeta = map.get(obj);
        if (null != newMeta) {
            return newMeta;
        }

        String beanName = nameProvider.getName(obj);
        String className = obj.getClass().getSimpleName();
        String context = Context.class.getName();
        List<String> nextParents = new ArrayList<>(parents);
        String label = (beanName!=null?beanName:className);

        if (obj instanceof Selector) {
            nextParents.add("S#"+label);
            context = getContextClass(obj, "getChannel");
            SelectorMeta meta = new SelectorMeta(beanName, className, context, parents);
            meta.setMap(this.getSelectorTaskMetaMap((Selector) obj, nextParents));
            newMeta = meta;
        } else if (obj instanceof Channel) {
            nextParents.add("C#"+label);
            context = getContextClass(obj, "execute");
            ChannelMeta meta = new ChannelMeta(beanName, className, context, parents);
            meta.setTaskList(this.getTaskMetaList((AbstractListTask) obj, nextParents));
            newMeta = meta;
        } else if (obj instanceof Orchestrator) {
            nextParents.add("O#"+label);
            context = getContextClass(obj, "execute");
            OrchestratorMeta meta = new OrchestratorMeta(beanName, className, context, parents);
            meta.setTaskList(this.getTaskMetaList((AbstractListTask) obj, nextParents));
            newMeta = meta;
        } else if (obj instanceof Task) {
            context = getContextClass(obj, "execute");
            newMeta = new TaskMeta(beanName, className, context, parents);
        }

        if (null != newMeta) {
            map.put(obj, newMeta);
        }

        return newMeta;
    }

    private String getContextClass(Object obj, String methodName) {
        String context = Context.class.getName();
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method m: methods ) {
            if(methodName.equals(m.getName()) && m.getParameterTypes().length==1 && !m.getParameterTypes()[0].equals(Context.class)){
                context = m.getParameterTypes()[0].getSimpleName();
            }
        }
        return context;
    }

    public List<Meta> getTaskMetaList(AbstractListTask obj, List<String> nextParents) {
        try {
            Field f = AbstractListTask.class.getDeclaredField("tasks");
            f.setAccessible(true);
            return ((List<Task>) f.get(obj)).stream()
                    .map(task -> this.createMeta(task, nextParents))
                    .collect(Collectors.toList());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new OrchestratorException("getTaskMetaList", e);
        }
    }

    public Map<String, Meta> getSelectorTaskMetaMap(Selector obj, List<String> nextParents) {
        try {
            Field f = Selector.class.getDeclaredField("taskMap");
            f.setAccessible(true);
            Map<Enum, Task> map = (Map<Enum, Task>) f.get(obj);
            return map.keySet().stream()
                    .collect(Collectors.toMap(Enum::name,
                            key -> this.createMeta(map.get(key), nextParents)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new OrchestratorException("getSelectorTaskMetaMap", e);
        }
    }
}
