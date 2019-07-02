package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.MoneylenderException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class Moneylender {
    public static final String USER_DEFAULTER = "Can't Give A Loan To A Defaulter User";
    public static final double LOAN_PAYMENT_COST = 200.00;
    public static final double LOAN_COST = 1000.00;
    public static final String USER_LOAN_IN_PROGRESS = "Can't Give A Loan To An User With One In Progress";
    private static final String USER_WITHOUT_LOAN = "This user don't have any Loan";

    private HashMap<User, Integer> unpaidFees = new HashMap<>();

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    public void giveLoan(User user, AccountManager accountManager) {
        validateUser(user);

        accountManager.giveLoan(user);
    }

    private void validateUser(User user) {
        if (isDefaulter(user)) { throw new MoneylenderException(USER_DEFAULTER); }

        if (hasLoanInProgress(user)) { throw new MoneylenderException(USER_LOAN_IN_PROGRESS); }
    }

    private boolean hasLoanInProgress(User user) {
        return !loansOf(user).isEmpty();
    }

    public boolean isDefaulter(User user) { return user.indebt(); }

    public void indebt(User user) { user.withDebt(); }

    public void payLoan(User user, AccountManager accountManager, Moneylender moneyLender) {
        if (isDefaulter(user)) { checkPayments(user, accountManager, moneyLender); }

        if (user.balance(accountManager) > LOAN_PAYMENT_COST) {
            accountManager.payLoan(user, moneyLender);
        } else {
            indebt(user);
            unpaidFee(user);
        }
    }

    public void checkIfLoanIsOver(AccountManager accountManager) {
        actualLoans().removeIf(loan -> remainingPayments(loan.user, accountManager) == 0);
    }

    private void checkPayments(User user, AccountManager accountManager, Moneylender moneyLender) {
        IntStream.rangeClosed(1, unpaidFees.get(user)).forEach(fun -> checkPayment(user, accountManager, moneyLender));

        if (unpaidFees.get(user) == 0) { user.payOffDebt(); }
    }

    private void checkPayment(User user, AccountManager accountManager, Moneylender moneyLender) {
        if (user.balance(accountManager) > LOAN_PAYMENT_COST) {
            unpaidFees.put(user, unpaidFees.get(user) - 1);
            accountManager.payLoan(user, moneyLender);
        }
    }

    private void unpaidFee(User user) {
        unpaidFees.putIfAbsent(user, 0);

        Integer actualUnpaidFees = unpaidFees.get(user);

        unpaidFees.put(user, actualUnpaidFees + 1);
    }

    public List<Loan> actualLoans() {
        return moneyTransactionDao.findAllLoan();
    }

    public int remainingPayments(User user, AccountManager accountManager) {
        Integer actualPayments = accountManager.amountOfPaymentsDone(getLoan(user));

        return 6 - actualPayments;
    }

    public int unpaidPayments(User user) {
        unpaidFees.putIfAbsent(user, 0);

        return unpaidFees.get(user);
    }

    public Loan getLoan(User user) {
        List<Loan> userLoans = loansOf(user);
        userLoans.sort(Comparator.comparing(loan -> loan.date));
        Collections.reverse(userLoans);

        Optional<Loan> userLoan = userLoans.stream().findFirst();

        if(userLoan.isPresent()){
            return userLoan.get();
        } else {
            throw new MoneylenderException(USER_WITHOUT_LOAN);
        }
    }

    private List<Loan> loansOf(User user) {
        return actualLoans().stream().filter(loan -> loan.isOwner(user)).collect(Collectors.toList());
    }
}
