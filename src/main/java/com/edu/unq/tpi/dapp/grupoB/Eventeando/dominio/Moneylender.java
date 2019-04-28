package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneylenderException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class Moneylender {
    public static final String USER_DEFAULTER = "Can't Give A Loan To A Defaulter User";
    public static final double LOAN_PAYMENT_COST = 200.00;
    public static final double LOAN_COST = 1000.00;

    private static Moneylender instance;

    private ArrayList<User> indebted = new ArrayList<>();
    private HashMap<User, Integer> unpaidFees = new HashMap<>();

    public static Moneylender get(User user) {
        // TODO: Hay que sacar este singleton y poner una tabla como hace la gente seria
        if(instance == null){ instance = new Moneylender(); }

        return instance;
    }

    public void giveLoan(User user) {
        validateUser(user);

        AccountManager.get(user).giveLoan(user);
    }

    private void validateUser(User user) { if (user.isDefaulter()) { throw new MoneylenderException(USER_DEFAULTER); } }

    public boolean isDefaulter(User user) { return indebted.contains(user); }

    public void indebt(User user) { indebted.add(user); }

    public void payLoan(User user) {
        if (isDefaulter(user)) { checkPayments(user); }

        if (user.balance() > LOAN_PAYMENT_COST) {
            AccountManager.get(user).payLoan(user);
        } else {
            indebt(user);
            unpaidFee(user);
        }
    }

    private void checkPayments(User user) {
        IntStream.rangeClosed(1, unpaidFees.get(user)).forEach(fun -> checkPayment(user));

        if (unpaidFees.get(user) == 0) { indebted.remove(user); }
    }

    private void checkPayment(User user) {
        if (user.balance() > LOAN_PAYMENT_COST) {
            unpaidFees.put(user, unpaidFees.get(user) - 1);
            AccountManager.get(user).payLoan(user);
        } else {
            unpaidFee(user);
        }
    }

    private void unpaidFee(User user) {
        unpaidFees.putIfAbsent(user, 0);

        unpaidFees.put(user, 1);
    }
}
