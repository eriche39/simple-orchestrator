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

package com.github.ehe.simpleorchestrator.sample.context;

import com.github.ehe.simpleorchestrator.context.HistoryContext;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;

public interface CreditCardContext extends HistoryContext<String> {
    CardApplication getApplication();
    int getCreditScore();
    int getRiskScore();
    void setCardApproveStatus(boolean isApproved);
}
