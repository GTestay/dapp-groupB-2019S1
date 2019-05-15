package com.edu.unq.tpi.dapp.grupoB.Eventeando.triggers;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.jobs.LoanPaymentsJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.CronScheduleBuilder.monthlyOnDayAndHourAndMinute;

public class LoanPaymentsTrigger {
    public static void main(String[] args) {
        try {
            // Creas los detalles del job que queres correr
            JobDetail job = JobBuilder.newJob(LoanPaymentsJob.class).withIdentity("LoanPaymentsJob", "Eventeando").build();

            // Preparas como es que se va a triggerear el job
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("LoanPaymentsTrigger", "Eventeando")
                    .withSchedule(monthlyOnDayAndHourAndMinute(5, 0, 0))
                    .forJob("LoanPaymentsJob", "Eventeando")
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
