package com.wocken.transactioncategorizer;

public enum ExportType {
    CREDIT("Credit"),
    DEBIT("Debit");

    private final String name;

    ExportType(String name) {
        this.name = name;
    }
}
