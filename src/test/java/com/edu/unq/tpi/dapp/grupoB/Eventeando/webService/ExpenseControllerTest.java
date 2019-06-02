package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.ExpenseService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


public class ExpenseControllerTest extends ControllerTest {

    @Autowired
    private ExpenseService expenseService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void anExpenseIsCreated() throws Exception {

        Expense coca = Expense.create("coca", 100.0);
        JSONObject bodyRequest = new JSONObject();
        bodyRequest.put("name", coca.name());
        bodyRequest.putOpt("cost", coca.cost());

        ResultActions perform = performPost(bodyRequest, url());

        MvcResult result = assertThatResponseIsCreated(perform);

        String responseString = getBodyOfTheRequest(result);

        JSONObject jsonResponse = new JSONObject(responseString);
        jsonResponse.remove("id");
        JSONAssert.assertEquals(jsonResponse, bodyRequest, true);
    }

    private String url() {
        return "/expenses";
    }
}
