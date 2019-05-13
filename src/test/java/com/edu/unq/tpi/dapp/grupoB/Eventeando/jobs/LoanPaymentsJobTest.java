package com.edu.unq.tpi.dapp.grupoB.Eventeando.jobs;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.services.LoanPaymentsService;
import org.junit.Test;
import org.mockito.Mockito;
import org.quartz.JobExecutionContext;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class LoanPaymentsJobTest {

    LoanPaymentsJob job = new LoanPaymentsJob();

    private JobExecutionContext context;

    @Test
    public void ifTodayIsTheFifthDayOfTheMonthExecutesTheService(){
        Clock clock = Clock.fixed(Instant.parse("2019-05-05T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsService service = Mockito.spy(new LoanPaymentsService());

        job.execute(context, service, date);

        verify(service, times(1)).execute();
    }


    @Test
    public void ifTodayIsNotTheFifthDayOfTheMonthDoNotExecutesTheService(){
        Clock clock = Clock.fixed(Instant.parse("2001-01-01T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsService service = Mockito.spy(new LoanPaymentsService());

        job.execute(context, service, date);

        verify(service, never()).execute();
    }
}
