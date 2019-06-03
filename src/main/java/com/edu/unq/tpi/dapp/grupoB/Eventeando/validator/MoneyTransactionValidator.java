package com.edu.unq.tpi.dapp.grupoB.Eventeando.validator;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.MoneyTransactionException;

import java.time.LocalDate;
import java.time.YearMonth;

public class MoneyTransactionValidator extends Validator {
    public static final String MONEY_TRANSACTION_IS_INVALID_WITHOUT_USER = "Money Transaction Is Invalid Without User";
    public static final String MONEY_TRANSACTION_IS_INVALID_WITHOUT_DATE = "Money Transaction Is Invalid Without Date";
    public static final String AMOUNT_HAS_NEGATIVE_VALUE = "Amount Must Be Positive";
    public static final String INVALID_DUE_DATE = "The Due Date Is Invalid";
    public static final String INVALID_CREDIT_CARD_NUMBER = "The Credit Card Number Is Invalid";

    public User validateUser(User user) { return validateNullityOf(user, new MoneyTransactionException(MONEY_TRANSACTION_IS_INVALID_WITHOUT_USER)); }

    public LocalDate validateDate(LocalDate date) { return validateNullityOf(date, new MoneyTransactionException(MONEY_TRANSACTION_IS_INVALID_WITHOUT_DATE)); }

    public double validateAmount(double amount) {
        if (amount < 0) { throw new MoneyTransactionException(AMOUNT_HAS_NEGATIVE_VALUE); }

        return amount;
    }

    public YearMonth validateDueDate(YearMonth dueDate) {
        if (todayIsAfter(dueDate)) { throw new MoneyTransactionException(INVALID_DUE_DATE); }

        return dueDate;
    }

    private boolean todayIsAfter(YearMonth dueDate) {
        return YearMonth.now().isAfter(dueDate);
    }

    public String validateCreditCardNumber(String cardNumber) {
        int length = String.valueOf(cardNumber).length();

        if (length < 12) { throw new MoneyTransactionException(INVALID_CREDIT_CARD_NUMBER); }

        return cardNumber;
    }
}
