package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseDao extends JpaRepository<Expense, Long> {
    List<Expense> findAllById(Iterable<Long> ids);
}
