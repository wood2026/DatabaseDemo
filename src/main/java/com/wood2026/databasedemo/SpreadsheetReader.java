package com.wood2026.databasedemo;


import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

public class SpreadsheetReader {
    private final TableView<SpreadsheetRow> spreadsheetTable;
    private Spreadsheet spreadsheet;

    public SpreadsheetReader(TableView<SpreadsheetRow> tableView) {
        spreadsheetTable = tableView;
    }

    public void readExcel(File file, Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
        if (file != null) {
            loadExcel(file);
        }
    }

    private void loadExcel(File file) {
        in(file);
        spreadsheetTable.refresh();
    }

    private void in(File file) {

        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            FormulaEvaluator evaluator =
                    workbook.getCreationHelper().createFormulaEvaluator();
            for (Row row : sheet) {
                int rowIndex = row.getRowNum();
                SpreadsheetRow spreadsheetRow =
                        spreadsheet.getRows().get(rowIndex);
                if (spreadsheetRow == null) {
                    spreadsheetRow = new SpreadsheetRow(rowIndex);
                    spreadsheet.setRow(rowIndex, spreadsheetRow);
                }
                for (Cell cell : row) {
                    int columnIndex = cell.getColumnIndex();
                    String value = formatter.formatCellValue(cell, evaluator);
                    if (value.isEmpty()) {
                        continue;
                    }
                    CellAddress address =
                            new CellAddress(rowIndex, columnIndex);
                    SpreadsheetCell spreadsheetCell =
                            new SpreadsheetCell(address);
                    spreadsheetCell.setValue(value);
                    spreadsheetRow.setCell(columnIndex, spreadsheetCell);
                }
                spreadsheet.setRow(rowIndex, spreadsheetRow);
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
