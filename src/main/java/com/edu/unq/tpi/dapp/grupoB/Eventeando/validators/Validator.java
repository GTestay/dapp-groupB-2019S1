package com.edu.unq.tpi.dapp.grupoB.Eventeando.validators;

import java.util.List;

public class Validator {

    protected <Type> Type validateNullityOf(Type field, RuntimeException exception) {
        if (field == null) {
            throw exception;
        }
        return field;
    }

    protected String validateLenghtBetween(String field, int lowerLimmit, int upperLimmit, RuntimeException exception) {
        int fieldLenght = field.length();

        if (fieldLenght < lowerLimmit || fieldLenght > upperLimmit) {
            throw exception;
        }
        return field;
    }

    protected <T> List<T> validateEmptinessOf(List<T> aList, RuntimeException aRuntimeExceptionToBeThrown) {
        if (aList == null || aList.isEmpty()) {
            throw aRuntimeExceptionToBeThrown;
        }
        return aList;
    }

    protected String validateEmptinessOf(String aString, RuntimeException aRuntimeExceptionToBeThrown) {
        if (aString == null || aString.isEmpty()) {
            throw aRuntimeExceptionToBeThrown;
        }
        return aString;
    }

    protected Double validateNegativiyOf(Double aNumber, RuntimeException exception) {
        if (aNumber == null || aNumber < 0) {
            throw exception;
        }
        return aNumber;
    }

}
