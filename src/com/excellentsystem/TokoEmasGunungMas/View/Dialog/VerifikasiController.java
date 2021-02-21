/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.excellentsystem.TokoEmasGunungMas.Main;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class VerifikasiController  {

    public TextField username;
    public PasswordField password;
    public void initialize() {
        username.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                password.selectAll();
                password.requestFocus();
            }
        });
    }   
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
}
