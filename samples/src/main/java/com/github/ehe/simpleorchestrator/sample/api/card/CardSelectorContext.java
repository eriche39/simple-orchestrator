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

package com.github.ehe.simpleorchestrator.sample.api.card;

import com.github.ehe.simpleorchestrator.sample.context.AsyncCheckRiskContext;
import com.github.ehe.simpleorchestrator.sample.context.CreditScoreContext;
import com.github.ehe.simpleorchestrator.sample.context.CreditCardContext;
import com.github.ehe.simpleorchestrator.sample.context.DebitCardContext;

public interface CardSelectorContext extends CreditScoreContext, CreditCardContext, DebitCardContext, AsyncCheckRiskContext {
}
