/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Gudang {
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final DoubleProperty beratBaki = new SimpleDoubleProperty();

    public double getBeratBaki() {
        return beratBaki.get();
    }

    public void setBeratBaki(double value) {
        beratBaki.set(value);
    }

    public DoubleProperty beratBakiProperty() {
        return beratBaki;
    }

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
    }
    
}
