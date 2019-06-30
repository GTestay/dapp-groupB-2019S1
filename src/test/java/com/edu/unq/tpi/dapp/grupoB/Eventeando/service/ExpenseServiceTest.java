package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureDataJpa
@ActiveProfiles("test")
public class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void noneExpenseIsRetrieved() {
        List<Expense> events = expenseService.allExpenses();
        assertThat(events).isEmpty();
    }


    @Test
    public void canCreateAnExpense() {
        Expense coca = Expense.create("coca", 100.0);
        expenseService.create(coca);

        List<Expense> events = expenseService.allExpenses();
        assertThat(events).contains(coca);
    }

}
