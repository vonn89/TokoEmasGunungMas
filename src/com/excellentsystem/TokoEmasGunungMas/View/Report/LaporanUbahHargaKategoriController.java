/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.LogHargaDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglNormal;
import com.excellentsystem.TokoEmasGunungMas.Model.LogHarga;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class LaporanUbahHargaKategoriController {

    @FXML
    private TableView<LogHarga> logHargaHeadTable;
    @FXML
    private TableColumn<LogHarga, String> tanggalColumn;
    @FXML
    private TableColumn<LogHarga, String> kodeKategoriColumn;
    @FXML
    private TableColumn<LogHarga, Number> hargaBeliColumn;
    @FXML
    private TableColumn<LogHarga, Number> hargaJualColumn;
    @FXML
    private TableColumn<LogHarga, String> kodeUserColumn;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private TextField searchField;
    private Main mainApp;
    private ObservableList<LogHarga> allLogHarga = FXCollections.observableArrayList();
    private ObservableList<LogHarga> filterData = FXCollections.observableArrayList();

    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglNormal.format(
                        tglBarang.parse(cellData.getValue().getTanggal())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tglLengkap));
        
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> getTableCell(rp));
        
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().userProperty());

        tglStartPicker.setConverter(Function.getTglConverter());
        tglStartPicker.setValue(LocalDate.now());
        tglStartPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglStartPicker));
        

        allLogHarga.addListener((ListChangeListener.Change<? extends LogHarga> change) -> {
            searchLogHarga();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchLogHarga();
                });
        filterData.addAll(allLogHarga);
    }

    public void setMainApp(Main mainApp) {
        try {
            this.mainApp = mainApp;
            logHargaHeadTable.setItems(filterData);
            getLogHarga();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void getLogHarga() {
        try (Connection con = Koneksi.getConnection()) {
            allLogHarga.clear();
            List<LogHarga> x = LogHargaDAO.getAllByDate(con, 
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
            allLogHarga.addAll(x);
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

    private void searchLogHarga() {
        try {
            filterData.clear();
            for (LogHarga temp : allLogHarga) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(tglNormal.format(tglBarang.parse(temp.getTanggal())))
                            || checkColumn(temp.getKodeKategori())
                            || checkColumn(gr.format(temp.getHargaBeli()))
                            || checkColumn(gr.format(temp.getHargaJual()))
                            || checkColumn(temp.getUser())) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

}
