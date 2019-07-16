package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.MoneylenderException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class MoneylenderService {
    public static final String USER_DEFAULTER = "Can't Give A Loan To A Defaulter User";
    public static final double LOAN_PAYMENT_COST = 200.00;
    public static final double LOAN_COST = 1000.00;
    public static final String USER_LOAN_IN_PROGRESS = "Can't Give A Loan To An User With One In Progress";
    private static final String USER_WITHOUT_LOAN = "This user don't have any Loan";

    private final MoneyTransactionDao moneyTransactionDao;

    public MoneylenderService(MoneyTransactionDao moneyTransactionDao) {
        this.moneyTransactionDao = moneyTransactionDao;
    }

    public Loan giveLoan(User user, AccountManagerService accountManagerService) {
        validateUser(user);

        return accountManagerService.giveLoan(user);
    }

    private void validateUser(User user) {
        if (isDefaulter(user)) { throw new MoneylenderException(USER_DEFAULTER); }

        if (hasLoanInProgress(user)) { throw new MoneylenderException(USER_LOAN_IN_PROGRESS); }
    }

    private boolean hasLoanInProgress(User user) {
        return loansOf(user).stream().anyMatch(loan -> !loan.isEnded());
    }

    public boolean isDefaulter(User user) { return user.indebt(); }

    public void indebt(User user) { user.withDebt(); }

    public void payLoan(User user, MoneylenderService moneyLender, AccountManagerService accountManagerService) {
        if (isDefaulter(user)) { checkPayments(user, moneyLender, accountManagerService); }

        if (user.balance(accountManagerService) >= LOAN_PAYMENT_COST) {
            accountManagerService.payLoan(user, moneyLender);
        } else {
            indebt(user);
            unpaidFee(user, accountManagerService);
        }
    }

    public void checkIfLoanIsOver(Loan loan, AccountManagerService accountManagerService) {
        if (remainingPayments(loan, accountManagerService) == 0) { loan.end(); }
    }

    private void checkPayments(User user, MoneylenderService moneyLender, AccountManagerService accountManagerService) {
        IntStream.rangeClosed(1, user.unpaidFees()).forEach(fun -> checkPayment(user, moneyLender, accountManagerService));

        if (user.unpaidFees() == 0) { user.debtSettled(); }
    }

    private void checkPayment(User user, MoneylenderService moneyLender, AccountManagerService accountManagerService) {
        if (user.balance(accountManagerService) >= LOAN_PAYMENT_COST) {
            user.payFee();
            accountManagerService.payLoan(user, moneyLender);
        }
    }

    private void unpaidFee(User user, AccountManagerService accountManagerService) {
        if(accountManagerService.amountOfPaymentsDone(getLoan(user)) + user.unpaidFees() < 6) {
            user.unpaidFee();
        }
    }

    public List<Loan> actualLoans() {
        return moneyTransactionDao.findAllLoan();
    }

    public int remainingPayments(Loan loan, AccountManagerService accountManagerService) {
        Integer actualPayments = accountManagerService.amountOfPaymentsDone(loan);

        return 6 - actualPayments;
    }

    public int remainingPayments(User user, AccountManagerService accountManagerService) {
        Integer actualPayments = accountManagerService.amountOfPaymentsDone(getLoan(user));

        return 6 - actualPayments;
    }

    public int unpaidPayments(User user) {
        return user.unpaidFees();
    }

    public Loan getLoan(User user) {
        List<Loan> userLoans = loansOf(user);
        userLoans.sort(Comparator.comparing(loan -> loan.date()));
        Collections.reverse(userLoans);

        Optional<Loan> userLoan = userLoans.stream().findFirst();

        return userLoan.orElseThrow(() -> new MoneylenderException(USER_WITHOUT_LOAN));
    }

    private List<Loan> loansOf(User user) {
        return moneyTransactionDao.findAllLoanByUser(user);
    }
}
