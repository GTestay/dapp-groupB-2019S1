package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneyAccountException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.MoneylenderException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.UserException;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factories.UserFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.validators.UserValidator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

public class UserTest {

    private UserFactory userFactory;


    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }


    @Test
    public void creationOfANewUser() {
        User user = User.create("Maximo", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));

        assertEquals("Maximo", user.name());
        assertEquals("Cossetti", user.lastname());
        assertEquals("eravenna@gmail.com", user.email());
        assertEquals("S1M6L4R", user.password());
        assertEquals(LocalDate.of(2002, 3, 21), user.birthday());
    }

    @Test
    public void userValidations() {
        nameValidations();

        lastnameValidations();

        emailValidations();

        passwordValidations();

        birthdayValidations();
    }

    private void birthdayValidations() {
        try {
            User.create("Maximo", "Cossetti", "eravenna@gmail.com", "S1M6L4R", null);
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_BIRTHDAY);
        }
    }

    private void passwordValidations() {
        try {
            User.create("Maximo", "Cossetti", "eravenna@gmail.com", null, LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_PASSWORD);
        }

        try {
            User.create("Maximo", "Cosseti", "eravenna@gmail.com", "L0s S1m6l4d0r3s", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_PASSWORD_IS_INVALID);
        }

        try {
            User.create("Maximo", "Cosseti", "eravenna@gmail.com", "S1M", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_PASSWORD_IS_INVALID);
        }
    }

    private void emailValidations() {
        try {
            User.create("Maximo", "Cossetti", null, "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_EMAIL);
        }

        try {
            User.create("Maximo", "Cossetti", "eravennagmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_EMAIL_IS_INVALID + ": Missing final '@domain'");
        }
    }

    private void lastnameValidations() {
        try {
            User.create("Maximo", null, "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_LASTNAME);
        }

        try {
            User.create("Maximo", "UnApellidoDemasiadoLargoComoParaSerReal", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_LASTNAME_IS_INVALID);
        }

        try {
            User.create("Maximo", "", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_LASTNAME_IS_INVALID);
        }
    }

    private void nameValidations() {
        try {
            User.create(null, "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_IS_INVALID_WITHOUT_NAME);
        }

        try {
            User.create("UnNombreDemasiadoLargoComoParaSerReal", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_NAME_IS_INVALID);
        }

        try {
            User.create("", "Cossetti", "eravenna@gmail.com", "S1M6L4R", LocalDate.of(2002, 3, 21));
            fail();
        } catch (UserException error) {
            assertEquals(error.getMessage(), UserValidator.USER_NAME_IS_INVALID);
        }
    }

    @Test
    public void userMakesACashDeposit() {
        User user = userFactory.user();

        user.cashDeposit(100.00);

        assertEquals(100.00, user.balance(), 0);
    }

    @Test
    public void userMakesACreditDeposit() {
        User user = userFactory.user();
        YearMonth dueDate = YearMonth.now().plusMonths(1);
        String cardNumber = "4111111111111111";

        user.creditDeposit(100.00, dueDate, cardNumber);

        assertEquals(100.00, user.balance(), 0);
    }

    @Test
    public void userTakesSomeCashOut() {
        User user = userFactory.userWithCash(100.00);

        user.takeCash(50.00);
        assertEquals(50.00, user.balance(), 0);
    }

    @Test
    public void userRequireSomeCredit() {
        User user = userFactory.userWithCash(100.00);

        user.requireCredit(50.00);
        assertEquals(50.00, user.balance(), 0);
    }

    @Test
    public void userCannotTakeCashOrRequiereCreditWithoutFounds() {
        User user = userFactory.user();

        try {
            user.requireCredit(50.00);
            fail();
        } catch (MoneyAccountException error) {
            assertEquals(error.getMessage(), AccountManager.USER_LOW_BALANCE);
        }

        try {
            user.takeCash(50.00);
            fail();
        } catch (MoneyAccountException error) {
            assertEquals(error.getMessage(), AccountManager.USER_LOW_BALANCE);
        }
    }

    @Test
    public void userCanNotTakeALoanWithoutBeenDefaulter(){
        User user = userFactory.userIndebt();

        try {
            user.takeOutALoan();
            fail();
        } catch (MoneylenderException error) {
            assertEquals(error.getMessage(), Moneylender.USER_DEFAULTER);
        }
    }

    @Test
    public void userCanNotTakeALoanHavingAnotherLoanInProgress(){
        User user = userFactory.user();

        user.takeOutALoan();

        try {
            user.takeOutALoan();
            fail();
        } catch (MoneylenderException error) {
            assertEquals(error.getMessage(), Moneylender.USER_LOAN_IN_PROGRESS);
        }
    }

    @Test
    public void userCanTakeALoanBeenDutiful(){
        User user = userFactory.user();

        user.takeOutALoan();

        assertEquals(1000.00, user.balance(), 0);
    }

    @Test
    public void haveToPayLoanAndHaveMoney() {
        User user = userFactory.userWithCash(400.00);
        user.takeOutALoan();
        user.takeCash(1000.00);

        user.payLoan();

        assertFalse(user.isDefaulter());
        assertEquals(200.00, user.balance(), 0);
    }

    @Test
    public void haveToPayLoanAndDoNotHaveMoney() {
        User user = userFactory.userWithCash(100.00);

        user.payLoan();

        assertTrue(user.isDefaulter());
        assertEquals(100.00, user.balance(), 0);
    }

    @Test
    public void haveToPayLoanAndDoNotHaveMoneyFirstMonthButWillPayNextMonth() {
        User user = userFactory.userWithCash(100.00);
        user.takeOutALoan();
        user.takeCash(1000.00);

        user.payLoan();

        assertTrue(user.isDefaulter());
        assertEquals(100.00, user.balance(), 0);

        user.cashDeposit(400.00);

        user.payLoan();

        assertFalse(user.isDefaulter());
        assertEquals(100.00, user.balance(), 0);
    }

    @Test
    public void haveToPayThreeLoansAndOnlyCanPayOne() {
        User user = userFactory.userWithCash(100.00);
        user.takeOutALoan();
        user.takeCash(1000.00);

        user.payLoan();

        assertTrue(user.isDefaulter());
        assertEquals(100.00, user.balance(), 0);

        user.cashDeposit(200.00);

        user.payLoan();
        user.payLoan();

        assertTrue(user.isDefaulter());
        assertEquals(100.00, user.balance(), 0);
    }
}
