package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneylenderException;

public class Moneylender {
    public static final String USER_DEFAULTER = "Can't Give A Loan To A Defaulter User";

    public void giveLoan(User user) {
        validateUser(user);

        AccountManager.get(user).giveLoan(user);
    }

    private void validateUser(User user) { if (user.isDefaulter()) { throw new MoneylenderException(USER_DEFAULTER); } }
}
