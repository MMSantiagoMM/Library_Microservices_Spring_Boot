package com.practice.borrowing.messages;

public enum FileMessage {
    INCORRECT_FILE("Must select a PDF file. The current format file is not allowed");

    public String message;

    FileMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
