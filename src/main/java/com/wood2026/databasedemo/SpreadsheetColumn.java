package com.wood2026.databasedemo;

public class SpreadsheetColumn {
    private String label;
    private final int index;
    public SpreadsheetColumn(String label, int index) {
        this.label = label;
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
