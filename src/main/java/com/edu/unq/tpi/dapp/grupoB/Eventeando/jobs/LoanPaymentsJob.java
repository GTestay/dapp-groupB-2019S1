package com.edu.unq.tpi.dapp.grupoB.Eventeando.jobs;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.services.LoanPaymentsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class LoanPaymentsJob implements Job {
    private LoanPaymentsService loanPaymentsService = new LoanPaymentsService();

    @Override
    public void execute(JobExecutionContext context) {
        loanPaymentsService.execute();
    }
}
