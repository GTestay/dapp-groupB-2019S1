package com.edu.unq.tpi.dapp.grupoB.Eventeando.validators;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneyTransactionException;

import java.time.LocalDate;

public class MoneyTransactionValidator extends Validator {
    public static final String MONEY_TRANSACTION_IS_INVALID_WITHOUT_USER = "Money Transaction Is Invalid Without User";
    public static final String MONEY_TRANSACTION_IS_INVALID_WITHOUT_DATE = "Money Transaction Is Invalid Without Date";
    public static final String AMOUNT_HAS_NEGATIVE_VALUE = "Amount Must Be Positive";

    public User validateUser(User user) { return validateNullityOf(user, new MoneyTransactionException(MONEY_TRANSACTION_IS_INVALID_WITHOUT_USER)); }

    public LocalDate validateDate(LocalDate date) { return validateNullityOf(date, new MoneyTransactionException(MONEY_TRANSACTION_IS_INVALID_WITHOUT_DATE)); }

    public double validateAmount(double amount) {
        if (amount < 0) { throw new MoneyTransactionException(AMOUNT_HAS_NEGATIVE_VALUE); }

        return amount;
    }
}
