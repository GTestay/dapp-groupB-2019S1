package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Expense {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    private String name;
    @JsonProperty
    private Double cost;

    private Expense() {

    }

    private Expense(String name, Double cost) {

        this.name = name;
        this.cost = cost;
    }

    public static Expense create(String name, Double cost) {
        ExpenseValidator validator = new ExpenseValidator();
        validator.validateCost(cost);
        validator.validateName(name);

        return new Expense(name, cost);
    }

    public String name() {
        return name;
    }

    public Double cost() {
        return cost;
    }

    public Long id() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }
}
