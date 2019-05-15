package com.edu.unq.tpi.dapp.grupoB.Eventeando.jobs;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.services.LoanPaymentsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDate;

public class LoanPaymentsJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        LoanPaymentsService loanPaymentsService = new LoanPaymentsService();
        LocalDate date = LocalDate.now();

        executeService(loanPaymentsService, date);
    }

    // Este metodo es solo para poder hacer los test para executeService
    public void execute(JobExecutionContext context, LoanPaymentsService service, LocalDate date) { executeService(service, date); }

    private void executeService(LoanPaymentsService service, LocalDate date) {
        if ( date.getDayOfMonth() == 5) {
            service.execute();
        } else {
            System.out.println("Job Trying To Execute On Invalid Date");
        }
    }
}
