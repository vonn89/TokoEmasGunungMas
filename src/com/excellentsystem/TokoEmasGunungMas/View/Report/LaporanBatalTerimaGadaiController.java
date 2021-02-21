/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiHead;
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
public class LaporanBatalTerimaGadaiController {

    @FXML
    private TreeTableView<GadaiHead> gadaiTable;
    @FXML
    private TreeTableColumn<GadaiHead, String> noGadaiColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> tglGadaiColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> kodeSalesColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> tglBatalColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> userBatalColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> kodePelangganColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> namaColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> alamatColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> noTelpColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> keteranganColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> totalBeratColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> totalPinjamanColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> lamaPinjamColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> bungaPersenColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> bungaKompColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> bungaRpColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Label totalBeratField;
    @FXML
    private Label totalPinjamanField;
    @FXML
    private DatePicker mulaiTglPicker;
    @FXML
    private DatePicker akhirTglPicker;
    final TreeItem<GadaiHead> root = new TreeItem<>();
    private ObservableList<GadaiHead> allGadai = FXCollections.observableArrayList();
    private ObservableList<GadaiHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noGadaiColumn.setCellValueFactory(param -> param.getValue().getValue().noGadaiProperty());
        tglGadaiColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getTglGadai())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBatalColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getTglBatal())));
            } catch (Exception ex) {
                return null;
            }
        });
        userBatalColumn.setCellValueFactory(param -> param.getValue().getValue().salesBatalProperty());
        kodeSalesColumn.setCellValueFactory(param -> param.getValue().getValue().kodeSalesProperty());
        kodePelangganColumn.setCellValueFactory(param -> param.getValue().getValue().kodePelangganProperty());
        namaColumn.setCellValueFactory(param -> param.getValue().getValue().namaProperty());
        alamatColumn.setCellValueFactory(param -> param.getValue().getValue().alamatProperty());
        noTelpColumn.setCellValueFactory(param -> param.getValue().getValue().noTelpProperty());
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        totalBeratColumn.setCellValueFactory(param -> param.getValue().getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> new TreeTableCell<GadaiHead, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(gr.format(value.doubleValue()));
                }
            }
        });
        totalPinjamanColumn.setCellValueFactory(param -> param.getValue().getValue().totalPinjamanProperty());
        totalPinjamanColumn.setCellFactory(col -> new TreeTableCell<GadaiHead, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        lamaPinjamColumn.setCellValueFactory(param -> param.getValue().getValue().lamaPinjamProperty());
        lamaPinjamColumn.setCellFactory(col -> new TreeTableCell<GadaiHead, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(gr.format(value.doubleValue()));
                }
            }
        });
        bungaPersenColumn.setCellValueFactory(param -> param.getValue().getValue().bungaPersenProperty());
        bungaPersenColumn.setCellFactory(col -> new TreeTableCell<GadaiHead, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(gr.format(value.doubleValue()));
                }
            }
        });
        bungaKompColumn.setCellValueFactory(param -> param.getValue().getValue().bungaKompProperty());
        bungaKompColumn.setCellFactory(col -> new TreeTableCell<GadaiHead, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        bungaRpColumn.setCellValueFactory(param -> param.getValue().getValue().bungaRpProperty());
        bungaRpColumn.setCellFactory(col -> new TreeTableCell<GadaiHead, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText("");
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
        allGadai.addListener((ListChangeListener.Change<? extends GadaiHead> change) -> {
            searchGadai();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchGadai();
                });
        filterData.addAll(allGadai);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getGadai();
    }

    @FXML
    private void getGadai() {
        try (Connection con = Koneksi.getConnection()){
            allGadai.clear();
            List<GadaiHead> gadai = GadaiHeadDAO.getAllByTglGadaiAndStatus(con, 
                    mulaiTglPicker.getValue().toString(),
                    akhirTglPicker.getValue().toString(),
                    "Batal Gadai");
            allGadai.addAll(gadai);
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

    private void searchGadai() {
        try {
            filterData.clear();
            for (GadaiHead temp : allGadai) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoGadai())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglGadai())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglBatal())))
                            || checkColumn(temp.getSalesBatal())
                            || checkColumn(temp.getKodeSales())
                            || checkColumn(temp.getKodePelanggan())
                            || checkColumn(temp.getNama())
                            || checkColumn(temp.getAlamat())
                            || checkColumn(temp.getNoTelp())
                            || checkColumn(gr.format(temp.getTotalBerat()))
                            || checkColumn(gr.format(temp.getTotalPinjaman()))
                            || checkColumn(gr.format(temp.getLamaPinjam()))
                            || checkColumn(gr.format(temp.getBungaPersen()))
                            || checkColumn(gr.format(temp.getBungaKomp()))
                            || checkColumn(gr.format(temp.getBungaRp()))
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getStatus())) {
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
        if (gadaiTable.getRoot() != null) {
            gadaiTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (GadaiHead temp : filterData) {
            if (!groupBy.contains(temp.getTglGadai().substring(0, 10))) {
                groupBy.add(temp.getTglGadai().substring(0, 10));
            }
        }
        double totalBerat = 0;
        double totalPinjaman = 0;
        for (String temp : groupBy) {
            GadaiHead head = new GadaiHead();
            head.setNoGadai(temp);
            TreeItem<GadaiHead> parent = new TreeItem<>(head);
            double berat = 0;
            double pinjaman = 0;
            double bungaKomp = 0;
            for (GadaiHead detail : filterData) {
                if (temp.equals(detail.getTglGadai().substring(0, 10))) {
                    TreeItem<GadaiHead> child = new TreeItem<>(detail);
                    parent.getChildren().addAll(child);
                    berat = berat + detail.getTotalBerat();
                    pinjaman = pinjaman + detail.getTotalPinjaman();
                    bungaKomp = bungaKomp + detail.getBungaKomp();
                    totalBerat = totalBerat + detail.getTotalBerat();
                    totalPinjaman = totalPinjaman + detail.getTotalPinjaman();
                }
            }
            parent.getValue().setTotalBerat(berat);
            parent.getValue().setTotalPinjaman(totalPinjaman);
            parent.getValue().setBungaKomp(bungaKomp);
            root.getChildren().add(parent);
        }
        gadaiTable.setRoot(root);
        totalBeratField.setText(gr.format(totalBerat));
        totalPinjamanField.setText(rp.format(totalPinjaman));
    }

}
