package com.wood2026.databasedemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SpreadsheetReader {
    private final TableView<SpreadsheetRow> spreadsheetTable;
    private Spreadsheet spreadsheet;
    private ObservableList<SpreadsheetRow> tableData;

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
        tableData = FXCollections.observableArrayList();
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
            e.printStackTrace();
        }
    }
    /*
    private void toSheet(){
        spreadsheetTable.getColumns().clear();
        for (Map.Entry<Integer, SpreadsheetColumn> entry
                : spreadsheet.getColumns().entrySet()) {

            int columnIndex = entry.getKey();
            SpreadsheetColumn spreadsheetColumn = entry.getValue();

            TableColumn<SpreadsheetRow, String> column =
                    new TableColumn<>(spreadsheetColumn.getLabel());

            // Set cell value factory here

            spreadsheetTable.getColumns().add(column);
        }
        for (int r = 0; r < 100; r++) {
            SpreadsheetRow row = spreadsheet.getRows().get(r);

            if (row == null) {
                row = new SpreadsheetRow(r);
                spreadsheet.setRow(r, row);
            }

            tableData.add(row);
        }

        spreadsheetTable.setItems(tableData);
    }
     */
}
