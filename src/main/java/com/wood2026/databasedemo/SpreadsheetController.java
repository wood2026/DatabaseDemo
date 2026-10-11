package com.wood2026.databasedemo;

import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SpreadsheetController {
    @FXML
    private TableView<ObservableList<String>> spreadsheetTable;

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