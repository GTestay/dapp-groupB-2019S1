package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.MoneyTransaction;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyTransactionDao extends JpaRepository<MoneyTransaction, Long> {
    List<MoneyTransaction> findAllByUser(User user);
}
