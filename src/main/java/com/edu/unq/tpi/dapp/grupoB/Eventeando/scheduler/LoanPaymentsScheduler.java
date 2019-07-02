package com.edu.unq.tpi.dapp.grupoB.Eventeando.scheduler;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.task.LoanPaymentsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanPaymentsScheduler {

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private Moneylender moneyLender;

    // cron = s m h D M
    @Scheduled(cron = "0 0 0 05 * ?")
    public void execute() {
        LoanPaymentsTask loanPaymentsTask = new LoanPaymentsTask();
        LocalDate date = LocalDate.now();

        executeTask(loanPaymentsTask, date, moneyLender, accountManager);
    }

    public void execute(LoanPaymentsTask task, LocalDate date, Moneylender moneyLender, AccountManager accountManager) { executeTask(task, date, moneyLender, accountManager); }

    private void executeTask(LoanPaymentsTask task, LocalDate date, Moneylender moneyLender, AccountManager accountManager) {
        if ( date.getDayOfMonth() == 5) {
            task.execute(moneyLender, accountManager);
        } else {
            System.out.println("Job Trying To Execute On Invalid Date");
        }
    }
}
