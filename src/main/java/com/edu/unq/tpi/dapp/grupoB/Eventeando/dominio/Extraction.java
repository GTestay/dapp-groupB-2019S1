package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Extraction")
public class Extraction extends MoneyTransaction {
    public static Extraction create(User user, LocalDate date, double amount) {
        Extraction instance = new Extraction();

        return (Extraction) validateInstance(user, date, amount, instance);
    }

    @Override
    public double transactionalValue() { return -amount; }
}
