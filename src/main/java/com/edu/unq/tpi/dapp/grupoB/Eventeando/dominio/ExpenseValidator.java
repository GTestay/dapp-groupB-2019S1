package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.Validator;

public class ExpenseValidator extends Validator {
    public static final String ERROR_CAN_NOT_CREATE_AN_EXPENSE_WHOSE_PRICE_IS_NEGATIVE = "error, can't not create an expense whose price is negative";
    public static final String ERROR_EXPENSE_IS_INVALID_WITHOUT_NAME = "The expense needs a name!";

    void validateCost(Double cost) {
        super.validateNegativiyOf(cost, new RuntimeException(ERROR_CAN_NOT_CREATE_AN_EXPENSE_WHOSE_PRICE_IS_NEGATIVE));
    }

    void validateName(String name) {
        super.validateEmptinessOf(name, new RuntimeException(ERROR_EXPENSE_IS_INVALID_WITHOUT_NAME));
    }
}
