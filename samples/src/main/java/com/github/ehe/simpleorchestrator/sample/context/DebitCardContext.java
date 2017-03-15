/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.context;

import com.github.ehe.simpleorchestrator.context.HistoryContext;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;

/**
 * Created by eric on 3/12/17.
 */
public interface DebitCardContext extends HistoryContext<String> {
    CardApplication getApplication();
    void setCardApproveStatus(boolean isApproved);
}
