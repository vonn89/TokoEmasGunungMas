/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.UserDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.user;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class UbahPasswordController {

    @FXML
    public Label username;
    @FXML
    public PasswordField passwordLama;
    @FXML
    public PasswordField passwordBaru;
    @FXML
    public PasswordField ulangiPasswordBaru;
    @FXML
    public Label warning;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        username.setText(user.getUsername());
        warning.setText("");
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void save() {
        if (passwordLama.getText().equals("")) {
            warning.setText("password lama masih kosong");
        } else if (passwordBaru.getText().equals("")) {
            warning.setText("password baru masih kosong");
        } else if (ulangiPasswordBaru.getText().equals("")) {
            warning.setText("ulangi password baru masih kosong");
        } else if (!passwordLama.getText().equals(user.getPassword())) {
            warning.setText("password lama salah");
        } else if (!passwordBaru.getText().equals(ulangiPasswordBaru.getText())) {
            warning.setText("password baru tidak sama");
        } else {
            try (Connection con = Koneksi.getConnection()) {
                user.setPassword(passwordBaru.getText());
                UserDAO.update(con, user);
                mainApp.showMessage(Modality.NONE, "Success", "Password baru berhasil di simpan");
                close();
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
