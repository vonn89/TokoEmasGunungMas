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
public class CetakBarcodeDetail {
    
    private final StringProperty noCetak = new SimpleStringProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private Barang barang;
    private CetakBarcodeHead cetakBarcodeHead;

    public CetakBarcodeHead getCetakBarcodeHead() {
        return cetakBarcodeHead;
    }

    public void setCetakBarcodeHead(CetakBarcodeHead cetakBarcodeHead) {
        this.cetakBarcodeHead = cetakBarcodeHead;
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
