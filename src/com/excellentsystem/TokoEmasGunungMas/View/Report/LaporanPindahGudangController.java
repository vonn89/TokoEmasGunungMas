/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PindahDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PindahHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahHead;
import java.sql.Connection;
import java.text.ParseException;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class LaporanPindahGudangController {

    @FXML
    private TableView<PindahHead> pindahGudangHeadTable;
    @FXML
    private TableColumn<PindahHead, String> noPindahColumn;
    @FXML
    private TableColumn<PindahHead, String> tglPindahColumn;
    @FXML
    private TableColumn<PindahHead, Number> totalQtyColumn;
    @FXML
    private TableColumn<PindahHead, Number> totalBeratColumn;
    @FXML
    private TableColumn<PindahHead, Number> totalBeratAsliColumn;
    @FXML
    private TableColumn<PindahHead, String> kodeUserColumn;
    @FXML
    private TableColumn<PindahHead, String> gudangTujuanColumn;

    @FXML
    private TableView<PindahDetail> pindahGudangDetailTable;
    @FXML
    private TableColumn<PindahDetail, String> kodeBarcodeColumn;
    @FXML
    private TableColumn<PindahDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PindahDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PindahDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<PindahDetail, String> kodeJenisColumn;
    @FXML
    private TableColumn<PindahDetail, String> kodeInternColumn;
    @FXML
    private TableColumn<PindahDetail, String> kadarColumn;
    @FXML
    private TableColumn<PindahDetail, Number> beratColumn;
    @FXML
    private TableColumn<PindahDetail, Number> beratAsliColumn;
    @FXML
    private TableColumn<PindahDetail, Number> nilaiPokokColumn;
    @FXML
    private TableColumn<PindahDetail, Number> hargaJualColumn;
    @FXML
    private TableColumn<PindahDetail, String> userBarcodeColumn;
    @FXML
    private TableColumn<PindahDetail, String> tglBarcodeColumn;
    @FXML
    private TableColumn<PindahDetail, String> statusBarangColumn;
    @FXML
    private TableColumn<PindahDetail, String> gudangAsalColumn;

    @FXML
    private Label totalQty;
    @FXML
    private Label totalBerat;
    @FXML
    private Label totalBeratAsli;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private TextField searchField;
    private Main mainApp;
    private ObservableList<PindahHead> allPindah = FXCollections.observableArrayList();
    private ObservableList<PindahHead> filterData = FXCollections.observableArrayList();
    private ObservableList<PindahDetail> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        noPindahColumn.setCellValueFactory(cellData -> cellData.getValue().noPindahProperty());
        tglPindahColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getTglPindah())));
            } catch (ParseException ex) {
                return null;
            }
        });
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> new TableCell<PindahHead, Number>() {
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
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> new TableCell<PindahHead, Number>() {
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
        totalBeratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratAsliProperty());
        totalBeratAsliColumn.setCellFactory(col -> new TableCell<PindahHead, Number>() {
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
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());

        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getBarang().getBarcodeDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().keteranganProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeJenisProperty());
        gudangAsalColumn.setCellValueFactory(cellData -> cellData.getValue().gudangAsalProperty());
        gudangTujuanColumn.setCellValueFactory(cellData -> cellData.getValue().gudangTujuanProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().beratProperty());
        beratColumn.setCellFactory(col -> new TableCell<PindahDetail, Number>() {
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
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> new TableCell<PindahDetail, Number>() {
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
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> new TableCell<PindahDetail, Number>() {
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
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> new TableCell<PindahDetail, Number>() {
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
        statusBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().statusBarangProperty());
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().barcodeByProperty());

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
        pindahGudangHeadTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectPindah(newValue));

        allPindah.addListener((ListChangeListener.Change<? extends PindahHead> change) -> {
            searchPindah();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPindah();
                });
        filterData.addAll(allPindah);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        pindahGudangHeadTable.setItems(filterData);
        pindahGudangDetailTable.setItems(allDetail);
        getPindahGudang();
    }

    @FXML
    private void getPindahGudang() {
        try (Connection con = Koneksi.getConnection()) {
            allPindah.clear();
            List<PindahHead> listPindahHead = PindahHeadDAO.getAllByTglPindah(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
            List<PindahDetail> listPindahDetail = PindahDetailDAO.getAllByTglPindah(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
            for (PindahHead h : listPindahHead) {
                List<PindahDetail> listDetail = new ArrayList<>();
                for (PindahDetail d : listPindahDetail) {
                    if (h.getNoPindah().equals(d.getNoPindah())) {
                        Barang b = BarangDAO.get(con, d.getKodeBarcode());
                        d.setBarang(b);
                        listDetail.add(d);
                    }
                }
                h.setAllDetail(listDetail);
            }
            allPindah.addAll(listPindahHead);
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

    private void searchPindah() {
        try {
            filterData.clear();
            for (PindahHead temp : allPindah) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPindah())
                            || checkColumn(temp.getGudangTujuan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPindah())))
                            || checkColumn(gr.format(temp.getTotalQty()))
                            || checkColumn(gr.format(temp.getTotalBerat()))
                            || checkColumn(gr.format(temp.getTotalBeratAsli()))
                            || checkColumn(temp.getKodeUser())) {
                        filterData.add(temp);
                    } else {
                        Boolean status = false;
                        for (PindahDetail detail : temp.getAllDetail()) {
                            if (checkColumn(detail.getKodeBarcode())
                                    || checkColumn(tglLengkap.format(tglSql.parse(detail.getBarang().getBarcodeDate())))
                                    || checkColumn(detail.getBarang().getNamaBarang())
                                    || checkColumn(detail.getBarang().getKeterangan())
                                    || checkColumn(detail.getBarang().getKodeKategori())
                                    || checkColumn(detail.getBarang().getKodeJenis())
                                    || checkColumn(detail.getBarang().getKodeIntern())
                                    || checkColumn(detail.getBarang().getKadar())
                                    || checkColumn(detail.getBarang().getStatusBarang())
                                    || checkColumn(gr.format(detail.getBarang().getBerat()))
                                    || checkColumn(gr.format(detail.getBarang().getBeratAsli()))
                                    || checkColumn(rp.format(detail.getBarang().getNilaiPokok()))
                                    || checkColumn(rp.format(detail.getBarang().getHargaJual()))
                                    || checkColumn(detail.getGudangAsal())
                                    || checkColumn(detail.getBarang().getBarcodeBy())) {
                                status = true;
                            }
                        }
                        if (status) {
                            filterData.add(temp);
                        }
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void selectPindah(PindahHead value) {
        if (value != null) {
            try {
                allDetail.clear();
                allDetail.addAll(value.getAllDetail());
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        } else {
            allDetail.clear();
        }
    }

    private void hitungTotal() {
        int qty = 0;
        double berat = 0;
        double beratAsli = 0;
        for (PindahHead h : filterData) {
            qty = qty + h.getTotalQty();
            berat = berat + h.getTotalBerat();
            beratAsli = beratAsli + h.getTotalBeratAsli();
        }
        totalQty.setText(gr.format(qty));
        totalBerat.setText(gr.format(berat));
        totalBeratAsli.setText(gr.format(beratAsli));
    }
}
