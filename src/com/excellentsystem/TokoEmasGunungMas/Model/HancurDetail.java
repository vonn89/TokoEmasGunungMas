/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Yunaz
 */
public class HancurDetail {
    
    private final StringProperty noHancur = new SimpleStringProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private Barang barang;
    private HancurHead hancurHead;

    public HancurHead getHancurHead() {
        return hancurHead;
    }

    public void setHancurHead(HancurHead hancurHead) {
        this.hancurHead = hancurHead;
    }
    
    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    public String getKodeBarcode() {
        return kodeBarcode.get();
    }

    public void setKodeBarcode(String value) {
        kodeBarcode.set(value);
    }

    public StringProperty kodeBarcodeProperty() {
        return kodeBarcode;
    }


    public String getNoHancur() {
        return noHancur.get();
    }

    public void setNoHancur(String value) {
        noHancur.set(value);
    }

    public StringProperty noHancurProperty() {
        return noHancur;
    }
}
