package com.wood2026.databasedemo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Spreadsheet {
    private Map<Integer, SpreadsheetRow> rows;
    private Map<Integer, SpreadsheetColumn> columns;
    public Spreadsheet() {
        rows = new HashMap<>();
        columns = new HashMap<>();
    }
    public Spreadsheet(Map<Integer, SpreadsheetRow> r, Map<Integer, SpreadsheetColumn> c) {
        rows = r;
        columns = c;
    }

    public Map<Integer, SpreadsheetRow> getRows() {
        return rows;
    }

    public void setRows(Map<Integer, SpreadsheetRow> rows) {
        this.rows = rows;
    }

    public Map<Integer, SpreadsheetColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<Integer, SpreadsheetColumn> columns) {
        this.columns = columns;
    }
}
