package com.edu.unq.tpi.dapp.grupoB.Eventeando.triggers;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.jobs.LoanPaymentsJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class LoanPaymentsTrigger {
    public static void main(String[] args) {
        try {
            // Creas los detalles del job que queres correr
            JobDetail job = JobBuilder.newJob(LoanPaymentsJob.class).withIdentity("LoanPaymentsJob", "Eventeando").build();

            // Preparas como es que se va a triggerear el job
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("LoanPaymentsTrigger", "Eventeando")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .build();

            // Lo pones en un scheduler
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
