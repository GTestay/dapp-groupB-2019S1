package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.MoneyTransactionDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence.UserDao;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.LoanStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class MoneyTransactionControllerTest extends ControllerTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    MoneylenderService moneyLender;

    @Autowired
    AccountManagerService accountManagerService;

    private UserFactory userFactory;

    @Autowired
    private MoneyTransactionDao moneyTransactionDao;

    @Autowired
    private MoneylenderService moneylenderService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userFactory = new UserFactory();
    }

    @Test
    public void canBringAllTheLoansStatus() throws Exception {
        User user = userFactory.user();
        userDao.save(user);

        user.takeOutALoan(moneyLender, accountManagerService);

        ResultActions perform = getLoanStatus();

        MvcResult result = assertStatusIsOkAndMediaType(perform);

        Loan loan = moneyTransactionDao.findAllLoanByUser(user).get(0);

        String responseString = getBodyOfTheRequest(result);
        JSONArray jsonResponse = new JSONArray(responseString);
        JSONObject loansStatusBody = new JSONObject();
        loansStatusBody.put("remainingFees", moneylenderService.remainingPayments(loan, accountManagerService));
        loansStatusBody.put("date", loan.date().toString());
        loansStatusBody.put("email", loan.user().email());
        loansStatusBody.put("name", loan.user().fullName());
        loansStatusBody.put("defaulter", loan.user().isDefaulter(moneylenderService));

        JSONAssert.assertEquals(jsonResponse.getJSONObject(0), loansStatusBody, true);
    }

    public ResultActions getLoanStatus() throws Exception {
        return clientRest.perform(get("/loanStatus").contentType(MediaType.APPLICATION_JSON));
    }
}
