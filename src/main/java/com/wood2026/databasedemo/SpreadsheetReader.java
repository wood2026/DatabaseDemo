package com.wood2026.databasedemo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SpreadsheetReader {
    private final TableView<SpreadsheetRow> spreadsheetTable;
    private ObservableList<SpreadsheetRow> tableData;
    public SpreadsheetReader(TableView<SpreadsheetRow> tableView) {
        spreadsheetTable = tableView;
    }

    public void readExcel(File file) {
        if (file != null) {
           loadExcel(file);
        }
    }
    private void loadExcel(File file) {
        tableData = FXCollections.observableArrayList();
        toSheet(in(file));
    }
    private Spreadsheet in(File file) {
        Spreadsheet spreadsheet = new Spreadsheet();

        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            FormulaEvaluator evaluator =
                    workbook.getCreationHelper().createFormulaEvaluator();
            for (Row row : sheet) {
                int rowIndex = row.getRowNum();
                SpreadsheetRow spreadsheetRow =
                        new SpreadsheetRow(rowIndex);
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
        return spreadsheet;
    }
    private void toSheet(Spreadsheet sheet){
        spreadsheetTable.getColumns().clear();
        for (Map.Entry<Integer, SpreadsheetColumn> entry
                : sheet.getColumns().entrySet()) {

            int columnIndex = entry.getKey();
            SpreadsheetColumn spreadsheetColumn = entry.getValue();

            TableColumn<SpreadsheetRow, String> column =
                    new TableColumn<>(spreadsheetColumn.getLabel());

            // Set cell value factory here

            spreadsheetTable.getColumns().add(column);
        }
        for (int r = 0; r < 100; r++) {
            SpreadsheetRow row = sheet.getRows().get(r);

            if (row == null) {
                row = new SpreadsheetRow(r);
                sheet.setRow(r, row);
            }

            tableData.add(row);
        }

        spreadsheetTable.setItems(tableData);
    }
}
