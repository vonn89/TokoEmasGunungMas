/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.allUser;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import com.excellentsystem.TokoEmasGunungMas.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LoginController {

    @FXML private Label versionLabel;
    @FXML private Label namaPerusahaanLabel;
    @FXML private Label warningLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private Main mainApp;
    private int attempt = 0;
    public void initialize() {
        usernameField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                passwordField.requestFocus();
        });
        passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                handleLoginButton();
        });
    }    
    public void setMainApp(Main mainApp){
        try {
            this.mainApp = mainApp;
            namaPerusahaanLabel.setText(sistem.getNamaToko().toUpperCase());
            warningLabel.setText("");
        } catch (Exception ex) {
            warningLabel.setText(ex.toString());
        }
    }
    @FXML 
    private void handleLoginButton(){
        if("".equals(usernameField.getText())){
            warningLabel.setText("Username belum dipilih");
        }else if(passwordField.getText().equals("")){
            warningLabel.setText("Password masih kosong");
        }else if(attempt>=3){
            System.exit(0);
        }else{
            User user = null;
            for(User u : allUser){
                if(u.getUsername().equals(usernameField.getText()))
                    user = u;
            }
            if(user!=null){
                if(!user.getPassword().equals(passwordField.getText())){
                    warningLabel.setText("Password masih salah");
                    attempt = attempt +1;
                }else{
                    Main.user = user;
                    mainApp.showMainScene();
                }
            }else{
                warningLabel.setText("Username masih salah");
            }
        }
    }
    @FXML
    private void close(){
        System.exit(3);
    }
   
    
}
