package com.edu.unq.tpi.dapp.grupoB.Eventeando.scheduler;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.aspect.LoggerAspect;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.task.LoanPaymentsTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanPaymentsScheduler {

    private final AccountManagerService accountManagerService;
    private Logger logger = LogManager.getLogger(LoggerAspect.class);
    private final MoneylenderService moneyLender;

    public LoanPaymentsScheduler(AccountManagerService accountManagerService, MoneylenderService moneyLender) {
        this.accountManagerService = accountManagerService;
        this.moneyLender = moneyLender;
    }

    // cron = s m h D M
    @Scheduled(cron = "0 0 0 05 * ?")
    public void execute() {
        LoanPaymentsTask loanPaymentsTask = new LoanPaymentsTask();
        LocalDate date = LocalDate.now();

        executeTask(loanPaymentsTask, date, moneyLender, accountManagerService);
    }

    public void execute(LoanPaymentsTask task, LocalDate date, MoneylenderService moneyLender, AccountManagerService accountManagerService) { executeTask(task, date, moneyLender, accountManagerService); }

    private void executeTask(LoanPaymentsTask task, LocalDate date, MoneylenderService moneyLender, AccountManagerService accountManagerService) {
        if ( date.getDayOfMonth() == 5) {
            logger.info("Job executing");
            task.execute(moneyLender, accountManagerService);
            logger.info("Job completed");
        } else {
            logger.error("Job Trying To Execute On Invalid Date");
        }
    }
}
