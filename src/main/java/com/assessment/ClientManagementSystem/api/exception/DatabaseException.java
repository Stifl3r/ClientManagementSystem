package com.assessment.ClientManagementSystem.api.exception;

import lombok.Getter;

@Getter
public class DatabaseException extends GenericException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, int errorCode) {
        super(message, errorCode);
    }
}
