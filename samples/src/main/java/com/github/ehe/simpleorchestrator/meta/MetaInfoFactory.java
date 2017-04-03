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

import com.github.ehe.simpleorchestrator.impl.OrchestratorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provide capabilities to create meta data for orchestration components at run time.
 * for orchestrator design visualization and inspection purpose.
 */
@Component
public class MetaInfoFactory implements NameProvider, ApplicationListener<ContextRefreshedEvent> {
    @Inject
    private ApplicationContext ctx;

    //Object to bean name map
    private Map<Object, String> beanMap = new HashMap();

    private MetaFactory factory = new MetaFactory(this);
    private List<Meta> orchestratorMetas;

    private  void init() {
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            beanMap.put(ctx.getBean(beanName), beanName);
        }
        orchestratorMetas = createOrchestratorMetas();
    }

    public List<Meta> getOrchestratorMetas() {
        return orchestratorMetas;
    }

    private List<Meta> createOrchestratorMetas() {
        String[] orchestratorNames = ctx.getBeanNamesForType(OrchestratorImpl.class);
        return Arrays.stream(orchestratorNames)
                .map(ctx::getBean)
                .map(factory::createMeta)
                .collect(Collectors.toList());
    }

    @Override
    public String getName(Object obj) {
        return beanMap.get(obj);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        init();
    }
}
