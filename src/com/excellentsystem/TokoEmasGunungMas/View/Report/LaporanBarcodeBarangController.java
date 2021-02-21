/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class LaporanBarcodeBarangController {

    @FXML
    private TreeTableView<Barang> barcodeBarangHeadTable;
    @FXML
    private TreeTableColumn<Barang, String> kodeBarcodeColumn;
    @FXML
    private TreeTableColumn<Barang, String> tglBarcodeColumn;
    @FXML
    private TreeTableColumn<Barang, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<Barang, String> keteranganColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeGudangColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeInternColumn;
    @FXML
    private TreeTableColumn<Barang, String> kadarColumn;
    @FXML
    private TreeTableColumn<Barang, Number> beratColumn;
    @FXML
    private TreeTableColumn<Barang, Number> beratAsliColumn;
    @FXML
    private TreeTableColumn<Barang, Number> nilaiPokokColumn;
    @FXML
    private TreeTableColumn<Barang, Number> hargaJualColumn;
    @FXML
    private TreeTableColumn<Barang, String> userBarcodeColumn;
    @FXML
    private TreeTableColumn<Barang, String> statusBarangColumn;

    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private Label totalQtyField;
    @FXML
    private Label totalBeratField;
    @FXML
    private Label totalBeratAsliField;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private TextField searchField;
    private Main mainApp;
    final TreeItem<Barang> root = new TreeItem<>();
    private ObservableList<Barang> allBarcodeBarang = FXCollections.observableArrayList();
    private ObservableList<Barang> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarcodeProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(
                                        cellData.getValue().getValue().getBarcodeDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().keteranganProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeJenisProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeGudangProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratProperty());
        beratColumn.setCellFactory(col -> new TreeTableCell<Barang, Number>() {
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
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> new TreeTableCell<Barang, Number>() {
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
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> new TreeTableCell<Barang, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> new TreeTableCell<Barang, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        statusBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().statusBarangProperty());
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().barcodeByProperty());

        tglStartPicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        tglStartPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglStartPicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SUNDAY) {
                            this.setStyle("-fx-background-color: derive(RED, 150%);");
                        }
                        if (item.equals(LocalDate.now())) {
                            this.setStyle(" -fx-font-weight:bold;");
                        }
                        if (item.isAfter(LocalDate.now())) {
                            this.setDisable(true);
                        }
                        if (item.isAfter(tglAkhirPicker.getValue())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        });
        tglAkhirPicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        tglAkhirPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglAkhirPicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SUNDAY) {
                            this.setStyle("-fx-background-color: derive(RED, 150%);");
                        }
                        if (item.equals(LocalDate.now())) {
                            this.setStyle(" -fx-font-weight:bold;");
                        }
                        if (item.isAfter(LocalDate.now())) {
                            this.setDisable(true);
                        }
                        if (item.isBefore(tglStartPicker.getValue())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        });
        allBarcodeBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarcodeBarang();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchBarcodeBarang();
                });
        filterData.addAll(allBarcodeBarang);
    }

    public void setMainApp(Main mainApp) {
        try {
            this.mainApp = mainApp;
            ObservableList<String> groupBy = FXCollections.observableArrayList();
            groupBy.add("Tanggal");
            groupBy.add("User");
            groupBy.add("Kategori Barang");
            groupBy.add("Jenis Barang");
            groupByCombo.setItems(groupBy);
            groupByCombo.getSelectionModel().select("Tanggal");
            getBarcodeBarang();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void getBarcodeBarang() {
        try(Connection con = Koneksi.getConnection()){
            allBarcodeBarang.clear();
            allBarcodeBarang.addAll(BarangDAO.getAllByTglBarcode(con, 
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString()));
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

    private void searchBarcodeBarang() {
        try {
            filterData.clear();
            for (Barang temp : allBarcodeBarang) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getKodeBarcode())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getBarcodeDate())))
                            || checkColumn(temp.getNamaBarang())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getKodeJenis())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeIntern())
                            || checkColumn(temp.getKadar())
                            || checkColumn(temp.getStatusBarang())
                            || checkColumn(gr.format(temp.getBerat()))
                            || checkColumn(gr.format(temp.getBeratAsli()))
                            || checkColumn(rp.format(temp.getNilaiPokok()))
                            || checkColumn(rp.format(temp.getHargaJual()))
                            || checkColumn(temp.getBarcodeBy())) {
                        filterData.add(temp);
                    }
                }
            }
            setTable();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void setTable() throws Exception {
        if (barcodeBarangHeadTable.getRoot() != null) {
            barcodeBarangHeadTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (Barang temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getBarcodeDate().substring(0, 10))) {
                    groupBy.add(temp.getBarcodeDate().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                if (!groupBy.contains(temp.getBarcodeBy())) {
                    groupBy.add(temp.getBarcodeBy());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                if (!groupBy.contains(temp.getKodeKategori())) {
                    groupBy.add(temp.getKodeKategori());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                if (!groupBy.contains(temp.getKodeJenis())) {
                    groupBy.add(temp.getKodeJenis());
                }
            }
        }
        double totalBerat = 0;
        double totalBeratAsli = 0;
        int totalQty = 0;
        for (String temp : groupBy) {
            Barang head = new Barang();
            head.setKodeBarcode(temp);
            TreeItem<Barang> parent = new TreeItem<>(head);
            double berat = 0;
            double beratAsli = 0;
            for (Barang detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getBarcodeDate().substring(0, 10))) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalQty = totalQty + 1;
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                    if (temp.equals(detail.getBarcodeBy())) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalQty = totalQty + 1;
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                    if (temp.equals(detail.getKodeKategori())) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalQty = totalQty + 1;
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                    if (temp.equals(detail.getKodeJenis())) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalQty = totalQty + 1;
                    }
                }
            }
            parent.getValue().setBerat(berat);
            parent.getValue().setBeratAsli(beratAsli);
            root.getChildren().add(parent);
        }
        barcodeBarangHeadTable.setRoot(root);
        totalBeratAsliField.setText(gr.format(totalBeratAsli));
        totalBeratField.setText(gr.format(totalBerat));
        totalQtyField.setText(gr.format(totalQty));
    }
}
