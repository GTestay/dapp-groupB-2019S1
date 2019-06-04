package com.edu.unq.tpi.dapp.grupoB.Eventeando.scheduler;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.task.LoanPaymentsTask;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class LoanPaymentsSchedulerTest {

    LoanPaymentsScheduler job = new LoanPaymentsScheduler();

    @Test
    public void ifTodayIsTheFifthDayOfTheMonthExecutesTheService(){
        Clock clock = Clock.fixed(Instant.parse("2019-05-05T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsTask service = Mockito.spy(new LoanPaymentsTask());

        job.execute(service, date);

        verify(service, times(1)).execute();
    }


    @Test
    public void ifTodayIsNotTheFifthDayOfTheMonthDoNotExecutesTheService(){
        Clock clock = Clock.fixed(Instant.parse("2001-01-01T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsTask service = Mockito.spy(new LoanPaymentsTask());

        job.execute(service, date);

        verify(service, never()).execute();
    }
}
