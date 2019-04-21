package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneyAccountException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountManager {
    public static final String USER_LOW_BALANCE = "El Usuario No Tiene Saldo Suficiente Para Realizar La Operacion";

    private static AccountManager instance;

    private HashMap<User, ArrayList<MoneyTransaction>> accounts = new HashMap<>();

    public static AccountManager get(User user) {
        // TODO: Hay que sacar este singleton y poner una tabla como hace la gente seria
        if(instance == null){ instance = new AccountManager(); }

        instance.accounts.putIfAbsent(user, new ArrayList<>());

        return instance;
    }

    private ArrayList<MoneyTransaction> transactions(User user) { return accounts.get(user); }

    // Por ahora estos dos son iguales, pero en teoria deberian ser distintos ya que uno necesita datos de la tarjeta
    public void takeCash(User user, double amount) {
        if (validateBalance(user, amount)) { throw new MoneyAccountException(USER_LOW_BALANCE); }

        transactions(user).add(Extraction.create(user, LocalDate.now(), amount));
    }

    public void requireCredit(User user, double amount) {
        if (validateBalance(user, amount)) { throw new MoneyAccountException(USER_LOW_BALANCE); }

        transactions(user).add(Extraction.create(user, LocalDate.now(), amount));
    }

    private boolean validateBalance(User user, double amount) { return statement(user) < amount; }

    public double statement(User user) { return transactions(user).stream().mapToDouble(MoneyTransaction::transactionalValue).sum(); }

    public void cashDeposit(User user, double amount) { makeDeposit(user, DepositByCash.create(user, LocalDate.now(), amount)); }

    public void creditDeposit(User user, double amount) { makeDeposit(user, DepositByCreditCard.create(user, LocalDate.now(), amount)); }

    private void makeDeposit(User user, Deposit deposit) { transactions(user).add(deposit); }
}
