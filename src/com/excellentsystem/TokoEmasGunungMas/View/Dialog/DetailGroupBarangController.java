/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.GudangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglNormal;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.Gudang;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DetailGroupBarangController {

    public TableView<StokBarang> barangTable;
    @FXML
    private TableColumn<StokBarang, String> kodeBarcodeColumn;
    @FXML
    private TableColumn<StokBarang, String> namaBarangColumn;
    @FXML
    private TableColumn<StokBarang, String> kodeKategoriColumn;
    @FXML
    private TableColumn<StokBarang, String> kodeJenisColumn;
    @FXML
    private TableColumn<StokBarang, String> kodeGudangColumn;
    @FXML
    private TableColumn<StokBarang, String> kodeInternColumn;
    @FXML
    private TableColumn<StokBarang, String> kadarColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAsliColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratKemasanColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label kodeGroup;
    @FXML
    private Label tanggal;
    @FXML
    private Label totalQty;
    @FXML
    private Label totalBerat;
    @FXML
    private Label totalBeratAsli;
    @FXML
    private Label totalBeratLabel;
    @FXML
    private Label beratTotalLabel;
    @FXML
    private Label beratTotal2Label;
    private double beratGudang = 0;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    private ObservableList<StokBarang> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().namaBarangProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kadarProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeInternProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        beratKemasanColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().beratKemasanProperty());
        beratKemasanColumn.setCellFactory(col -> getTableCell(gr));

        allBarang.addListener((ListChangeListener.Change<? extends StokBarang> change) -> {
            searchStokBarang();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchStokBarang();
                });
        filterData.addAll(allBarang);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            barangTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<StokBarang> row = new TableRow<StokBarang>() {
                @Override
                public void updateItem(StokBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            barangTable.refresh();
                        });
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        barangTable.setItems(filterData);
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

    public void setBarang(String kodeGudang, String kodeKategori, String kodeJenis, String tgl) {
        try (Connection con = Koneksi.getConnection()) {
            kodeGroup.setText(kodeGudang + " " + kodeKategori + " " + kodeJenis);
            tanggal.setText(tglNormal.format(tglBarang.parse(tgl)));
            allBarang.clear();
            List<StokBarang> x = StokBarangDAO.getAllStokBarcode(con,
                    tgl, kodeGudang, kodeKategori, kodeJenis);
            for (StokBarang s : x) {
                if (s.getStokAkhir() == 1) {
                    Barang b = BarangDAO.get(con, s.getKodeBarcode());
                    s.setBarang(b);
                    allBarang.add(s);
                }
            }
            if (!"".equals(kodeGudang)) {
                Gudang gudang = GudangDAO.get(con, kodeGudang);
                beratGudang = gudang.getBeratBaki();
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchStokBarang() {
        try {
            filterData.clear();
            for (StokBarang temp : allBarang) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getKodeJenis())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getBarang().getKodeBarcode())
                            || checkColumn(temp.getBarang().getNamaBarang())
                            || checkColumn(temp.getBarang().getKeterangan())
                            || checkColumn(temp.getBarang().getKodeIntern())
                            || checkColumn(temp.getBarang().getKadar())
                            || checkColumn(gr.format(temp.getBarang().getBeratKemasan()))
                            || checkColumn(gr.format(temp.getBarang().getBerat()))
                            || checkColumn(gr.format(temp.getBarang().getBeratAsli()))) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void hitungTotal() {
        double totalBeratAll = 0;
        double beratAsli = 0;
        double berat = 0;
        int qty = 0;
        for (StokBarang b : filterData) {
            totalBeratAll = totalBeratAll + b.getBeratAsliAkhir() + b.getBarang().getBeratKemasan();
            beratAsli = beratAsli + b.getBeratAsliAkhir();
            berat = berat + b.getBeratAkhir();
            qty = qty + b.getStokAkhir();
        }
        totalBerat.setText(gr.format(berat));
        totalQty.setText(gr.format(qty));
        totalBeratAsli.setText(gr.format(beratAsli));
        totalBeratLabel.setText(gr.format(totalBeratAll + (qty * Main.sistem.getBeratLabel())));
        if (beratGudang != 0) {
            beratTotalLabel.setText(gr.format(totalBeratAll + (qty * Main.sistem.getBeratLabel()) + beratGudang));
            beratTotalLabel.setVisible(true);
            beratTotal2Label.setVisible(true);
        }
    }

    @FXML
    private void cancel() {
        mainApp.closeDialog(owner, stage);
    }

}
