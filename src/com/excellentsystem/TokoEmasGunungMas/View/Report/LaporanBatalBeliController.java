/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PembelianHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianHead;
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
public class LaporanBatalBeliController {

    @FXML
    private TreeTableView<PembelianDetail> pembelianTable;
    @FXML
    private TreeTableColumn<PembelianDetail, String> noPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> tglPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> tglBatalColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> userBatalColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> kodeSalesColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> kodePelangganColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> namaColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> alamatColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> noTelpColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, Number> totalBeratKotorColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, Number> totalBeratBersihColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, Number> totalPembelianColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> catatanColumn;

    @FXML
    private TreeTableColumn<PembelianDetail, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, Number> beratKotorColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, Number> beratBersihColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, Number> hargaKompColumn;
    @FXML
    private TreeTableColumn<PembelianDetail, Number> hargaBeliColumn;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private Label totalBeratKotorField;
    @FXML
    private Label totalBeratBersihField;
    @FXML
    private Label totalPembelianField;
    @FXML
    private DatePicker mulaiTglPicker;
    @FXML
    private DatePicker akhirTglPicker;
    final TreeItem<PembelianDetail> root = new TreeItem<>();
    private ObservableList<PembelianDetail> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPembelianColumn.setCellValueFactory(param -> param.getValue().getValue().noPembelianProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getPembelian().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBatalColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getPembelian().getTglBatal())));
            } catch (Exception ex) {
                return null;
            }
        });
        userBatalColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().userBatalProperty());
        kodeSalesColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().kodeSalesProperty());
        kodePelangganColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().kodePelangganProperty());
        namaColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().namaProperty());
        alamatColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().alamatProperty());
        noTelpColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().noTelpProperty());
        totalBeratKotorColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().totalberatKotorProperty());
        totalBeratKotorColumn.setCellFactory(col -> new TreeTableCell<PembelianDetail, Number>() {
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
        totalBeratBersihColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().totalBeratBersihProperty());
        totalBeratBersihColumn.setCellFactory(col -> new TreeTableCell<PembelianDetail, Number>() {
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
        totalPembelianColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> new TreeTableCell<PembelianDetail, Number>() {
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
        catatanColumn.setCellValueFactory(param -> param.getValue().getValue().getPembelian().catatanProperty());

        namaBarangColumn.setCellValueFactory(param -> param.getValue().getValue().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(param -> param.getValue().getValue().kodeJenisProperty());
        beratKotorColumn.setCellValueFactory(param -> param.getValue().getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> new TreeTableCell<PembelianDetail, Number>() {
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
        beratBersihColumn.setCellValueFactory(param -> param.getValue().getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> new TreeTableCell<PembelianDetail, Number>() {
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
        hargaKompColumn.setCellValueFactory(param -> param.getValue().getValue().hargaKompProperty());
        hargaKompColumn.setCellFactory(col -> new TreeTableCell<PembelianDetail, Number>() {
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
        hargaBeliColumn.setCellValueFactory(param -> param.getValue().getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> new TreeTableCell<PembelianDetail, Number>() {
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
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianDetail> change) -> {
            searchPembelianDetail();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPembelianDetail();
                });
        filterData.addAll(allPembelian);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("No Pembelian");
        groupBy.add("Tanggal");
        groupBy.add("Sales");
        groupBy.add("Kategori Barang");
        groupBy.add("Jenis Barang");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Pembelian");
        getPembelian();
    }

    @FXML
    private void getPembelian() {
        try (Connection con = Koneksi.getConnection()) {
            allPembelian.clear();
            List<PembelianDetail> listPembelianDetail = PembelianDetailDAO.getAllByTglBatalAndStatus(con,
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString(), "false");
            List<PembelianHead> listPembelianHead = PembelianHeadDAO.getAllByTglBatalAndStatus(con,
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString(), "false");
            for (PembelianDetail d : listPembelianDetail) {
                for (PembelianHead h : listPembelianHead) {
                    if (d.getNoPembelian().equals(h.getNoPembelian())) {
                        if (d.getNoPembelian().equals(h.getNoPembelian())) {
                            d.setPembelian(h);
                        }
                    }
                }
            }
            allPembelian.addAll(listPembelianDetail);
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

    private void searchPembelianDetail() {
        try {
            filterData.clear();
            for (PembelianDetail temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPembelian())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getPembelian().getTglPembelian())))
                            || checkColumn(temp.getPembelian().getKodeSales())
                            || checkColumn(temp.getPembelian().getKodePelanggan())
                            || checkColumn(temp.getPembelian().getNama())
                            || checkColumn(temp.getPembelian().getAlamat())
                            || checkColumn(temp.getPembelian().getNoTelp())
                            || checkColumn(gr.format(temp.getPembelian().getTotalberatKotor()))
                            || checkColumn(gr.format(temp.getPembelian().getTotalBeratBersih()))
                            || checkColumn(gr.format(temp.getPembelian().getTotalPembelian()))
                            || checkColumn(temp.getPembelian().getCatatan())
                            || checkColumn(temp.getNamaBarang())
                            || checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getKodeJenis())
                            || checkColumn(gr.format(temp.getBeratKotor()))
                            || checkColumn(gr.format(temp.getBeratBersih()))
                            || checkColumn(gr.format(temp.getHargaBeli()))
                            || checkColumn(gr.format(temp.getHargaKomp()))) {
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
        if (pembelianTable.getRoot() != null) {
            pembelianTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PembelianDetail temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")) {
                if (!groupBy.contains(temp.getNoPembelian())) {
                    groupBy.add(temp.getNoPembelian());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getPembelian().getTglPembelian().substring(0, 10))) {
                    groupBy.add(temp.getPembelian().getTglPembelian().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                if (!groupBy.contains(temp.getPembelian().getKodeSales())) {
                    groupBy.add(temp.getPembelian().getKodeSales());
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
        double totalBeratBersih = 0;
        double totalBeratKotor = 0;
        double totalBeli = 0;
        for (String temp : groupBy) {
            PembelianDetail head = new PembelianDetail();
            head.setNoPembelian(temp);
            PembelianHead p = new PembelianHead();
            p.setKodeSales("");
            head.setPembelian(p);
            TreeItem<PembelianDetail> parent = new TreeItem<>(head);
            double beratKotor = 0;
            double beratBersih = 0;
            double hargaKomp = 0;
            double hargaBeli = 0;
            for (PembelianDetail detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")) {
                    if (temp.equals(detail.getNoPembelian())) {
                        TreeItem<PembelianDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        beratKotor = beratKotor + detail.getBeratKotor();
                        beratBersih = beratBersih + detail.getBeratBersih();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaBeli = hargaBeli + detail.getHargaBeli();
                        totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                        totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                        totalBeli = totalBeli + detail.getHargaBeli();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getPembelian().getTglPembelian().substring(0, 10))) {
                        TreeItem<PembelianDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        beratKotor = beratKotor + detail.getBeratKotor();
                        beratBersih = beratBersih + detail.getBeratBersih();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaBeli = hargaBeli + detail.getHargaBeli();
                        totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                        totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                        totalBeli = totalBeli + detail.getHargaBeli();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                    if (temp.equals(detail.getPembelian().getKodeSales())) {
                        TreeItem<PembelianDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        beratKotor = beratKotor + detail.getBeratKotor();
                        beratBersih = beratBersih + detail.getBeratBersih();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaBeli = hargaBeli + detail.getHargaBeli();
                        totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                        totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                        totalBeli = totalBeli + detail.getHargaBeli();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                    if (temp.equals(detail.getKodeKategori())) {
                        TreeItem<PembelianDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        beratKotor = beratKotor + detail.getBeratKotor();
                        beratBersih = beratBersih + detail.getBeratBersih();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaBeli = hargaBeli + detail.getHargaBeli();
                        totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                        totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                        totalBeli = totalBeli + detail.getHargaBeli();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                    if (temp.equals(detail.getKodeJenis())) {
                        TreeItem<PembelianDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        beratKotor = beratKotor + detail.getBeratKotor();
                        beratBersih = beratBersih + detail.getBeratBersih();
                        hargaKomp = hargaKomp + detail.getHargaKomp();
                        hargaBeli = hargaBeli + detail.getHargaBeli();
                        totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                        totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                        totalBeli = totalBeli + detail.getHargaBeli();
                    }
                }
            }
            parent.getValue().setBeratKotor(beratKotor);
            parent.getValue().setBeratBersih(beratBersih);
            parent.getValue().setHargaBeli(hargaBeli);
            parent.getValue().setHargaKomp(hargaKomp);
            root.getChildren().add(parent);
        }
        pembelianTable.setRoot(root);
        totalBeratBersihField.setText(gr.format(totalBeratBersih));
        totalBeratKotorField.setText(gr.format(totalBeratKotor));
        totalPembelianField.setText(rp.format(totalBeli));
    }
}
