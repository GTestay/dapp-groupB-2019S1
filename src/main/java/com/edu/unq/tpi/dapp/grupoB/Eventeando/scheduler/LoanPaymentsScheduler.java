package com.edu.unq.tpi.dapp.grupoB.Eventeando.scheduler;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.tasks.LoanPaymentsTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanPaymentsScheduler {

    // cron = s m h D M
    @Scheduled(cron = "0 0 0 05 * ?")
    public void execute() {
        LoanPaymentsTask loanPaymentsTask = new LoanPaymentsTask();
        LocalDate date = LocalDate.now();

        executeTask(loanPaymentsTask, date);
    }

    public void execute(LoanPaymentsTask task, LocalDate date) { executeTask(task, date); }

    private void executeTask(LoanPaymentsTask task, LocalDate date) {
        if ( date.getDayOfMonth() == 5) {
            task.execute();
        } else {
            System.out.println("Job Trying To Execute On Invalid Date");
        }
    }
}
