package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.*;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.MoneyAccountException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class AccountManagerService {
    public static final String USER_LOW_BALANCE = "Not Enough Money For This Transaction";

    private final MoneyTransactionDao moneyTransactionDao;

    @Autowired
    public AccountManagerService(MoneyTransactionDao moneyTransactionDao) {
        this.moneyTransactionDao = moneyTransactionDao;
    }

    public Loan giveLoan(User user) {
        Loan newLoan = Loan.create(user);

        moneyTransactionDao.save(newLoan);

        return newLoan;
    }

    public List<MoneyTransaction> transactions(User user) { return moneyTransactionDao.findAllByUser(user); }

    // Por ahora estos dos son iguales, pero en teoria deberian ser distintos ya que uno necesita datos de la tarjeta
    public void takeCash(User user, double amount) {
        validateUserBalance(user, amount);

        moneyTransactionDao.save(Extraction.create(user, LocalDate.now(), amount));
    }

    public void requireCredit(User user, double amount) {
        validateUserBalance(user, amount);

        moneyTransactionDao.save(Extraction.create(user, LocalDate.now(), amount));
    }

    private void validateUserBalance(User user, double amount) {
        if (validateBalance(user, amount)) {
            throw new MoneyAccountException(USER_LOW_BALANCE);
        }
    }

    private boolean validateBalance(User user, double amount) { return balance(user) < amount; }

    public double balance(User user) { return transactions(user).stream().mapToDouble(MoneyTransaction::transactionalValue).sum(); }

    public void cashDeposit(User user, double amount) {
        makeDeposit(DepositByCash.create(user, LocalDate.now(), amount));
    }

    public void creditDeposit(User user, double amount, YearMonth dueDate, String cardNumber) {
        makeDeposit(DepositByCreditCard.create(user, LocalDate.now(), amount, dueDate, cardNumber));
    }

    private void makeDeposit(Deposit deposit) { moneyTransactionDao.save(deposit); }

    public void payLoan(User user, MoneylenderService moneyLender) { moneyTransactionDao.save(LoanPayment.create(user, moneyLender.getLoan(user))); }

    public Integer amountOfPaymentsDone(Loan userLoan) {
        return moneyTransactionDao.findAllLoanPaymentByUserAndReferenceLoan(userLoan.user(), userLoan).size();
    }
}
