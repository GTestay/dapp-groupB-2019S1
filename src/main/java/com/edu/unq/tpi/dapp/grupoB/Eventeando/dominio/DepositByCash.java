package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("DepositByCash")
public class DepositByCash extends Deposit {
    public static DepositByCash create(User user, LocalDate date, double amount) {
        DepositByCash instance = new DepositByCash();

        return (DepositByCash) validateInstance(user, date, amount, instance);
    }
}
