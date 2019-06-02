package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.ExpenseService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        ResultActions perform = clientRest.perform(post(url())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyRequest.toString()));

        MvcResult result = perform
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(responseString);
        jsonResponse.remove("id");
        JSONAssert.assertEquals(jsonResponse, bodyRequest, true);
    }

    private String url() {
        return "/expenses";
    }
}
