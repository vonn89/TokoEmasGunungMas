/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas;

import com.excellentsystem.TokoEmasGunungMas.DAO.AmbilBarangDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.AmbilBarangHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.CetakBarcodeDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.CetakBarcodeHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.HancurDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.HancurHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.JenisDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.LogHargaDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PembelianHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PindahDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PindahHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.TambahBarangDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.TambahBarangHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.UserDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.VerifikasiDAO;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeHead;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiHead;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import com.excellentsystem.TokoEmasGunungMas.Model.Keuangan;
import com.excellentsystem.TokoEmasGunungMas.Model.LogHarga;
import com.excellentsystem.TokoEmasGunungMas.Model.Otoritas;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianHead;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanHead;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahHead;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import com.excellentsystem.TokoEmasGunungMas.Model.TambahBarangDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.TambahBarangHead;
import com.excellentsystem.TokoEmasGunungMas.Model.User;
import com.excellentsystem.TokoEmasGunungMas.Model.Verifikasi;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class Service {

    public static String saveUpdateKategori(Connection con, Kategori kategori) {
        try {
            con.setAutoCommit(false);

            KategoriDAO.update(con, kategori);

            GadaiDetailDAO.updateHargaSekarangByKategori(con, kategori);

            BarangDAO.updateHarga(con, kategori);

            LogHarga log = new LogHarga();
            log.setTanggal(tglBarang.format(new Date()));
            log.setKodeKategori(kategori.getKodeKategori());
            log.setHargaBeli(kategori.getHargaBeli());
            log.setHargaJual(kategori.getHargaJual());
            log.setUser(Main.user.getUsername());
            LogHargaDAO.insert(con, log);

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveUpdateJenis(Connection con, Jenis jenis) {
        try {
            con.setAutoCommit(false);

            JenisDAO.update(con, jenis);

            BarangDAO.updateKategori(con, jenis);

            BarangDAO.updateHarga(con, jenis.getKategori());

            GadaiDetailDAO.updateHargaSekarangByKategori(con, jenis.getKategori());

            StokBarangDAO.updateKategori(con, jenis);

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveUpdateUser(Connection con, User user) {
        try {
            con.setAutoCommit(false);

            UserDAO.update(con, user);

            for (Otoritas temp : user.getOtoritas()) {
                OtoritasDAO.update(con, temp);
            }

            for (Verifikasi temp : user.getVerifikasi()) {
                VerifikasiDAO.update(con, temp);
            }

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveNewUser(Connection con, User user) {
        try {
            con.setAutoCommit(false);

            UserDAO.insert(con, user);

            for (Otoritas temp : user.getOtoritas()) {
                OtoritasDAO.insert(con, temp);
            }

            for (Verifikasi temp : user.getVerifikasi()) {
                VerifikasiDAO.insert(con, temp);
            }

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String deleteUser(Connection con, User user) {
        try {
            con.setAutoCommit(false);

            UserDAO.delete(con, user);

            OtoritasDAO.delete(con, user);

            VerifikasiDAO.delete(con, user);

            con.commit();
            con.setAutoCommit(true);
            return "true";
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveTambahBarang(Connection con, TambahBarangHead tb) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            TambahBarangHeadDAO.insert(con, tb);

            List<String> jenis = new ArrayList<>();
            for (TambahBarangDetail d : tb.getAllDetail()) {
                TambahBarangDetailDAO.insert(con, d);
                if (!jenis.contains(d.getKodeJenis())) {
                    jenis.add(d.getKodeJenis());
                }
            }
            for (String j : jenis) {
                int qty = 0;
                double berat = 0;
                String kategori = "";
                for (TambahBarangDetail d : tb.getAllDetail()) {
                    if (j.equals(d.getKodeJenis())) {
                        qty = qty + d.getQty();
                        berat = berat + d.getBerat();
                        kategori = d.getKodeKategori();
                    }
                }
                StokBarang stok = StokBarangDAO.getStokNonBarcodeTodayByKodeJenis(con, j);
                if (stok == null) {
                    stok = new StokBarang();
                    stok.setTanggal(Main.sistem.getTglSystem());
                    stok.setKodeBarcode("");
                    stok.setKodeKategori(kategori);
                    stok.setKodeJenis(j);
                    stok.setKodeGudang("");
                    stok.setStokAwal(0);
                    stok.setBeratAwal(0);
                    stok.setBeratAsliAwal(0);
                    stok.setStokMasuk(qty);
                    stok.setBeratMasuk(berat);
                    stok.setBeratAsliMasuk(berat);
                    stok.setStokKeluar(0);
                    stok.setBeratKeluar(0);
                    stok.setBeratAsliKeluar(0);
                    stok.setStokAkhir(qty);
                    stok.setBeratAkhir(berat);
                    stok.setBeratAsliAkhir(berat);
                    StokBarangDAO.insert(con, stok);
                } else {
                    stok.setBeratMasuk((double) Math.round((stok.getBeratMasuk() + berat) * 100) / 100);
                    stok.setBeratAsliMasuk((double) Math.round((stok.getBeratAsliMasuk() + berat) * 100) / 100);
                    stok.setStokMasuk(stok.getStokMasuk() + qty);
                    stok.setBeratAkhir((double) Math.round((stok.getBeratAkhir() + berat) * 100) / 100);
                    stok.setBeratAsliAkhir((double) Math.round((stok.getBeratAsliAkhir() + berat) * 100) / 100);
                    stok.setStokAkhir(stok.getStokAkhir() + qty);
                    StokBarangDAO.update(con, stok);
                }
            }
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveAmbilBarang(Connection con, AmbilBarangHead ab) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            ab.setNoAmbil(AmbilBarangHeadDAO.getId(con));
            AmbilBarangHeadDAO.insert(con, ab);

            List<String> jenis = new ArrayList<>();
            int noUrut = 1;
            for (AmbilBarangDetail d : ab.getAllDetail()) {
                d.setNoAmbil(ab.getNoAmbil());
                d.setNoUrut(noUrut);
                AmbilBarangDetailDAO.insert(con, d);
                if (!jenis.contains(d.getKodeJenis())) {
                    jenis.add(d.getKodeJenis());
                }
                noUrut++;
            }
            for (String j : jenis) {
                int qty = 0;
                double berat = 0;
                for (AmbilBarangDetail d : ab.getAllDetail()) {
                    if (j.equals(d.getKodeJenis())) {
                        qty = qty + d.getQty();
                        berat = berat + d.getBerat();
                    }
                }
                StokBarang stok = StokBarangDAO.getStokNonBarcodeTodayByKodeJenis(con, j);
                if (stok == null) {
                    status = "Stok tidak ditemukan";
                } else {
                    if (stok.getBeratAkhir() - berat < 0 || stok.getStokAkhir() - qty < 0) {
                        status = "Stok " + stok.getKodeJenis() + " tidak mencukupi";
                    } else {
                        stok.setBeratKeluar((double) Math.round((stok.getBeratKeluar() + berat) * 100) / 100);
                        stok.setBeratAsliKeluar((double) Math.round((stok.getBeratAsliKeluar() + berat) * 100) / 100);
                        stok.setStokKeluar(stok.getStokKeluar() + qty);
                        stok.setBeratAkhir((double) Math.round((stok.getBeratAkhir() - berat) * 100) / 100);
                        stok.setBeratAsliAkhir((double) Math.round((stok.getBeratAsliAkhir() - berat) * 100) / 100);
                        stok.setStokAkhir(stok.getStokAkhir() - qty);
                        StokBarangDAO.update(con, stok);
                    }
                }
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveNewBarang(Connection con, Barang b) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            b.setKodeBarcode(BarangDAO.getId(con));
            BarangDAO.insert(con, b);

            StokBarang stok = new StokBarang();
            stok.setTanggal(sistem.getTglSystem());
            stok.setKodeBarcode(b.getKodeBarcode());
            stok.setKodeKategori(b.getKodeKategori());
            stok.setKodeJenis(b.getKodeJenis());
            stok.setKodeGudang(b.getKodeGudang());
            stok.setBeratAwal(0);
            stok.setBeratAsliAwal(0);
            stok.setStokAwal(0);
            stok.setBeratMasuk(b.getBerat());
            stok.setBeratAsliMasuk(b.getBeratAsli());
            stok.setStokMasuk(1);
            stok.setBeratKeluar(0);
            stok.setBeratAsliKeluar(0);
            stok.setStokKeluar(0);
            stok.setBeratAkhir(b.getBerat());
            stok.setBeratAsliAkhir(b.getBeratAsli());
            stok.setStokAkhir(1);
            StokBarangDAO.insert(con, stok);

            StokBarang stokKeluar = StokBarangDAO.getStokNonBarcodeTodayByKodeJenis(con, b.getKodeJenis());
            if (stokKeluar == null) {
                status = "Stok barang " + b.getKodeJenis() + " tidak ditemukan";
            } else {
                if (stokKeluar.getBeratAkhir() - b.getBeratAsli() < 0 || stokKeluar.getStokAkhir() - 1 < 0) {
                    status = "Stok " + stokKeluar.getKodeJenis() + " tidak mencukupi";
                } else {
                    stokKeluar.setBeratKeluar((double) Math.round((stokKeluar.getBeratKeluar() + b.getBeratAsli()) * 100) / 100);
                    stokKeluar.setBeratAsliKeluar((double) Math.round((stokKeluar.getBeratAsliKeluar() + b.getBeratAsli()) * 100) / 100);
                    stokKeluar.setStokKeluar(stokKeluar.getStokKeluar() + 1);
                    stokKeluar.setBeratAkhir((double) Math.round((stokKeluar.getBeratAkhir() - b.getBeratAsli()) * 100) / 100);
                    stokKeluar.setBeratAsliAkhir((double) Math.round((stokKeluar.getBeratAsliAkhir() - b.getBeratAsli()) * 100) / 100);
                    stokKeluar.setStokAkhir(stokKeluar.getStokAkhir() - 1);
                    StokBarangDAO.update(con, stokKeluar);
                }
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String savePindahGudang(Connection con, PindahHead pb) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            pb.setNoPindah(PindahHeadDAO.getId(con));
            PindahHeadDAO.insert(con, pb);

            for (PindahDetail d : pb.getAllDetail()) {
                d.setNoPindah(pb.getNoPindah());
                PindahDetailDAO.insert(con, d);
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if (b == null) {
                    status = "Barang tidak ditemukan";
                } else {
                    StokBarang stok = StokBarangDAO.getStokBarcodeTodayByKodeBarcodeAndKodeGudang(con, b.getKodeBarcode(), b.getKodeGudang());
                    if (stok == null) {
                        status = "Stok tidak ditemukan";
                    } else {
                        if (stok.getStokAkhir() != 1) {
                            status = "Stok sudah tidak ada";
                        } else {
                            if (pb.getGudangTujuan().equals(b.getKodeGudang())) {
                                stok.setBeratMasuk((double) Math.round((stok.getBeratMasuk() + b.getBerat()) * 100) / 100);
                                stok.setBeratAsliMasuk((double) Math.round((stok.getBeratAsliMasuk() + b.getBeratAsli()) * 100) / 100);
                                stok.setStokMasuk(stok.getStokMasuk() + 1);
                                stok.setBeratKeluar((double) Math.round((stok.getBeratKeluar() + b.getBerat()) * 100) / 100);
                                stok.setBeratAsliKeluar((double) Math.round((stok.getBeratAsliKeluar() + b.getBeratAsli()) * 100) / 100);
                                stok.setStokKeluar(stok.getStokKeluar() + 1);
                                StokBarangDAO.update(con, stok);
                            } else {
                                StokBarang stokMasuk = StokBarangDAO.getStokBarcodeTodayByKodeBarcodeAndKodeGudang(con, b.getKodeBarcode(), pb.getGudangTujuan());
                                if (stokMasuk == null) {
                                    stokMasuk = new StokBarang();
                                    stokMasuk.setTanggal(stok.getTanggal());
                                    stokMasuk.setKodeBarcode(b.getKodeBarcode());
                                    stokMasuk.setKodeKategori(b.getKodeKategori());
                                    stokMasuk.setKodeJenis(b.getKodeJenis());
                                    stokMasuk.setKodeGudang(pb.getGudangTujuan());
                                    stokMasuk.setBeratAwal(0);
                                    stokMasuk.setBeratAsliAwal(0);
                                    stokMasuk.setStokAwal(0);
                                    stokMasuk.setBeratMasuk(b.getBerat());
                                    stokMasuk.setBeratAsliMasuk(b.getBeratAsli());
                                    stokMasuk.setStokMasuk(1);
                                    stokMasuk.setBeratKeluar(0);
                                    stokMasuk.setBeratAsliKeluar(0);
                                    stokMasuk.setStokKeluar(0);
                                    stokMasuk.setBeratAkhir(b.getBerat());
                                    stokMasuk.setBeratAsliAkhir(b.getBeratAsli());
                                    stokMasuk.setStokAkhir(1);
                                    StokBarangDAO.insert(con, stokMasuk);
                                } else {
                                    stokMasuk.setBeratMasuk((double) Math.round((stokMasuk.getBeratMasuk() + b.getBerat()) * 100) / 100);
                                    stokMasuk.setBeratAsliMasuk((double) Math.round((stokMasuk.getBeratAsliMasuk() + b.getBeratAsli()) * 100) / 100);
                                    stokMasuk.setStokMasuk(stokMasuk.getStokMasuk() + 1);
                                    stokMasuk.setBeratAkhir((double) Math.round((stokMasuk.getBeratAkhir() + b.getBerat()) * 100) / 100);
                                    stokMasuk.setBeratAsliAkhir((double) Math.round((stokMasuk.getBeratAsliAkhir() + b.getBeratAsli()) * 100) / 100);
                                    stokMasuk.setStokAkhir(stokMasuk.getStokAkhir() + 1);
                                    StokBarangDAO.update(con, stokMasuk);
                                }
                                stok.setBeratKeluar(stok.getBeratKeluar() + b.getBerat());
                                stok.setBeratAsliKeluar(stok.getBeratAsliKeluar() + b.getBeratAsli());
                                stok.setStokKeluar(stok.getStokKeluar() + 1);
                                stok.setBeratAkhir(stok.getBeratAkhir() - b.getBerat());
                                stok.setBeratAsliAkhir(stok.getBeratAsliAkhir() - b.getBeratAsli());
                                stok.setStokAkhir(stok.getStokAkhir() - 1);
                                StokBarangDAO.update(con, stok);

                                b.setKodeGudang(pb.getGudangTujuan());
                                BarangDAO.update(con, b);
                            }
                        }
                    }
                }
            }
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveHancurBarang(Connection con, HancurHead hb) {
        try {
            con.setAutoCommit(false);
            String status = "true";
            
            hb.setNoHancur(HancurHeadDAO.getId(con));
            HancurHeadDAO.insert(con, hb);
            for (HancurDetail d : hb.getAllDetail()) {
                d.setNoHancur(hb.getNoHancur());
                HancurDetailDAO.insert(con, d);
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if (b == null) {
                    status = "Barang tidak ditemukan";
                } else {
                    StokBarang stok = StokBarangDAO.getStokBarcodeTodayByKodeBarcodeAndKodeGudang(con, b.getKodeBarcode(), b.getKodeGudang());
                    if (stok == null) {
                        status = "Stok tidak ditemukan";
                    } else {
                        if (stok.getStokAkhir() != 1) {
                            status = "Stok sudah tidak ada";
                        } else {
                            stok.setBeratKeluar((double) Math.round((stok.getBeratKeluar() + b.getBerat()) * 100) / 100);
                            stok.setBeratAsliKeluar((double) Math.round((stok.getBeratAsliKeluar() + b.getBeratAsli()) * 100) / 100);
                            stok.setStokKeluar(stok.getStokKeluar() + 1);
                            stok.setBeratAkhir((double) Math.round((stok.getBeratAkhir() - b.getBerat()) * 100) / 100);
                            stok.setBeratAsliAkhir((double) Math.round((stok.getBeratAsliAkhir() - b.getBeratAsli()) * 100) / 100);
                            stok.setStokAkhir(stok.getStokAkhir() - 1);
                            StokBarangDAO.update(con, stok);

                            b.setStatusBarang("Hancur");
                            b.setDeletedDate(tglSql.format(new Date()));
                            b.setDeletedBy(hb.getKodeUser());
                            BarangDAO.update(con, b);
                        }
                    }
                }
            }
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveCetakBarcode(Connection con, CetakBarcodeHead cb) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            cb.setNoCetak(CetakBarcodeHeadDAO.getId(con));
            CetakBarcodeHeadDAO.insert(con, cb);
            
            for (CetakBarcodeDetail d : cb.getAllDetail()) {
                d.setNoCetak(cb.getNoCetak());
                CetakBarcodeDetailDAO.insert(con, d);
            }
            
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String savePenjualan(Connection con, PenjualanHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";
            
            p.setNoPenjualan(PenjualanHeadDAO.getId(con));
            PenjualanHeadDAO.insert(con, p);
            
            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setKategori("Penjualan");
            k.setDeskripsi(p.getNoPenjualan());
            k.setJumlahRp(p.getGrandtotal());
            k.setKodeUser(p.getKodeSales());
            KeuanganDAO.insert(con, k);
            
            for (PenjualanDetail d : p.getAllDetail()) {
                d.setNoPenjualan(p.getNoPenjualan());
                PenjualanDetailDAO.insert(con, d);
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if (b == null) {
                    status = "Barang tidak ditemukan";
                } else {
                    StokBarang stok = StokBarangDAO.getStokBarcodeTodayByKodeBarcodeAndKodeGudang(con, b.getKodeBarcode(), b.getKodeGudang());
                    if (stok == null) {
                        status = "Stok tidak ditemukan";
                    } else {
                        if (stok.getStokAkhir() != 1) {
                            status = "Stok sudah tidak ada";
                        } else {
                            stok.setBeratKeluar((double) Math.round((stok.getBeratKeluar() + b.getBerat()) * 100) / 100);
                            stok.setBeratAsliKeluar((double) Math.round((stok.getBeratAsliKeluar() + b.getBeratAsli()) * 100) / 100);
                            stok.setStokKeluar(stok.getStokKeluar() + 1);
                            stok.setBeratAkhir((double) Math.round((stok.getBeratAkhir() - b.getBerat()) * 100) / 100);
                            stok.setBeratAsliAkhir((double) Math.round((stok.getBeratAsliAkhir() - b.getBeratAsli()) * 100) / 100);
                            stok.setStokAkhir(stok.getStokAkhir() - 1);
                            StokBarangDAO.update(con, stok);

                            b.setStatusBarang("Terjual");
                            b.setHargaJual(d.getHargaJual());
                            b.setSoldDate(tglSql.format(new Date()));
                            b.setSoldBy(p.getKodeSales());
                            BarangDAO.update(con, b);
                        }
                    }
                }
            }
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPenjualan(Connection con, PenjualanHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";
            
            PenjualanHeadDAO.update(con, p);
            
            Keuangan keu = KeuanganDAO.get(con, "Penjualan", p.getNoPenjualan(), p.getGrandtotal());
            KeuanganDAO.delete(con, keu.getNoKeuangan());
            
            List<Kategori> listKategori = KategoriDAO.getAll(con);
            for (PenjualanDetail d : p.getAllDetail()) {
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if (b == null) {
                    status = "Barang tidak ditemukan";
                } else {
                    StokBarang stok = StokBarangDAO.getStokBarcodeTodayByKodeBarcodeAndKodeGudang(con, b.getKodeBarcode(), b.getKodeGudang());
                    if (stok == null) {
                        status = "Stok tidak ditemukan";
                    } else {
                        stok.setBeratKeluar((double) Math.round((stok.getBeratKeluar() - b.getBerat()) * 100) / 100);
                        stok.setBeratAsliKeluar((double) Math.round((stok.getBeratAsliKeluar() - b.getBeratAsli()) * 100) / 100);
                        stok.setStokKeluar(stok.getStokKeluar() - 1);
                        stok.setBeratAkhir((double) Math.round((stok.getBeratAkhir() + b.getBerat()) * 100) / 100);
                        stok.setBeratAsliAkhir((double) Math.round((stok.getBeratAsliAkhir() + b.getBeratAsli()) * 100) / 100);
                        stok.setStokAkhir(stok.getStokAkhir() + 1);
                        StokBarangDAO.update(con, stok);

                        b.setStatusBarang("Tersedia");
                        b.setSoldDate("2000-01-01 00:00:00");
                        b.setSoldBy("");
                        boolean statusKategori = false;
                        for (Kategori k : listKategori) {
                            if (d.getKodeKategori().equals(k.getKodeKategori())) {
                                b.setHargaJual(k.getHargaJual() * b.getBerat());
                                statusKategori = true;
                            }
                        }
                        if (!statusKategori) {
                            status = "Kategori barang " + b.getKodeKategori() + " tidak ditemukan";
                        }
                        BarangDAO.update(con, b);
                    }
                }
            }
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String savePembelian(Connection con, PembelianHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";
            
            p.setNoPembelian(PembelianHeadDAO.getId(con));
            PembelianHeadDAO.insert(con, p);
            
            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setKategori("Pembelian");
            k.setDeskripsi(p.getNoPembelian());
            k.setJumlahRp(-p.getTotalPembelian());
            k.setKodeUser(p.getKodeSales());
            KeuanganDAO.insert(con, k);
            
            int noUrut = 1;
            for (PembelianDetail d : p.getAllDetail()) {
                d.setNoPembelian(p.getNoPembelian());
                d.setNoUrut(noUrut);
                PembelianDetailDAO.insert(con, d);
                noUrut++;
                
                if(d.getKodeBarcode()!=null){
                    PenjualanDetail pjl = PenjualanDetailDAO.getByKodeBarcode(con, d.getKodeBarcode());
                    pjl.setNoPembelian(p.getNoPembelian());
                    PenjualanDetailDAO.update(con, pjl);
                }
            }
            
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPembelian(Connection con, PembelianHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";
            
            PembelianHeadDAO.update(con, p);
                        
            Keuangan keu = KeuanganDAO.get(con, "Pembelian", p.getNoPembelian(), -p.getTotalPembelian());
            KeuanganDAO.delete(con, keu.getNoKeuangan());
            
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String saveGadai(Connection con, GadaiHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";
            
            GadaiHeadDAO.insert(con, p);
            
            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setKategori("Terima Gadai");
            k.setDeskripsi(p.getNoGadai());
            k.setJumlahRp(-p.getTotalPinjaman());
            k.setKodeUser(p.getKodeSales());
            KeuanganDAO.insert(con, k);
            
            int noUrut = 1;
            for (GadaiDetail d : p.getAllDetail()) {
                d.setNoUrut(noUrut);
                GadaiDetailDAO.insert(con, d);
                noUrut++;
            }
            
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalGadai(Connection con, GadaiHead p) {
        try {
            con.setAutoCommit(false);
            String status = "true";
            
            GadaiHeadDAO.update(con, p);
            
            Keuangan keu = KeuanganDAO.get(con, "Terima Gadai", p.getNoGadai(), -p.getTotalPinjaman());
            KeuanganDAO.delete(con, keu.getNoKeuangan());
            
            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String savePelunasanGadai(Connection con, GadaiHead gadaiLama, GadaiHead gadaiBaru) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            GadaiHeadDAO.update(con, gadaiLama);

            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setKategori("Pelunasan Gadai");
            k.setDeskripsi(gadaiLama.getNoGadai());
            k.setJumlahRp(gadaiLama.getTotalPinjaman());
            k.setKodeUser(gadaiLama.getSalesLunas());
            KeuanganDAO.insert(con, k);

            Keuangan kbunga = new Keuangan();
            kbunga.setNoKeuangan(KeuanganDAO.getId(con));
            kbunga.setTglKeuangan(tglSql.format(new Date()));
            kbunga.setKategori("Bunga Gadai");
            kbunga.setDeskripsi(gadaiLama.getNoGadai());
            kbunga.setJumlahRp(gadaiLama.getBungaRp());
            kbunga.setKodeUser(gadaiLama.getSalesLunas());
            KeuanganDAO.insert(con, kbunga);
            
            if (gadaiBaru != null) {
                GadaiHeadDAO.insert(con, gadaiBaru);
                for (GadaiDetail d : gadaiLama.getAllDetail()) {
                    d.setNoGadai(gadaiBaru.getNoGadai());
                    GadaiDetailDAO.insert(con, d);
                }
                Keuangan k2 = new Keuangan();
                k2.setNoKeuangan(KeuanganDAO.getId(con));
                k2.setTglKeuangan(tglSql.format(new Date()));
                k2.setKategori("Terima Gadai");
                k2.setDeskripsi(gadaiBaru.getNoGadai());
                k2.setJumlahRp(-gadaiBaru.getTotalPinjaman());
                k2.setKodeUser(gadaiBaru.getKodeSales());
                KeuanganDAO.insert(con, k2);
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }

    public static String batalPelunasanGadai(Connection con, GadaiHead gadai, String userBatal) {
        try {
            con.setAutoCommit(false);
            String status = "true";

            Keuangan keu = KeuanganDAO.get(con, "Pelunasan Gadai", gadai.getNoGadai(), gadai.getTotalPinjaman());
            KeuanganDAO.delete(con, keu.getNoKeuangan());
            
            Keuangan keu2 = KeuanganDAO.get(con, "Bunga Gadai", gadai.getNoGadai(), gadai.getBungaRp());
            KeuanganDAO.delete(con, keu2.getNoKeuangan());
            
            gadai.setTglLunas("2000-01-01 00:00:00");
            gadai.setSalesLunas("");
            gadai.setStatus("Belum Lunas");
            gadai.setBungaRp(gadai.getBungaKomp());
            GadaiHeadDAO.update(con, gadai);

            List<GadaiHead> listGadai = GadaiHeadDAO.getAllByTglGadaiAndStatusNotLike(con,
                    sistem.getTglSystem(),
                    sistem.getTglSystem(), "Batal Gadai");
            GadaiHead gadaiBaru = null;
            for (GadaiHead g : listGadai) {
                if (g.getKeterangan().contains(gadai.getNoGadai())) {
                    gadaiBaru = g;
                }
            }
            if (gadaiBaru != null) {
                gadaiBaru.setStatus("Batal Gadai");
                gadaiBaru.setTglBatal(tglSql.format(new Date()));
                gadaiBaru.setSalesBatal(userBatal);
                GadaiHeadDAO.update(con, gadaiBaru);

                Keuangan keu3 = KeuanganDAO.get(con, "Terima Gadai", gadaiBaru.getNoGadai(), gadai.getTotalPinjaman());
                KeuanganDAO.delete(con, keu3.getNoKeuangan());
            }

            if (status.equals("true")) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        } catch (Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
}
