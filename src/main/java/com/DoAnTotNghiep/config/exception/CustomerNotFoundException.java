package com.DoAnTotNghiep.config.exception;

public class CustomerNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE = "Could not find any account with this email";
    /**
     * Constructor
     */
    public CustomerNotFoundException() {
        super(MESSAGE);
    }

    /**
     * Constructor
     * @param message
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
