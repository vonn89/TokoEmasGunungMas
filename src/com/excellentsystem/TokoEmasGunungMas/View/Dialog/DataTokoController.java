/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.SistemDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import com.excellentsystem.TokoEmasGunungMas.Model.Sistem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataTokoController {

    @FXML
    private TextField namaField;
    @FXML
    private TextField alamatField;
    @FXML
    private TextField noTelpField;
    @FXML
    private TextField beratLabelField;
    @FXML
    private TextField prefixBarcodeField;
    @FXML
    private TextField serverField;
    @FXML
    private TextField printerPenjualanField;
    @FXML
    private TextField printerGadaiField;
    @FXML
    private TextField printerBarcodeField;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        Function.setBeratField(beratLabelField);
        setDataPerusahaan();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    private static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    private void setDataPerusahaan() {
        try {
            namaField.setText(Main.sistem.getNamaToko());
            alamatField.setText(Main.sistem.getAlamatToko());
            noTelpField.setText(Main.sistem.getNoTelpToko());
            beratLabelField.setText(gr.format(Main.sistem.getBeratLabel()));
            prefixBarcodeField.setText(Main.sistem.getPrefixBarcode());
            BufferedReader in = new BufferedReader(new FileReader("koneksi.txt"));
            String ipServer = in.readLine();
            String printerPenjualan = in.readLine();
            String printerGadai = in.readLine();
            String printerBarcode = in.readLine();
            serverField.setText(ipServer);
            printerPenjualanField.setText(printerPenjualan);
            printerGadaiField.setText(printerGadai);
            printerBarcodeField.setText(printerBarcode);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void cancel() {
        mainApp.closeDialog(owner, stage);
    }

    @FXML
    private void saveDataPerusahaan() {
        try(Connection con = Koneksi.getConnection()){
            Sistem sistem = Main.sistem;
            sistem.setNamaToko(namaField.getText());
            sistem.setAlamatToko(alamatField.getText());
            sistem.setNoTelpToko(noTelpField.getText());
            sistem.setPrefixBarcode(prefixBarcodeField.getText());
            sistem.setBeratLabel(Double.parseDouble(beratLabelField.getText().replaceAll(",", "")));
            BufferedWriter out = new BufferedWriter(new FileWriter("koneksi.txt"));
            out.write(serverField.getText());
            out.newLine();
            out.write(printerPenjualanField.getText());
            out.newLine();
            out.write(printerGadaiField.getText());
            out.newLine();
            out.write(printerBarcodeField.getText());
            out.close();
            SistemDAO.update(con, sistem);
            MessageController controller = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Setting baru berhasil disimpan,\nto take effect please restart program");
            controller.OK.setOnAction((ActionEvent event) -> {
                try {
                    mainApp.MainStage.close();
                    mainApp.start(new Stage());
                } catch (Exception e) {
                    System.exit(0);
                }
            });
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
