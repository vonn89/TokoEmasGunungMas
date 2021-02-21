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
public class Servis {
    private final StringProperty noServis = new SimpleStringProperty();
    private final StringProperty tglServis = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty kodePelanggan = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final StringProperty kategoriServis = new SimpleStringProperty();
    private final DoubleProperty biayaServis = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglAmbil = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty salesAmbil = new SimpleStringProperty();
    private final StringProperty salesBatal = new SimpleStringProperty();

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public String getSalesBatal() {
        return salesBatal.get();
    }

    public void setSalesBatal(String value) {
        salesBatal.set(value);
    }

    public StringProperty salesBatalProperty() {
        return salesBatal;
    }

    public String getSalesAmbil() {
        return salesAmbil.get();
    }

    public void setSalesAmbil(String value) {
        salesAmbil.set(value);
    }

    public StringProperty salesAmbilProperty() {
        return salesAmbil;
    }

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
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

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public double getBiayaServis() {
        return biayaServis.get();
    }

    public void setBiayaServis(double value) {
        biayaServis.set(value);
    }

    public DoubleProperty biayaServisProperty() {
        return biayaServis;
    }

    public String getKategoriServis() {
        return kategoriServis.get();
    }

    public void setKategoriServis(String value) {
        kategoriServis.set(value);
    }

    public StringProperty kategoriServisProperty() {
        return kategoriServis;
    }

    public double getBerat() {
        return berat.get();
    }

    public void setBerat(double value) {
        berat.set(value);
    }

    public DoubleProperty beratProperty() {
        return berat;
    }

    public String getNamaBarang() {
        return namaBarang.get();
    }

    public void setNamaBarang(String value) {
        namaBarang.set(value);
    }

    public StringProperty namaBarangProperty() {
        return namaBarang;
    }

    public String getNoTelp() {
        return noTelp.get();
    }

    public void setNoTelp(String value) {
        noTelp.set(value);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getKodePelanggan() {
        return kodePelanggan.get();
    }

    public void setKodePelanggan(String value) {
        kodePelanggan.set(value);
    }

    public StringProperty kodePelangganProperty() {
        return kodePelanggan;
    }

    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
    }

    public String getTglServis() {
        return tglServis.get();
    }

    public void setTglServis(String value) {
        tglServis.set(value);
    }

    public StringProperty tglServisProperty() {
        return tglServis;
    }

    public String getNoServis() {
        return noServis.get();
    }

    public void setNoServis(String value) {
        noServis.set(value);
    }

    public StringProperty noServisProperty() {
        return noServis;
    }
    
}
