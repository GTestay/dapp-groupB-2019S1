package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.LoanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class MoneyTransactionController {

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private MoneylenderService moneylenderService;

    @Autowired
    private AccountManagerService accountManagerService;

    @GetMapping("/loanStatus")
    @ResponseStatus(HttpStatus.OK)
    public List<LoanStatus> loanStatus() {
        ArrayList<LoanStatus> allLoanStatus = new ArrayList<>();
        List<Loan> loans = moneyTransactionDao.findAllLoan();

        loans.forEach(loan -> allLoanStatus.add(makeLoanStatus(loan, moneylenderService, accountManagerService)));

        return allLoanStatus;
    }

    private LoanStatus makeLoanStatus(Loan loan, MoneylenderService moneylenderService, AccountManagerService accountManagerService) {
        return new LoanStatus(loan, moneylenderService, accountManagerService);
    }
}
