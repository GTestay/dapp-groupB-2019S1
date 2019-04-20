package com.edu.unq.tpi.dapp.grupoB.Eventeando.validators;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.DepositMoneyException;

import java.time.LocalDate;

public class DepositMoneyValidator {
    public static final String DEPOSIT_MONEY_IS_INVALID_WITHOUT_USER = "Deposit of Money Is Invalid Without User";
    public static final String DEPOSIT_MONEY_IS_INVALID_WITHOUT_DATE = "Deposit of Money Is Invalid Without Date";
    public static final String AMOUNT_HAS_NEGATIVE_VALUE = "Amount Must Be Positive";

    public User validateUser(User user) {
        validateNullityOf(user, new DepositMoneyException(DEPOSIT_MONEY_IS_INVALID_WITHOUT_USER));

        return user;
    }

    public LocalDate validateDate(LocalDate date) {
        validateNullityOf(date, new DepositMoneyException(DEPOSIT_MONEY_IS_INVALID_WITHOUT_DATE));

        return date;
    }

    private void validateNullityOf(Object field, DepositMoneyException exception) { if (field == null) { throw exception; } }

    public double validateAmount(double amount) {
        if (amount < 0) { throw new DepositMoneyException(AMOUNT_HAS_NEGATIVE_VALUE); }

        return amount;
    }
}
