package com.wood2026.databasedemo;

public class SpreadsheetCell {

    private final CellAddress address;
    private Object value;
    public SpreadsheetCell(CellAddress address) {
        this.address = address;
        value = null;
    }
    public SpreadsheetCell(CellAddress address, Object value) {
        this.address = address;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public CellAddress getAddress() {
        return address;
    }
}
