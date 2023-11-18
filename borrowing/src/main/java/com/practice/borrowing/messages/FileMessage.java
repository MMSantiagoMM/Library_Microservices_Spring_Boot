package com.practice.borrowing.messages;

public enum FileMessage {
    PDF_FILE_TYPE("application/pdf"),
    INCORRECT_FILE("Must select a PDF file. The current format file is not allowded");

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
