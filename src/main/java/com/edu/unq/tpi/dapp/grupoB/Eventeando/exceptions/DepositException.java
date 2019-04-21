package com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions;

public class DepositException extends RuntimeException {
    public DepositException(String depositErrorMessage) { super(depositErrorMessage); }
}
