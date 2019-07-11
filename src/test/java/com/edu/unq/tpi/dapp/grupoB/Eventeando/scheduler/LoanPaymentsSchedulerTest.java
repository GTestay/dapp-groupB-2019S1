package com.edu.unq.tpi.dapp.grupoB.Eventeando.scheduler;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.task.LoanPaymentsTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class LoanPaymentsSchedulerTest {

    @Autowired
    private AccountManagerService accountManagerService;

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private LoanPaymentsScheduler job;

    @Autowired
    private MoneylenderService moneyLender;

    @Test
    public void ifTodayIsTheFifthDayOfTheMonthExecutesTheService() {
        Clock clock = Clock.fixed(Instant.parse("2019-05-05T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsTask service = Mockito.spy(new LoanPaymentsTask());

        job.execute(service, date, moneyLender, accountManagerService);

        verify(service, times(1)).execute(moneyLender, accountManagerService);
    }


    @Test
    public void ifTodayIsNotTheFifthDayOfTheMonthDoNotExecutesTheService() {
        Clock clock = Clock.fixed(Instant.parse("2001-01-01T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsTask service = Mockito.spy(new LoanPaymentsTask());

        job.execute(service, date, moneyLender, accountManagerService);

        verify(service, never()).execute(moneyLender, accountManagerService);
    }
}
