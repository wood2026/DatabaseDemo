package com.wood2026.databasedemo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;

public class SpreadsheetController {
    @FXML
    private TableView<SpreadsheetRow> spreadsheetTable;
    private Spreadsheet spreadsheet;
    private boolean nonsenseEnabled;

    @FXML
    public void initialize() {
        spreadsheet = new Spreadsheet();
        spreadsheetTable.setEditable(true);
        spreadsheetTable.getSelectionModel().setCellSelectionEnabled(true);
        spreadsheetTable.setColumnResizePolicy(
                TableView.UNCONSTRAINED_RESIZE_POLICY
        );
        initializeTable();
    }

    @FXML
    public void labelNonsense() {
        nonsenseEnabled = !nonsenseEnabled;
        List<String> words = nonsenseEnabled
                ? WordGenerator.getUniqueRandomWords(spreadsheetTable.getColumns().size())
                : List.of();
        for (int i = 0; i < spreadsheetTable.getColumns().size(); i++) {
            TableColumn<SpreadsheetRow, ?> column =
                    spreadsheetTable.getColumns().get(i);

            String label = nonsenseEnabled
                    ? words.get(i)
                    : labelMaker(i);
            Text measurement = new Text(label);
            measurement.setFont(javafx.scene.text.Font.font(12));

            double width = Math.max(80,
                    measurement.getLayoutBounds().getWidth() + 30);

            column.setText(label);
            column.setMinWidth(width);
            column.setPrefWidth(width);
            column.setMaxWidth(Double.MAX_VALUE);
        }
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
                new TableColumn<>(labelMaker(columnIndex));
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

    private static String labelMaker(int columnIndex) {
        char alpha = 'A';
        char beta = 'A';
        String ret;
        if (columnIndex > 25) {
            beta += (char)(columnIndex - 26);
            ret = String.valueOf(alpha) + beta;
        } else {
            alpha += (char)columnIndex;
            ret = String.valueOf(alpha);
        }
        return ret;
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