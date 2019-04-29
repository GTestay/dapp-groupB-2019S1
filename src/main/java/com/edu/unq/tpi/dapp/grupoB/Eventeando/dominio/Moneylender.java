package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneyAccountException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneylenderException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Moneylender {
    public static final String USER_DEFAULTER = "Can't Give A Loan To A Defaulter User";
    public static final double LOAN_PAYMENT_COST = 200.00;
    public static final double LOAN_COST = 1000.00;
    public static final String USER_LOAN_IN_PROGRESS = "Can't Give A Loan To An User With One In Progress";
    private static final String USER_WITHOUT_LOAN = "This user don't have any Loan";

    private static Moneylender instance;

    private HashSet<User> indebted = new HashSet<>();
    private ArrayList<Loan> actualLoans = new ArrayList<>();
    private HashMap<User, Integer> unpaidFees = new HashMap<>();

    public static Moneylender get() {
        // TODO: Hay que sacar este singleton y poner una tabla como hace la gente seria
        if(instance == null){ instance = new Moneylender(); }

        return instance;
    }

    public void giveLoan(User user) {
        validateUser(user);

        Loan newLoan = AccountManager.get(user).giveLoan(user);

        actualLoans.add(newLoan);
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
        }
    }

    private void unpaidFee(User user) {
        unpaidFees.putIfAbsent(user, 0);

        Integer actualUnpaidFees = unpaidFees.get(user);

        unpaidFees.put(user, actualUnpaidFees + 1);
    }

    public ArrayList<Loan> actualLoans() { return actualLoans; }

    public int remainingPayments(User user) {
        AccountManager accountManager = AccountManager.get(user);

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
}
