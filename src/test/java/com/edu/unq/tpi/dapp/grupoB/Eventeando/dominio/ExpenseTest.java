package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import org.junit.Test;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.ExpenseValidator.ERROR_CAN_NOT_CREATE_AN_EXPENSE_WHOSE_PRICE_IS_NEGATIVE;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseTest {

    @Test
    public void anExpenseCanNotBeCreatedWithNegativeCost() {
        try {
            Expense.create("aName", -1.00);
            fail();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(ERROR_CAN_NOT_CREATE_AN_EXPENSE_WHOSE_PRICE_IS_NEGATIVE);
        }
    }

    @Test
    public void anExpenseCanNotBeCreatedWithoutName() {
        try {
            Expense.create(null, 1.00);
            fail();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(ExpenseValidator.ERROR_EXPENSE_IS_INVALID_WITHOUT_NAME);
        }
    }


}
