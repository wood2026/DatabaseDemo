package com.wood2026.databasedemo;

import java.util.Map;

public class SpreadsheetRow {
    private Map<Integer, SpreadsheetCell> cells;
    private final int index;
    public SpreadsheetRow(int index) {
        this.index = index;
        this.cells = null;
    }

    public Map<Integer, SpreadsheetCell> getAllCells() {
        return cells;
    }

    public void setAllCells(Map<Integer, SpreadsheetCell> cells) {
        this.cells = cells;
    }

    public void setCell(int column, SpreadsheetCell cell) {
        cells.put(column, cell);
    }

    public SpreadsheetCell getCell(int column) {
        return cells.get(column);
    }

    public int getIndex() {
        return index;
    }
}
