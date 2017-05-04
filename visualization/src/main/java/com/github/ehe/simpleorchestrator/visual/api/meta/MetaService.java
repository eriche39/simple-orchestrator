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

package com.github.ehe.simpleorchestrator.visual.api.meta;

import com.github.ehe.simpleorchestrator.meta.Meta;
import com.github.ehe.simpleorchestrator.meta.MetaInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * get Orchestrator meta data
 */

@Component
@Path("/internal/meta")
public class MetaService {

    @Autowired
    private MetaInfoFactory metaFactory;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meta> getOrchestrators() {
        return metaFactory.getOrchestratorMetas();
    }

}