/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.Function;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import java.text.ParseException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DetailBarangController {

    @FXML
    private TextField kodeBarcodeField;
    @FXML
    private TextField kodeKategoriField;
    @FXML
    private TextField kodeJenisField;
    @FXML
    private TextField kodeGudangField;
    @FXML
    private TextField beratField;
    @FXML
    private TextField beratAsliField;
    @FXML
    private TextField tglBarcodeField;
    @FXML
    private TextField userBarcodeField;
    @FXML
    private TextField tglJualField;
    @FXML
    private TextField userJualField;
    @FXML
    private TextField tglHancurField;
    @FXML
    private TextField userHancurField;
    public TextField namaBarangField;
    public TextArea keteranganField;
    public TextField kodeInternField;
    public TextField kadarField;
    public TextField nilaiPokokField;
    public TextField hargaJualField;
    public AnchorPane anchorPane;
    public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        Function.setRpField(nilaiPokokField);
        Function.setRpField(hargaJualField);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setBarang(Barang b) {
        try {
            kodeBarcodeField.setText(b.getKodeBarcode());
            namaBarangField.setText(b.getNamaBarang());
            keteranganField.setText(b.getKeterangan());
            kodeKategoriField.setText(b.getKodeKategori());
            kodeJenisField.setText(b.getKodeJenis());
            kodeGudangField.setText(b.getKodeGudang());
            kodeInternField.setText(b.getKodeIntern());
            kadarField.setText(b.getKadar());
            beratField.setText(gr.format(b.getBerat()));
            beratAsliField.setText(gr.format(b.getBeratAsli()));
            tglBarcodeField.setText(tglLengkap.format(tglSql.parse(b.getBarcodeDate())));
            userBarcodeField.setText(b.getBarcodeBy());
            if (!b.getSoldDate().startsWith("2000-01-01")) {
                tglJualField.setText(tglLengkap.format(tglSql.parse(b.getSoldDate())));
            }
            userJualField.setText(b.getSoldBy());
            if (!b.getDeletedDate().startsWith("2000-01-01")) {
                tglHancurField.setText(tglLengkap.format(tglSql.parse(b.getDeletedDate())));
            }
            userHancurField.setText(b.getDeletedBy());
            nilaiPokokField.setText(rp.format(b.getNilaiPokok()));
            hargaJualField.setText(rp.format(b.getHargaJual()));
        } catch (ParseException ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    public void setViewOnly() {
        namaBarangField.setDisable(true);
        keteranganField.setDisable(true);
        kodeInternField.setDisable(true);
        kadarField.setDisable(true);
        beratField.setDisable(true);
        nilaiPokokField.setDisable(true);
        hargaJualField.setDisable(true);
        saveButton.setVisible(false);
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
