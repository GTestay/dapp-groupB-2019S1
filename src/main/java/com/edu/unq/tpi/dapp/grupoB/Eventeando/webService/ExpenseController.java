package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    private static final String baseUrl = "/expenses";

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(baseUrl)
    @ResponseBody
    public List<Expense> events() {
        return expenseService.allExpenses();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(baseUrl)
    @ResponseBody
    public Expense events(@RequestBody Expense expense) {
        return expenseService.create(expense);
    }

}
