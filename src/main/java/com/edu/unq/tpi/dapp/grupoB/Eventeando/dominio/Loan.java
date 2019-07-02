package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Loan")
public class Loan extends MoneyTransaction {

    public static Loan create(User user) {
        Loan instance = new Loan();

        validateInstance(user, LocalDate.now(), Moneylender.LOAN_COST, instance);

        return instance;
    }

    @Override
    public double transactionalValue() { return amount; }

    public boolean isOwner(User owner) { return owner == user; }
}
