package com.edu.unq.tpi.dapp.grupoB.Eventeando.scheduler;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.AccountManager;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Moneylender;
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
    private AccountManager accountManager;

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private Moneylender moneyLender;

    @Test
    public void ifTodayIsTheFifthDayOfTheMonthExecutesTheService() {
        LoanPaymentsScheduler job = new LoanPaymentsScheduler();

        Clock clock = Clock.fixed(Instant.parse("2019-05-05T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsTask service = Mockito.spy(new LoanPaymentsTask());

        job.execute(service, date, moneyLender, accountManager);

        verify(service, times(1)).execute(moneyLender, accountManager);
    }


    @Test
    public void ifTodayIsNotTheFifthDayOfTheMonthDoNotExecutesTheService() {
        LoanPaymentsScheduler job = new LoanPaymentsScheduler();

        Clock clock = Clock.fixed(Instant.parse("2001-01-01T01:01:01.00Z"), ZoneId.of("UTC"));
        LocalDate date = LocalDate.now(clock);
        LoanPaymentsTask service = Mockito.spy(new LoanPaymentsTask());

        job.execute(service, date, moneyLender, accountManager);

        verify(service, never()).execute(moneyLender, accountManager);
    }
}
