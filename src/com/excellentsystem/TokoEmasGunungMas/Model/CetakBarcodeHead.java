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
 * @author Yunaz
 */
public class CetakBarcodeHead {
    
    private final StringProperty noCetak = new SimpleStringProperty();
    private final StringProperty tglCetak = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratAsli = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private List<CetakBarcodeDetail> allDetail;

    public List<CetakBarcodeDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<CetakBarcodeDetail> allDetail) {
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

    public double getTotalBeratAsli() {
        return totalBeratAsli.get();
    }

    public void setTotalBeratAsli(double value) {
        totalBeratAsli.set(value);
    }

    public DoubleProperty totalBeratAsliProperty() {
        return totalBeratAsli;
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

    public String getTglCetak() {
        return tglCetak.get();
    }

    public void setTglCetak(String value) {
        tglCetak.set(value);
    }

    public StringProperty tglCetakProperty() {
        return tglCetak;
    }

    public String getNoCetak() {
        return noCetak.get();
    }

    public void setNoCetak(String value) {
        noCetak.set(value);
    }

    public StringProperty noCetakProperty() {
        return noCetak;
    }
}
