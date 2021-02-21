/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class KartuStokController {

    @FXML
    private TableView<StokBarang> barangTable;
    @FXML
    private TableColumn<StokBarang, String> tanggalColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokAwalColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAwalColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAsliAwalColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokMasukColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratMasukColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAsliMasukColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokKeluarColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratKeluarColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAsliKeluarColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokAkhirColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAkhirColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAsliAkhirColumn;
    @FXML
    private Label kodeGroup;
    @FXML
    private ComboBox<String> periode;
    private String gudang = "";
    private String kategori = "";
    private String jenis = "";
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<String> allPeriode = FXCollections.observableArrayList();

    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        stokAwalColumn.setCellValueFactory(cellData -> cellData.getValue().stokAwalProperty());
        stokAwalColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratAsliAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliAwalProperty());
        beratAsliAwalColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        stokMasukColumn.setCellValueFactory(cellData -> cellData.getValue().stokMasukProperty());
        stokMasukColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratAsliMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliMasukProperty());
        beratAsliMasukColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        stokKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().stokKeluarProperty());
        stokKeluarColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratAsliKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliKeluarProperty());
        beratAsliKeluarColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
        beratAsliAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliAkhirProperty());
        beratAsliAkhirColumn.setCellFactory(col -> new TableCell<StokBarang, Number>() {
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
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        barangTable.setItems(allBarang);
        allPeriode.add("Today");
        allPeriode.add("This Month");
        allPeriode.add("This Year");
        allPeriode.add("All");
        periode.setItems(allPeriode);
        periode.getSelectionModel().select("This Month");
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() - 80);
        stage.setWidth(mainApp.screenSize.getWidth() - 80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void setBarang(String kodeGudang, String kodeKategori, String kodeJenis) {
        kodeGroup.setText(kodeGudang + " " + kodeKategori + " " + kodeJenis);
        gudang = kodeGudang;
        kategori = kodeKategori;
        jenis = kodeJenis;
        getStokBarang();
    }

    @FXML
    private void getStokBarang() {
        try (Connection con = Koneksi.getConnection()){
            allBarang.clear();
            allBarang.addAll(StokBarangDAO.getKartuStokBarangBarcode(con, 
                    periode.getSelectionModel().getSelectedItem(), gudang, kategori, jenis));
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
