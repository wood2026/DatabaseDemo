package com.wood2026.databasedemo;

import java.util.HashMap;
import java.util.Map;

public class SpreadsheetRow {
    private final Map<Integer, SpreadsheetCell> cells = new HashMap<>();
    private final int index;
    public SpreadsheetRow(int index) {
        this.index = index;
    }

    public Map<Integer, SpreadsheetCell> getAllCells() {
        return cells;
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
