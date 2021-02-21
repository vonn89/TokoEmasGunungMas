/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class HancurBarangController {

    @FXML
    private TableView<Barang> barangTable;
    @FXML
    private TableColumn<Barang, String> kodeBarcodeColumn;
    @FXML
    private TableColumn<Barang, String> namaBarangColumn;
    @FXML
    private TableColumn<Barang, String> kodeKategoriColumn;
    @FXML
    private TableColumn<Barang, String> kodeJenisColumn;
    @FXML
    private TableColumn<Barang, String> kodeGudangColumn;
    @FXML
    private TableColumn<Barang, String> kodeInternColumn;
    @FXML
    private TableColumn<Barang, String> kadarColumn;
    @FXML
    private TableColumn<Barang, Number> beratColumn;
    @FXML
    private TableColumn<Barang, Number> beratAsliColumn;

    public Label totalQty;
    public Label totalBerat;
    public Label totalBeratAsli;
    public Label totalBeratLabel;
    public Button saveButton;
    public AnchorPane anchorPane;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public ObservableList<Barang> allBarang = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> new TableCell<Barang, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(gr.format(value.doubleValue()));
                }
            }
        });
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> new TableCell<Barang, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(gr.format(value.doubleValue()));
                }
            }
        });
        barangTable.setRowFactory(table -> {
            TableRow<Barang> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem delete = new MenuItem("Hapus Barang");
            delete.setOnAction((ActionEvent event) -> {
                allBarang.remove(row.getItem());
                hitungTotal();
                barangTable.refresh();
            });
            MenuItem refresh = new MenuItem("Refresh");
            refresh.setOnAction((ActionEvent event) -> {
                barangTable.refresh();
            });
            rowMenu.getItems().addAll(delete, refresh);
            row.setContextMenu(rowMenu);
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                        && mouseEvent.getClickCount() == 2) {
                    if (row.getItem().isStatus()) {
                        row.getItem().setStatus(false);
                    } else {
                        row.getItem().setStatus(true);
                    }
                }
            });
            return row;
        });
        barangTable.setItems(allBarang);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() - 80);
        stage.setWidth(mainApp.screenSize.getWidth() - 80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void setBarang(List<Barang> x) {
        allBarang.addAll(x);
        hitungTotal();
    }

    private void hitungTotal() {
        double beratAsli = 0;
        double berat = 0;
        int qty = 0;
        for (Barang b : allBarang) {
            if (b.isStatus()) {
                beratAsli = beratAsli + b.getBeratAsli();
                berat = berat + b.getBerat();
                qty = qty + 1;
            }
        }
        totalBerat.setText(gr.format(berat));
        totalQty.setText(gr.format(qty));
        totalBeratAsli.setText(gr.format(beratAsli));
        totalBeratLabel.setText(gr.format(beratAsli + (qty * Main.sistem.getBeratLabel())));
    }

    @FXML
    private void cancel() {
        mainApp.closeDialog(owner, stage);
    }
}
