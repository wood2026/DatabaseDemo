package com.wood2026.databasedemo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
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
        spreadsheetTable.setEditable(true);
        spreadsheetTable.getSelectionModel().setCellSelectionEnabled(true);
        initializeTable();
    }

    private void initializeTable() {
        ObservableList<SpreadsheetRow> visibleRows =
                FXCollections.observableArrayList();
        for (int columnIndex = 0; columnIndex < 52; columnIndex++) {

            TableColumn<SpreadsheetRow, String> column = createColumn(columnIndex);

            enableCellEditing(column, columnIndex);
            spreadsheetTable.getColumns().add(column);
        }
        for (int r = 0; r < 100; r++) {
            SpreadsheetRow row = new SpreadsheetRow(r);

            spreadsheet.setRow(r, row);
            visibleRows.add(row);
        }

        spreadsheetTable.setItems(visibleRows);
    }

    private static TableColumn<SpreadsheetRow, String> createColumn(int columnIndex) {
        TableColumn<SpreadsheetRow, String> column =
                new TableColumn<>("Column " + (columnIndex + 1));
        column.setSortable(false);
        column.setCellValueFactory(data -> {
            SpreadsheetCell cell =
                    data.getValue().getCell(columnIndex);

            String value = cell == null || cell.getValue() == null
                    ? ""
                    : cell.getValue().toString();
            return new ReadOnlyStringWrapper(value);
        });
        return column;
    }

    private void enableCellEditing(TableColumn<SpreadsheetRow, String> column,
                                   int columnIndex) {
        column.setEditable(true);
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            SpreadsheetRow row = event.getRowValue();
            SpreadsheetCell cell = row.getCell(columnIndex);
            if (cell == null) {
                cell = new SpreadsheetCell(new CellAddress(row.getIndex(), columnIndex));
                row.setCell(columnIndex, cell);
            }
            cell.setValue(event.getNewValue());
            spreadsheetTable.refresh();
        });
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
        spreadsheetReader.readExcel(file, spreadsheet);
    }
}