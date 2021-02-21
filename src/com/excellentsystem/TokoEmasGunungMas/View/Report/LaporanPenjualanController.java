/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanHead;
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
public class LaporanPenjualanController {

    @FXML
    private TreeTableView<PenjualanDetail> penjualanTable;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> noPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> tglPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeSalesColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodePelangganColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> namaColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> alamatColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> noTelpColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> totalBeratColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> grandtotalColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> catatanColumn;

    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeBarcodeColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeGudangColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeInternColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kadarColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> beratColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> beratAsliColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> nilaiPokokColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> hargaKompColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> hargaJualColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> userBarcodeColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> tglBarcodeColumn;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private Label totalBeratField;
    @FXML
    private Label totalNilaiPokokField;
    @FXML
    private Label totalPenjualanField;
    @FXML
    private DatePicker mulaiTglPicker;
    @FXML
    private DatePicker akhirTglPicker;
    final TreeItem<PenjualanDetail> root = new TreeItem<>();
    private ObservableList<PenjualanDetail> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPenjualanColumn.setCellValueFactory(param -> param.getValue().getValue().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getPenjualan().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        kodeSalesColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().kodeSalesProperty());
        kodePelangganColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().kodePelangganProperty());
        namaColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().namaProperty());
        alamatColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().alamatProperty());
        noTelpColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().noTelpProperty());
        totalBeratColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> new TreeTableCell<PenjualanDetail, Number>() {
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
        grandtotalColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().grandtotalProperty());
        grandtotalColumn.setCellFactory(col -> new TreeTableCell<PenjualanDetail, Number>() {
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
        catatanColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().catatanProperty());

        kodeBarcodeColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().namaBarangProperty());
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().keteranganProperty());
        kodeKategoriColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeJenisProperty());
        kodeGudangColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeGudangProperty());
        kodeInternColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeInternProperty());
        kadarColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kadarProperty());
        beratColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().beratProperty());
        beratColumn.setCellFactory(col -> new TreeTableCell<PenjualanDetail, Number>() {
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
        beratAsliColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> new TreeTableCell<PenjualanDetail, Number>() {
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
        nilaiPokokColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> new TreeTableCell<PenjualanDetail, Number>() {
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
        hargaKompColumn.setCellValueFactory(param -> param.getValue().getValue().hargaKompProperty());
        hargaKompColumn.setCellFactory(col -> new TreeTableCell<PenjualanDetail, Number>() {
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
        hargaJualColumn.setCellValueFactory(param -> param.getValue().getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> new TreeTableCell<PenjualanDetail, Number>() {
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
        userBarcodeColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().barcodeByProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getBarang().getBarcodeDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        mulaiTglPicker.setConverter(new StringConverter<LocalDate>() {
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
        mulaiTglPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        mulaiTglPicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                        if (item.isAfter(akhirTglPicker.getValue())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        });
        akhirTglPicker.setConverter(new StringConverter<LocalDate>() {
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
        akhirTglPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        akhirTglPicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                        if (item.isBefore(mulaiTglPicker.getValue())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanDetail> change) -> {
            searchPenjualanDetail();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPenjualanDetail();
                });
        filterData.addAll(allPenjualan);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("No Penjualan");
        groupBy.add("Tanggal");
        groupBy.add("Sales");
        groupBy.add("Kategori Barang");
        groupBy.add("Jenis Barang");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Penjualan");
        getPenjualan();
    }

    @FXML
    private void getPenjualan() {
        try (Connection con = Koneksi.getConnection()) {
            allPenjualan.clear();
            List<PenjualanDetail> allDetail = PenjualanDetailDAO.getAllByTglPenjualanAndStatus(con, 
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString(), "true");
            List<PenjualanHead> listPenjualanHead = PenjualanHeadDAO.getAllByTglPenjualanAndStatus(con, 
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString(), "true");
            for (PenjualanDetail d : allDetail) {
                for (PenjualanHead h : listPenjualanHead) {
                    if (d.getNoPenjualan().equals(h.getNoPenjualan())) {
                        d.setPenjualan(h);
                    }
                }
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                d.setBarang(b);
            }
            allPenjualan.addAll(allDetail);
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

    private void searchPenjualanDetail() {
        try {
            filterData.clear();
            for (PenjualanDetail temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenjualan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getPenjualan().getTglPenjualan())))
                            || checkColumn(temp.getPenjualan().getKodeSales())
                            || checkColumn(temp.getPenjualan().getKodePelanggan())
                            || checkColumn(temp.getPenjualan().getNama())
                            || checkColumn(temp.getPenjualan().getAlamat())
                            || checkColumn(temp.getPenjualan().getNoTelp())
                            || checkColumn(gr.format(temp.getPenjualan().getTotalBerat()))
                            || checkColumn(gr.format(temp.getPenjualan().getGrandtotal()))
                            || checkColumn(temp.getPenjualan().getCatatan())
                            || checkColumn(temp.getKodeBarcode())
                            || checkColumn(temp.getBarang().getNamaBarang())
                            || checkColumn(temp.getBarang().getKeterangan())
                            || checkColumn(temp.getBarang().getKodeKategori())
                            || checkColumn(temp.getBarang().getKodeJenis())
                            || checkColumn(temp.getBarang().getKodeGudang())
                            || checkColumn(temp.getBarang().getKodeIntern())
                            || checkColumn(temp.getBarang().getKadar())
                            || checkColumn(gr.format(temp.getBarang().getBerat()))
                            || checkColumn(gr.format(temp.getBarang().getBeratAsli()))
                            || checkColumn(gr.format(temp.getBarang().getNilaiPokok()))
                            || checkColumn(gr.format(temp.getHargaJual()))
                            || checkColumn(gr.format(temp.getHargaKomp()))
                            || checkColumn(tglLengkap.format(tglSql.parse(
                                    temp.getBarang().getBarcodeDate())))) {
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
        if (penjualanTable.getRoot() != null) {
            penjualanTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PenjualanDetail temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")) {
                if (!groupBy.contains(temp.getNoPenjualan())) {
                    groupBy.add(temp.getNoPenjualan());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getPenjualan().getTglPenjualan().substring(0, 10))) {
                    groupBy.add(temp.getPenjualan().getTglPenjualan().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                if (!groupBy.contains(temp.getPenjualan().getKodeSales())) {
                    groupBy.add(temp.getPenjualan().getKodeSales());
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
        double totalNilai = 0;
        double totalJual = 0;
        for (String temp : groupBy) {
            PenjualanDetail head = new PenjualanDetail();
            head.setNoPenjualan(temp);
            PenjualanHead p = new PenjualanHead();
            p.setKodeSales("");
            head.setPenjualan(p);
            TreeItem<PenjualanDetail> parent = new TreeItem<>(head);
            double berat = 0;
            double beratAsli = 0;
            double nilaiPokok = 0;
            double hargaKomp = 0;
            double hargaJual = 0;
            for (PenjualanDetail detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")) {
                    if (temp.equals(detail.getNoPenjualan())) {
                        TreeItem<PenjualanDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBarang().getBeratAsli();
                        nilaiPokok = nilaiPokok + detail.getNilaiPokok();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaJual = hargaJual + detail.getHargaJual();
                        totalBerat = totalBerat + detail.getBerat();
                        totalNilai = totalNilai + detail.getNilaiPokok();
                        totalJual = totalJual + detail.getHargaJual();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getPenjualan().getTglPenjualan().substring(0, 10))) {
                        TreeItem<PenjualanDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBarang().getBeratAsli();
                        nilaiPokok = nilaiPokok + detail.getNilaiPokok();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaJual = hargaJual + detail.getHargaJual();
                        totalBerat = totalBerat + detail.getBerat();
                        totalNilai = totalNilai + detail.getNilaiPokok();
                        totalJual = totalJual + detail.getHargaJual();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                    if (temp.equals(detail.getPenjualan().getKodeSales())) {
                        TreeItem<PenjualanDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBarang().getBeratAsli();
                        nilaiPokok = nilaiPokok + detail.getNilaiPokok();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaJual = hargaJual + detail.getHargaJual();
                        totalBerat = totalBerat + detail.getBerat();
                        totalNilai = totalNilai + detail.getNilaiPokok();
                        totalJual = totalJual + detail.getHargaJual();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                    if (temp.equals(detail.getKodeKategori())) {
                        TreeItem<PenjualanDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBarang().getBeratAsli();
                        nilaiPokok = nilaiPokok + detail.getNilaiPokok();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaJual = hargaJual + detail.getHargaJual();
                        totalBerat = totalBerat + detail.getBerat();
                        totalNilai = totalNilai + detail.getNilaiPokok();
                        totalJual = totalJual + detail.getHargaJual();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                    if (temp.equals(detail.getKodeJenis())) {
                        TreeItem<PenjualanDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBarang().getBeratAsli();
                        nilaiPokok = nilaiPokok + detail.getNilaiPokok();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaJual = hargaJual + detail.getHargaJual();
                        totalBerat = totalBerat + detail.getBerat();
                        totalNilai = totalNilai + detail.getNilaiPokok();
                        totalJual = totalJual + detail.getHargaJual();
                    }
                }
            }
            Barang b = new Barang();
            b.setBeratAsli(beratAsli);
            b.setBerat(berat);
            b.setNilaiPokok(nilaiPokok);
            parent.getValue().setBarang(b);
            parent.getValue().setHargaKomp(hargaKomp);
            parent.getValue().setHargaJual(hargaJual);
            root.getChildren().add(parent);
        }
        penjualanTable.setRoot(root);
        totalBeratField.setText(gr.format(totalBerat));
        totalNilaiPokokField.setText(rp.format(totalNilai));
        totalPenjualanField.setText(rp.format(totalJual));
    }

}
