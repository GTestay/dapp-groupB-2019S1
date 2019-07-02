package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.MoneyAccountException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountManager {
    public static final String USER_LOW_BALANCE = "Not Enough Money For This Transaction";

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    public Loan giveLoan(User user) {
        Loan newLoan = Loan.create(user);

        moneyTransactionDao.save(newLoan);

        return newLoan;
    }

    private List<MoneyTransaction> transactions(User user) { return moneyTransactionDao.findAllByUser(user); }

    // Por ahora estos dos son iguales, pero en teoria deberian ser distintos ya que uno necesita datos de la tarjeta
    public void takeCash(User user, double amount) {
        if (validateBalance(user, amount)) { throw new MoneyAccountException(USER_LOW_BALANCE); }

        moneyTransactionDao.save(Extraction.create(user, LocalDate.now(), amount));
    }

    public void requireCredit(User user, double amount) {
        if (validateBalance(user, amount)) { throw new MoneyAccountException(USER_LOW_BALANCE); }

        moneyTransactionDao.save(Extraction.create(user, LocalDate.now(), amount));
    }

    private boolean validateBalance(User user, double amount) { return balance(user) < amount; }

    public double balance(User user) { return transactions(user).stream().mapToDouble(MoneyTransaction::transactionalValue).sum(); }

    public void cashDeposit(User user, double amount) {
        makeDeposit(user, DepositByCash.create(user, LocalDate.now(), amount));
    }

    public void creditDeposit(User user, double amount, YearMonth dueDate, String cardNumber) {
        makeDeposit(user, DepositByCreditCard.create(user, LocalDate.now(), amount, dueDate, cardNumber));
    }

    private void makeDeposit(User user, Deposit deposit) { moneyTransactionDao.save(deposit); }

    public void payLoan(User user, Moneylender moneyLender) { moneyTransactionDao.save(LoanPayment.create(user, moneyLender.getLoan(user))); }

    public Integer amountOfPaymentsDone(Loan userLoan) {
        List<LoanPayment> userPayments = transactions(userLoan.user).stream()
                .filter(transaction -> transaction instanceof LoanPayment)
                .map(transaction -> (LoanPayment) transaction)
                .collect(Collectors.toList());

        userPayments.stream().filter(payment -> payment.belongsTo(userLoan)).collect(Collectors.toList());

        return userPayments.size();
    }
}
