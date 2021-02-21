/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class AmbilBarangHead {
    private final StringProperty noAmbil = new SimpleStringProperty();
    private final StringProperty tglAmbil = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private List<AmbilBarangDetail> allDetail;

    public List<AmbilBarangDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<AmbilBarangDetail> allDetail) {
        this.allDetail = allDetail;
    }

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public double getTotalBerat() {
        return totalBerat.get();
    }

    public void setTotalBerat(double value) {
        totalBerat.set(value);
    }

    public DoubleProperty totalBeratProperty() {
        return totalBerat;
    }

    public int getTotalQty() {
        return totalQty.get();
    }

    public void setTotalQty(int value) {
        totalQty.set(value);
    }

    public IntegerProperty totalQtyProperty() {
        return totalQty;
    }
    
    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public String getTglAmbil() {
        return tglAmbil.get();
    }

    public void setTglAmbil(String value) {
        tglAmbil.set(value);
    }

    public StringProperty tglAmbilProperty() {
        return tglAmbil;
    }

    public String getNoAmbil() {
        return noAmbil.get();
    }

    public void setNoAmbil(String value) {
        noAmbil.set(value);
    }

    public StringProperty noAmbilProperty() {
        return noAmbil;
    }
    
}
