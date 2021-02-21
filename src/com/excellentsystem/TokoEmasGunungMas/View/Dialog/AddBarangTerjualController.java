/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
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
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class AddBarangTerjualController {

    public TableView<PenjualanDetail> penjualanTable;
    @FXML
    private TableColumn<PenjualanDetail, String> noPenjualanColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> tglPenjualanColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> kodeSalesColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> kodePelangganColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> namaColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> alamatColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> noTelpColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> totalBeratColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> grandtotalColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> catatanColumn;

    @FXML
    private TableColumn<PenjualanDetail, String> kodeBarcodeColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> kodeJenisColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> kodeGudangColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> kodeInternColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> kadarColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> beratColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> beratAsliColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> nilaiPokokColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> hargaKompColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> hargaJualColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> userBarcodeColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> tglBarcodeColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    private ObservableList<PenjualanDetail> allPenjualanDetail = FXCollections.observableArrayList();
    private ObservableList<PenjualanDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;


    public void initialize() {
        noPenjualanColumn.setCellValueFactory(param -> param.getValue().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(
                        cellData.getValue().getPenjualan().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        kodeSalesColumn.setCellValueFactory(param -> param.getValue().getPenjualan().kodeSalesProperty());
        kodePelangganColumn.setCellValueFactory(param -> param.getValue().getPenjualan().kodePelangganProperty());
        namaColumn.setCellValueFactory(param -> param.getValue().getPenjualan().namaProperty());
        alamatColumn.setCellValueFactory(param -> param.getValue().getPenjualan().alamatProperty());
        noTelpColumn.setCellValueFactory(param -> param.getValue().getPenjualan().noTelpProperty());
        totalBeratColumn.setCellValueFactory(param -> param.getValue().getPenjualan().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        grandtotalColumn.setCellValueFactory(param -> param.getValue().getPenjualan().grandtotalProperty());
        grandtotalColumn.setCellFactory(col -> getTableCell(rp));
        catatanColumn.setCellValueFactory(param -> param.getValue().getPenjualan().catatanProperty());

        kodeBarcodeColumn.setCellValueFactory(param -> param.getValue().getBarang().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(param -> param.getValue().getBarang().namaBarangProperty());
        keteranganColumn.setCellValueFactory(param -> param.getValue().getBarang().keteranganProperty());
        kodeKategoriColumn.setCellValueFactory(param -> param.getValue().getBarang().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(param -> param.getValue().getBarang().kodeJenisProperty());
        kodeGudangColumn.setCellValueFactory(param -> param.getValue().getBarang().kodeGudangProperty());
        kodeInternColumn.setCellValueFactory(param -> param.getValue().getBarang().kodeInternProperty());
        kadarColumn.setCellValueFactory(param -> param.getValue().getBarang().kadarProperty());
        beratColumn.setCellValueFactory(param -> param.getValue().getBarang().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(param -> param.getValue().getBarang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        nilaiPokokColumn.setCellValueFactory(param -> param.getValue().getBarang().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTableCell(rp));
        hargaKompColumn.setCellValueFactory(param -> param.getValue().hargaKompProperty());
        hargaKompColumn.setCellFactory(col -> getTableCell(rp));
        hargaJualColumn.setCellValueFactory(param -> param.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        userBarcodeColumn.setCellValueFactory(param -> param.getValue().getBarang().barcodeByProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(
                        cellData.getValue().getBarang().getBarcodeDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE).minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> new DateCell() {
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
        });
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> new DateCell() {
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
                if (item.isBefore(tglMulaiPicker.getValue())) {
                    this.setDisable(true);
                }
            }
        });
        allPenjualanDetail.addListener((ListChangeListener.Change<? extends PenjualanDetail> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchBarang();
                });
        filterData.addAll(allPenjualanDetail);
        penjualanTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        getPenjualanDetail();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() - 80);
        stage.setWidth(mainApp.screenSize.getWidth() - 80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void getPenjualanDetail() {
        try(Connection con = Koneksi.getConnection()){
            allPenjualanDetail.clear();
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByTglPenjualanAndStatus(con, 
                    tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
            List<PenjualanHead> listPenjualanHead = PenjualanHeadDAO.getAllByTglPenjualanAndStatus(con, 
                    tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
            List<Barang> listBarang = BarangDAO.getAll(con, "Terjual", "Semua", "Semua", "Semua", "Semua");
            for (PenjualanDetail d : listPenjualanDetail) {
                for (PenjualanHead h : listPenjualanHead) {
                    if (d.getNoPenjualan().equals(h.getNoPenjualan())) {
                        d.setPenjualan(h);
                    }
                }
                for (Barang b : listBarang) {
                    if (d.getKodeBarcode().equals(b.getKodeBarcode())) {
                        d.setBarang(b);
                    }
                }
            }
            allPenjualanDetail.addAll(listPenjualanDetail);
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

    private void searchBarang() {
        try {
            filterData.clear();
            for (PenjualanDetail temp : allPenjualanDetail) {
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
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
