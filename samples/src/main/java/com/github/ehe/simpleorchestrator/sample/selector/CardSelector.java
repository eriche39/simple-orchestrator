/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.selector;

import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.impl.Selector;
import com.github.ehe.simpleorchestrator.sample.api.card.CardSelectorConext;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;

import java.util.EnumMap;

public class CardSelector extends Selector<CardApplication.CardType, CardSelectorConext> {
    public CardSelector(EnumMap<CardApplication.CardType, Task<CardSelectorConext>> cardTypeTaskMap) {
        super(cardTypeTaskMap);
    }

    @Override
    public CardApplication.CardType getChannel(CardSelectorConext context) {
        return context.getApplication().getCardType();
    }
}
