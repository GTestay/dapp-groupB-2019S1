package com.edu.unq.tpi.dapp.grupoB.Eventeando.scheduler;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.aspect.LoggerAspect;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.Moneylender;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.task.LoanPaymentsTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanPaymentsScheduler {

    private final AccountManager accountManager;
    private Logger logger = LogManager.getLogger(LoggerAspect.class);
    private final Moneylender moneyLender;

    public LoanPaymentsScheduler(AccountManager accountManager, Moneylender moneyLender) {
        this.accountManager = accountManager;
        this.moneyLender = moneyLender;
    }

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
            logger.info("Job executing");
            task.execute(moneyLender, accountManager);
            logger.info("Job completed");
        } else {
            logger.error("Job Trying To Execute On Invalid Date");
        }
    }
}
