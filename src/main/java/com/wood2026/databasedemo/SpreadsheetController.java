package com.wood2026.databasedemo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SpreadsheetController {
    @FXML
    private TableView<SpreadsheetRow> spreadsheetTable;
    private Spreadsheet spreadsheet;

    @FXML
    public void initialize() {
        spreadsheet = new Spreadsheet();
        initializeTable();
    }

    private void initializeTable() {
        ObservableList<SpreadsheetRow> visibleRows =
                FXCollections.observableArrayList();
        for (int c = 0; c < 52; c++) {
            final int columnIndex = c;

            TableColumn<SpreadsheetRow, String> column =
                    new TableColumn<>("Column " + (c + 1));

            column.setCellValueFactory(data -> {
                SpreadsheetCell cell =
                        data.getValue().getCell(columnIndex);

                String value = cell == null || cell.getValue() == null
                        ? ""
                        : cell.getValue().toString();

                return new ReadOnlyStringWrapper(value);
            });

            spreadsheetTable.getColumns().add(column);
        }
        for (int r = 0; r < 100; r++) {
            SpreadsheetRow row = new SpreadsheetRow(r);

            spreadsheet.setRow(r, row);
            visibleRows.add(row);
        }

        spreadsheetTable.setItems(visibleRows);
    }

    @FXML
    protected void exitApplication(){
        Platform.exit();
    }

    @FXML
    protected void openSpreadsheet() {
        Stage stage = (Stage) spreadsheetTable.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Spreadsheet");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(stage);
        SpreadsheetReader spreadsheetReader = new SpreadsheetReader(spreadsheetTable);
        spreadsheetReader.readExcel(file);
    }
}