package com.joshwood.databasedemo;

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

public class SpreadsheetReader {
    private TableView<ObservableList<String>> spreadsheetTable;
    //private final ObservableList<ObservableList<String>> tableData;
    public SpreadsheetReader(TableView<ObservableList<String>> tableView) {
        spreadsheetTable = tableView;
    }

    public void ReadExcel(File file) {
        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        if (file != null) {
            try (Workbook workbook = WorkbookFactory.create(file)) {
                Sheet sheet = workbook.getSheetAt(0);
                int columnCount = sheet.getRow(0).getLastCellNum();
                DataFormatter formatter = new DataFormatter();
                FormulaEvaluator evaluator =
                        workbook.getCreationHelper().createFormulaEvaluator();

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    ObservableList<String> rowData = FXCollections.observableArrayList();
                    for (int c = 0; c < columnCount; c++) {
                        Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (cell == null) {
                            rowData.add("");
                        } else
                            rowData.add(formatter.formatCellValue(cell, evaluator));
                    }
                    tableData.add(rowData);
                    //System.out.println();
                }
                spreadsheetTable.getColumns().clear();

                for (int c = 0; c < columnCount; c++) {
                    final int columnIndex = c;

                    Cell headerCell = sheet.getRow(0).getCell(c);

                    String headerName = formatter.formatCellValue(headerCell);

                    TableColumn<ObservableList<String>, String> column =
                            new TableColumn<>(headerName);

                    column.setCellValueFactory(cellData ->
                            new ReadOnlyStringWrapper(
                                    cellData.getValue().get(columnIndex)
                            )
                    );

                    spreadsheetTable.getColumns().add(column);
                }

                spreadsheetTable.setItems(tableData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
