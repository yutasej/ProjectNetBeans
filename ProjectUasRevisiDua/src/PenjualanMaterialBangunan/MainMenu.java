package PenjualanMaterialBangunan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;

public class MainMenu extends javax.swing.JInternalFrame {

    Connection conn;
    Connection conn2;
    Connection conn3;
    Connection conn4;
    ResultSet rs;
    ResultSet rs2;
    ResultSet rs3;
    Statement stm;
    Statement stm2;
    Statement stm3;
    Statement stm4;
    PreparedStatement pst;
    DefaultTableModel tabmod;
    DefaultTableModel tabmod2;
    DefaultTableModel tabmod3;
    DefaultTableModel tabmod4;
    DefaultTableModel tabmod5;

    public MainMenu() {
        initComponents();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/UasPbo", "root", "");
            conn2 = DriverManager.getConnection("jdbc:mysql://localhost/UasPbo", "root", "");
            conn3 = DriverManager.getConnection("jdbc:mysql://localhost/UasPbo", "root", "");
            conn4 = DriverManager.getConnection("jdbc:mysql://localhost/UasPbo", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

//        Memanggil Method
        atur_akses();
        offAksesBarang();
        offAksesKonsumen();
        tabelTransaksi();
        tampilInputBarang();
        tampilInputKonsumen();
        updateComboBoxBarang();
        updateComboBoxKonsumen();
    }

    private void atur_akses() {
        nomorTransaksi.setEnabled(false);
        txtTanggal.setEnabled(false);
        namaBarang.setEnabled(false);
        namaKonsumen.setEnabled(false);
        hargaBarang.setEnabled(false);
        kembalianUang.setEnabled(false);
        totalBayar.setEnabled(false);
        nomorTransaksi.setEnabled(false);
        inputKodeBarang.setEnabled(false);
        inputKodeKonsumen.setEnabled(false);
        totalHarga.setEnabled(false);
    }

    private void offAksesBarang() {
        editBarang.setEnabled(false);
        hapusBarang.setEnabled(false);
        refreshBarang.setEnabled(false);
    }

    private void offAksesKonsumen() {
        editKonsumen.setEnabled(false);
        hapusKonsumen.setEnabled(false);
        refreshKonsumen.setEnabled(false);
    }

    private void onAksesBarang() {
        editBarang.setEnabled(true);
        hapusBarang.setEnabled(true);
        refreshBarang.setEnabled(true);
    }

    private void onAksesKonsumen() {
        editKonsumen.setEnabled(true);
        hapusKonsumen.setEnabled(true);
        refreshKonsumen.setEnabled(true);
    }

    private void tabelTransaksi() {
        tabmod4 = new DefaultTableModel();

        jTable1.setModel(tabmod4);

        tabmod4.addColumn("No Transaksi");
        tabmod4.addColumn("Kode Barang");
        tabmod4.addColumn("Nama Barang");
        tabmod4.addColumn("Harga");
        tabmod4.addColumn("Jumlah");
        tabmod4.addColumn("Total");

        utama();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        txtTanggal.setText(formatter.format(date));
        totalBayar.setText("0");
        txtbayar.setText("0");
        kembalianUang.setText("0");
    }

    private void tampilInputBarang() {
        tabmod3 = new DefaultTableModel();

        tabmod3.addColumn("Kode Barang");
        tabmod3.addColumn("Nama Barang");
        tabmod3.addColumn("Satuan Barang");
        tabmod3.addColumn("Harga Barang");
        tabmod3.addColumn("Stok Barang");
        jTable4.setModel(tabmod3);

        String kueri = "select * from barang_2";

        try {
            stm2 = conn2.createStatement();
            rs2 = stm2.executeQuery(kueri);

            while (rs2.next()) {
                tabmod3.addRow(new Object[]{
                    rs2.getString("kd_brg"),
                    rs2.getString("nama_brg"),
                    rs2.getString("satuan_brg"),
                    rs2.getString("harga_brg"),
                    rs2.getString("stok")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tampilInputKonsumen() {
        tabmod5 = new DefaultTableModel();

        tabmod5.addColumn("Kode Konsumen");
        tabmod5.addColumn("Nama Konsumen");
        tabmod5.addColumn("Alamat Konsumen");
        jTable2.setModel(tabmod5);

        String kueri3 = "select * from konsumen";

        try {
            stm3 = conn3.createStatement();
            rs3 = stm3.executeQuery(kueri3);

            while (rs3.next()) {
                tabmod5.addRow(new Object[]{
                    rs3.getString("kd_kons"),
                    rs3.getString("nama_kons"),
                    rs3.getString("alamat")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nomorFaktur() {
        try {
            stm = conn.createStatement();
            String sql4 = "select * from transaksiKonsumen order by no_jual desc";
            rs = stm.executeQuery(sql4);
            if (rs.next()) {
                String noJual = rs.getString("no_jual").substring(2);
                String tr = "" + (Integer.parseInt(noJual) + 1);
                String nol = "";

                if (tr.length() == 1) {
                    nol = "000";
                } else if (tr.length() == 2) {
                    nol = "00";
                } else if (tr.length() == 3) {
                    nol = "0";
                } else if (tr.length() == 4) {
                    nol = "";
                }
                nomorTransaksi.setText("NT" + nol + tr);
            } else {
                nomorTransaksi.setText("NT0001");
            }
            rs.close();
            stm.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void nomorKodeBarang() {
        try {
            stm = conn.createStatement();
            String sql4 = "select * from barang_2 order by kd_brg desc";
            rs = stm.executeQuery(sql4);
            if (rs.next()) {
                String kodeBrg = rs.getString("kd_brg").substring(2);
                String B = "" + (Integer.parseInt(kodeBrg) + 1);
                String nol = "";

                if (B.length() == 1) {
                    nol = "000";
                } else if (B.length() == 2) {
                    nol = "00";
                } else if (B.length() == 3) {
                    nol = "0";
                } else if (B.length() == 4) {
                    nol = "";
                }
                inputKodeBarang.setText("B-" + nol + B);
            } else {
                inputKodeBarang.setText("B-0001");
            }
            rs.close();
            stm.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void nomorKodeKonsumen() {
        try {
            stm = conn.createStatement();
            String sql4 = "select * from konsumen order by kd_kons desc";
            rs = stm.executeQuery(sql4);
            if (rs.next()) {
                String kodeKons = rs.getString("kd_kons").substring(2);
                String K = "" + (Integer.parseInt(kodeKons) + 1);
                String nol = "";

                if (K.length() == 1) {
                    nol = "000";
                } else if (K.length() == 2) {
                    nol = "00";
                } else if (K.length() == 3) {
                    nol = "0";
                } else if (K.length() == 4) {
                    nol = "";
                }
                inputKodeKonsumen.setText("K-" + nol + K);
            } else {
                inputKodeKonsumen.setText("K-0001");
            }
            rs.close();
            stm.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void updateComboBoxBarang() {
        String sql = "select * from barang_2";

        try {
            stm2 = conn2.createStatement();
            rs2 = stm2.executeQuery(sql);

            barangComboBox.removeAllItems();

            while (rs2.next()) {
                barangComboBox.addItem(rs2.getString("kd_brg"));
            }
            clear2();
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateComboBoxKonsumen() {
        String sql = "select * from konsumen";

        try {
            stm3 = conn3.createStatement();
            rs3 = stm3.executeQuery(sql);

            kodeKonsumenComboBox.removeAllItems();

            while (rs3.next()) {
                kodeKonsumenComboBox.addItem(rs3.getString("kd_kons"));
            }
            kodeKonsumenComboBox.setSelectedIndex(-1);
            namaKonsumen.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadData() {
        tabmod4 = (DefaultTableModel) jTable1.getModel();
        tabmod4.addRow(new Object[]{
            nomorTransaksi.getText(),
            barangComboBox.getSelectedItem(),
            namaBarang.getText(),
            hargaBarang.getText(),
            jumlahPembelian.getText(),
            totalHarga.getText(),});
    }

    public void kosong() {
        tabmod4 = (DefaultTableModel) jTable1.getModel();

        while (tabmod4.getRowCount() > 0) {
            tabmod4.removeRow(0);
        }
    }

    public void utama() {
        nomorTransaksi.setText("");
        kodeKonsumenComboBox.setSelectedIndex(-1);
        namaBarang.setText("");
        hargaBarang.setText("");
        jumlahPembelian.setText("");
        nomorFaktur();
        nomorKodeBarang();
        nomorKodeKonsumen();
    }

    public void clear() {
        kodeKonsumenComboBox.setSelectedIndex(-1);
        namaKonsumen.setText("");
        totalBayar.setText("0");
        txtbayar.setText("0");
        kembalianUang.setText("0");
        totalHarga.setText("");
    }

    public void clear2() {
        barangComboBox.setSelectedIndex(-1);
        namaBarang.setText("");
        hargaBarang.setText("");
        jumlahPembelian.setText("");
    }

    public void clear3() {
        inputNamaBarang.setText("");
        inputSatuanBarang.setText("");
        inputHargaBarang.setText("");
        inputStokBarang.setText("");
    }

    public void clear4() {
        inputNamaKonsumen.setText("");
        inputAlamatKonsumen.setText("");
    }

    public void clear5() {
        inputNamaBarang.setText("");
        inputSatuanBarang.setText("");
        inputHargaBarang.setText("");
        inputStokBarang.setText("");
    }

    public void clear6() {
        inputNamaKonsumen.setText("");
        inputAlamatKonsumen.setText("");
    }

    public void tambahTransaksi() {

        if (barangComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih Barang Terlebih Dahulu!");
        } else if (jumlahPembelian.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan Jumlah Pembelian Terlebih Dahulu!");
        } else {
            int jumlah, harga, total;
            int baris = jTable4.getRowCount();

            if (barangComboBox.getSelectedIndex() == 0) {
                if (Integer.parseInt(jTable4.getValueAt(0, 4).toString()) < Integer.valueOf(jumlahPembelian.getText())) {
                    JOptionPane.showMessageDialog(null, "Maaf Jumlah Pembelian Melibihi Stok Barang");
                } else {
                    jumlah = Integer.valueOf(jumlahPembelian.getText());
                    harga = Integer.valueOf(hargaBarang.getText());
                    total = jumlah * harga;

                    totalBayar.setText(String.valueOf(total));
                    totalHarga.setText(String.valueOf(total));

                    loadData();
                    totalBiaya();
                    clear2();
                    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Ke Tabel!");
                }
            } else if (barangComboBox.getSelectedIndex() == 1) {
                if (Integer.parseInt(jTable4.getValueAt(1, 4).toString()) < Integer.valueOf(jumlahPembelian.getText())) {
                    JOptionPane.showMessageDialog(null, "Maaf Jumlah Pembelian Melibihi Stok Barang");
                } else {
                    jumlah = Integer.valueOf(jumlahPembelian.getText());
                    harga = Integer.valueOf(hargaBarang.getText());
                    total = jumlah * harga;

                    totalBayar.setText(String.valueOf(total));
                    totalHarga.setText(String.valueOf(total));

                    loadData();
                    totalBiaya();
                    clear2();
                    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Ke Tabel!");
                }
            } else if (barangComboBox.getSelectedIndex() == 2) {
                if (Integer.parseInt(jTable4.getValueAt(2, 4).toString()) < Integer.valueOf(jumlahPembelian.getText())) {
                    JOptionPane.showMessageDialog(null, "Maaf Jumlah Pembelian Melibihi Stok Barang");
                } else {
                    jumlah = Integer.valueOf(jumlahPembelian.getText());
                    harga = Integer.valueOf(hargaBarang.getText());
                    total = jumlah * harga;

                    totalBayar.setText(String.valueOf(total));
                    totalHarga.setText(String.valueOf(total));

                    loadData();
                    totalBiaya();
                    clear2();
                    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Ke Tabel!");
                }
            } else if (barangComboBox.getSelectedIndex() == 3) {
                if (Integer.parseInt(jTable4.getValueAt(3, 4).toString()) < Integer.valueOf(jumlahPembelian.getText())) {
                    JOptionPane.showMessageDialog(null, "Maaf Jumlah Pembelian Melibihi Stok Barang");
                } else {
                    jumlah = Integer.valueOf(jumlahPembelian.getText());
                    harga = Integer.valueOf(hargaBarang.getText());
                    total = jumlah * harga;

                    totalBayar.setText(String.valueOf(total));
                    totalHarga.setText(String.valueOf(total));

                    loadData();
                    totalBiaya();
                    clear2();
                    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Ke Tabel!");
                }
            } else if (barangComboBox.getSelectedIndex() == 4) {
                if (Integer.parseInt(jTable4.getValueAt(4, 4).toString()) < Integer.valueOf(jumlahPembelian.getText())) {
                    JOptionPane.showMessageDialog(null, "Maaf Jumlah Pembelian Melibihi Stok Barang");
                } else {
                    jumlah = Integer.valueOf(jumlahPembelian.getText());
                    harga = Integer.valueOf(hargaBarang.getText());
                    total = jumlah * harga;

                    totalBayar.setText(String.valueOf(total));
                    totalHarga.setText(String.valueOf(total));

                    loadData();
                    totalBiaya();
                    clear2();
                    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Ke Tabel!");
                }
            } else if (barangComboBox.getSelectedIndex() == 5) {
                if (Integer.parseInt(jTable4.getValueAt(5, 4).toString()) < Integer.valueOf(jumlahPembelian.getText())) {
                    JOptionPane.showMessageDialog(null, "Maaf Jumlah Pembelian Melibihi Stok Barang");
                } else {
                    jumlah = Integer.valueOf(jumlahPembelian.getText());
                    harga = Integer.valueOf(hargaBarang.getText());
                    total = jumlah * harga;

                    totalBayar.setText(String.valueOf(total));
                    totalHarga.setText(String.valueOf(total));

                    loadData();
                    totalBiaya();
                    clear2();
                    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Ke Tabel!");
                }
            } else if (barangComboBox.getSelectedIndex() == 6) {
                if (Integer.parseInt(jTable4.getValueAt(6, 4).toString()) < Integer.valueOf(jumlahPembelian.getText())) {
                    JOptionPane.showMessageDialog(null, "Maaf Jumlah Pembelian Melibihi Stok Barang");
                } else {
                    jumlah = Integer.valueOf(jumlahPembelian.getText());
                    harga = Integer.valueOf(hargaBarang.getText());
                    total = jumlah * harga;

                    totalBayar.setText(String.valueOf(total));
                    totalHarga.setText(String.valueOf(total));

                    loadData();
                    totalBiaya();
                    clear2();
                    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Ke Tabel!");
                }
            }
        }
    }

    private void totalBiaya() {
        int jumlahBaris = jTable1.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(jTable1.getValueAt(i, 3).toString());
            hargaBarang = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }
        totalBayar.setText(String.valueOf(totalBiaya));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        nomorTransaksi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        kodeKonsumenComboBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        namaKonsumen = new javax.swing.JTextField();
        barangComboBox = new javax.swing.JComboBox<>();
        namaBarang = new javax.swing.JTextField();
        hargaBarang = new javax.swing.JTextField();
        jumlahPembelian = new javax.swing.JTextField();
        hapusButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tambahButton = new javax.swing.JButton();
        simpanButton = new javax.swing.JButton();
        hapusSemuaItem = new javax.swing.JButton();
        cetakButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        totalBayar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtbayar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        kembalianUang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        totalHarga = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        inputKodeKonsumen = new javax.swing.JTextField();
        inputNamaKonsumen = new javax.swing.JTextField();
        inputAlamatKonsumen = new javax.swing.JTextField();
        tambahKonsumen = new javax.swing.JButton();
        refreshKonsumen = new javax.swing.JButton();
        editKonsumen = new javax.swing.JButton();
        hapusKonsumen = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        inputKodeBarang = new javax.swing.JTextField();
        inputNamaBarang = new javax.swing.JTextField();
        inputSatuanBarang = new javax.swing.JTextField();
        inputHargaBarang = new javax.swing.JTextField();
        inputStokBarang = new javax.swing.JTextField();
        tambahBarang = new javax.swing.JButton();
        refreshBarang = new javax.swing.JButton();
        editBarang = new javax.swing.JButton();
        hapusBarang = new javax.swing.JButton();

        setTitle("Menu Utama");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/picture1.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Karisma Market");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Menjual Berbagai Material Bangunan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(496, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel4.setText("Menu Utama");

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Master Barang");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Transaksi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Master Konsumen");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(79, 79, 79))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 300, 630));

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setLayout(new java.awt.CardLayout());

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("No Transaksi");

        nomorTransaksi.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tanggal Transaksi");

        jLabel7.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kode Konsumen");

        kodeKonsumenComboBox.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        kodeKonsumenComboBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                kodeKonsumenComboBoxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        kodeKonsumenComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeKonsumenComboBoxActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nama Konsumen");

        namaKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        barangComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangComboBoxActionPerformed(evt);
            }
        });

        jumlahPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahPembelianActionPerformed(evt);
            }
        });
        jumlahPembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumlahPembelianKeyPressed(evt);
            }
        });

        hapusButton.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        hapusButton.setText("Hapus Item");
        hapusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusButtonActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        tambahButton.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        tambahButton.setText("Tambah");
        tambahButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahButtonActionPerformed(evt);
            }
        });

        simpanButton.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        simpanButton.setText("Simpan");
        simpanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanButtonActionPerformed(evt);
            }
        });

        hapusSemuaItem.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        hapusSemuaItem.setText("Hapus Semua Item");
        hapusSemuaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusSemuaItemActionPerformed(evt);
            }
        });

        cetakButton.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        cetakButton.setText("Cetak");

        jLabel9.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total Bayar");

        totalBayar.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Bayar");

        txtbayar.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Kembali");

        kembalianUang.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Kode Barang");

        jLabel19.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Nama");

        jLabel20.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Harga");

        jLabel21.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Jumlah");

        txtTanggal.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Total");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(barangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(hargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomorTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(namaKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(kodeKonsumenComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jumlahPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(totalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(simpanButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cetakButton))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(hapusButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(hapusSemuaItem)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tambahButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kembalianUang, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(totalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(25, 25, 25))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(kodeKonsumenComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomorTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(namaKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(barangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jumlahPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(totalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusButton)
                    .addComponent(tambahButton)
                    .addComponent(hapusSemuaItem))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(simpanButton)
                            .addComponent(cetakButton))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kembalianUang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addContainerGap(45, Short.MAX_VALUE))))
        );

        jPanel4.add(jPanel5, "card2");

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        jLabel23.setFont(new java.awt.Font("Liberation Sans", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Master Konsumen");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel24.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Kode Konsumen");

        jLabel27.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Nama Konsumen");

        jLabel28.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Alamat Konsumen");

        inputKodeKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N

        inputNamaKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        inputNamaKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNamaKonsumenActionPerformed(evt);
            }
        });
        inputNamaKonsumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputNamaKonsumenKeyPressed(evt);
            }
        });

        inputAlamatKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        inputAlamatKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputAlamatKonsumenActionPerformed(evt);
            }
        });
        inputAlamatKonsumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputAlamatKonsumenKeyPressed(evt);
            }
        });

        tambahKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        tambahKonsumen.setText("Simpan");
        tambahKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahKonsumenActionPerformed(evt);
            }
        });

        refreshKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        refreshKonsumen.setText("Batal");
        refreshKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshKonsumenActionPerformed(evt);
            }
        });

        editKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        editKonsumen.setText("Edit");
        editKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editKonsumenActionPerformed(evt);
            }
        });

        hapusKonsumen.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        hapusKonsumen.setText("Hapus");
        hapusKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusKonsumenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 307, Short.MAX_VALUE)
                        .addComponent(jLabel23)
                        .addGap(289, 289, 289))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputAlamatKonsumen, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(inputNamaKonsumen)
                            .addComponent(inputKodeKonsumen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(refreshKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hapusKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(tambahKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 106, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputKodeKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputNamaKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputAlamatKonsumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 131, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6, "card3");

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));

        jLabel12.setFont(new java.awt.Font("Liberation Sans", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Master Barang");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jLabel13.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Kode Barang");

        jLabel14.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nama Barang");

        jLabel15.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Satuan Barang");

        jLabel16.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Harga Barang");

        jLabel18.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Stok Barang");

        inputKodeBarang.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        inputNamaBarang.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        inputNamaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputNamaBarangKeyPressed(evt);
            }
        });

        inputSatuanBarang.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        inputSatuanBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputSatuanBarangKeyPressed(evt);
            }
        });

        inputHargaBarang.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        inputHargaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputHargaBarangKeyPressed(evt);
            }
        });

        inputStokBarang.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        inputStokBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputStokBarangActionPerformed(evt);
            }
        });
        inputStokBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputStokBarangKeyPressed(evt);
            }
        });

        tambahBarang.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        tambahBarang.setText("Simpan");
        tambahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahBarangActionPerformed(evt);
            }
        });

        refreshBarang.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        refreshBarang.setText("Batal");
        refreshBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBarangActionPerformed(evt);
            }
        });
        refreshBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                refreshBarangKeyPressed(evt);
            }
        });

        editBarang.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        editBarang.setText("Edit");
        editBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBarangActionPerformed(evt);
            }
        });

        hapusBarang.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        hapusBarang.setText("Hapus");
        hapusBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBarangActionPerformed(evt);
            }
        });
        hapusBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hapusBarangKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(jLabel12)
                        .addGap(0, 325, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(inputNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(91, 91, 91)
                                            .addComponent(inputKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(inputSatuanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputHargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputStokBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(refreshBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(hapusBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(tambahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(inputKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tambahBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(editBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(inputNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(refreshBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hapusBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(inputSatuanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(inputHargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(inputStokBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7, "card4");

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 910, 630));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barangComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangComboBoxActionPerformed
        switch (barangComboBox.getSelectedIndex()) {

            case 0:
                namaBarang.setText((String) jTable4.getValueAt(0, 1));
                hargaBarang.setText((String) jTable4.getValueAt(0, 3));
                break;

            case 1:
                namaBarang.setText((String) jTable4.getValueAt(1, 1));
                hargaBarang.setText((String) jTable4.getValueAt(1, 3));
                break;

            case 2:
                namaBarang.setText((String) jTable4.getValueAt(2, 1));
                hargaBarang.setText((String) jTable4.getValueAt(2, 3));
                break;

            case 3:
                namaBarang.setText((String) jTable4.getValueAt(3, 1));
                hargaBarang.setText((String) jTable4.getValueAt(3, 3));
                break;

            case 4:
                namaBarang.setText((String) jTable4.getValueAt(4, 1));
                hargaBarang.setText((String) jTable4.getValueAt(4, 3));
                break;

            case 5:
                namaBarang.setText((String) jTable4.getValueAt(5, 1));
                hargaBarang.setText((String) jTable4.getValueAt(5, 3));
                break;

            case 6:
                namaBarang.setText((String) jTable4.getValueAt(6, 1));
                hargaBarang.setText((String) jTable4.getValueAt(6, 3));
                break;
        }
    }//GEN-LAST:event_barangComboBoxActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int pilih = JOptionPane.showConfirmDialog(null, "Yakin Ingin Keluar Aplikasi ?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);

        if (pilih == JOptionPane.OK_OPTION) {
            System.exit(0);
        } else {
            JOptionPane.getRootFrame();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void kodeKonsumenComboBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_kodeKonsumenComboBoxPopupMenuWillBecomeInvisible
    }//GEN-LAST:event_kodeKonsumenComboBoxPopupMenuWillBecomeInvisible

    private void kodeKonsumenComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeKonsumenComboBoxActionPerformed
        switch (kodeKonsumenComboBox.getSelectedIndex()) {

            case 0:
                namaKonsumen.setText((String) jTable2.getValueAt(0, 1));
                break;

            case 1:
                namaKonsumen.setText((String) jTable2.getValueAt(1, 1));
                break;

            case 2:
                namaKonsumen.setText((String) jTable2.getValueAt(2, 1));
                break;

            case 3:
                namaKonsumen.setText((String) jTable2.getValueAt(3, 1));
                break;

            case 4:
                namaKonsumen.setText((String) jTable2.getValueAt(4, 1));
                break;

            case 5:
                namaKonsumen.setText((String) jTable2.getValueAt(5, 1));
                break;

            case 6:
                namaKonsumen.setText((String) jTable2.getValueAt(6, 1));
                break;
        }
    }//GEN-LAST:event_kodeKonsumenComboBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jPanel4.removeAll();
        jPanel4.repaint();
        jPanel4.revalidate();

        jPanel4.add(jPanel7);
        jPanel4.repaint();
        jPanel4.revalidate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void hapusSemuaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusSemuaItemActionPerformed
        if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Item Kosong!");
        } else {
            int pilih = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menghapus Semua Item ?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);

            if (pilih == JOptionPane.OK_OPTION) {

                kosong();
                totalBiaya();
                txtbayar.setText("0");
                kembalianUang.setText("0");
                totalHarga.setText("");
                JOptionPane.showMessageDialog(null, "Berhasil Menghapus Semua Item!");
            } else {
                JOptionPane.getRootFrame();
            }
        }
    }//GEN-LAST:event_hapusSemuaItemActionPerformed

    private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed

        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Tekan Item Yang Akan Dihapus!");
        } else {
            int konfirmasi = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menghapus Item Yang Dipilih ?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);

            if (konfirmasi == JOptionPane.OK_OPTION) {
                tabmod4 = (DefaultTableModel) jTable1.getModel();
                int baris = jTable1.getSelectedRow();

                tabmod4.removeRow(baris);
                totalBiaya();
                txtbayar.setText("0");
                kembalianUang.setText("0");
                totalHarga.setText("");
                JOptionPane.showMessageDialog(null, "Berhasil Menghapus Item Yang Dipilih!");
            } else {
                JOptionPane.getRootFrame();
            }
        }
    }//GEN-LAST:event_hapusButtonActionPerformed

    private void jumlahPembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahPembelianKeyPressed

    }//GEN-LAST:event_jumlahPembelianKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jPanel4.removeAll();
        jPanel4.repaint();
        jPanel4.revalidate();

        jPanel4.add(jPanel5);
        jPanel4.repaint();
        jPanel4.revalidate();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        onAksesBarang();

        inputKodeBarang.setText((String) jTable4.getValueAt(jTable4.getSelectedRow(), 0));
        inputNamaBarang.setText((String) jTable4.getValueAt(jTable4.getSelectedRow(), 1));
        inputSatuanBarang.setText((String) jTable4.getValueAt(jTable4.getSelectedRow(), 2));
        inputHargaBarang.setText((String) jTable4.getValueAt(jTable4.getSelectedRow(), 3));
        inputStokBarang.setText((String) jTable4.getValueAt(jTable4.getSelectedRow(), 4));

        tambahBarang.setEnabled(false);
    }//GEN-LAST:event_jTable4MouseClicked

    private void tambahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahBarangActionPerformed
        if (inputNamaBarang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi Nama Barang!");
        } else if (inputSatuanBarang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi Satuan Barang!");
        } else if (inputHargaBarang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi Harga Barang!");
        } else if (inputStokBarang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi Stok Barang!");
        } else {
            String sql3 = "insert into barang_2 values ('" + inputKodeBarang.getText() + "',"
                    + "'" + inputNamaBarang.getText() + "',"
                    + "'" + inputSatuanBarang.getText() + "',"
                    + "'" + inputHargaBarang.getText() + "',"
                    + "'" + inputStokBarang.getText() + "')";
            try {
                stm2 = conn2.createStatement();
                stm2.executeUpdate(sql3);
                tampilInputBarang();
                updateComboBoxBarang();
                JOptionPane.showMessageDialog(rootPane, "Berhasil Menambah Data!");
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear3();
            nomorKodeBarang();
        }
    }//GEN-LAST:event_tambahBarangActionPerformed

    private void jumlahPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahPembelianActionPerformed
        tambahTransaksi();
    }//GEN-LAST:event_jumlahPembelianActionPerformed

    private void tambahButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahButtonActionPerformed
        tambahTransaksi();
    }//GEN-LAST:event_tambahButtonActionPerformed

    private void txtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarActionPerformed
        int total, bayar, kembalian;

        total = Integer.valueOf(totalBayar.getText());
        bayar = Integer.valueOf(txtbayar.getText());

        if (total > bayar) {
            JOptionPane.showMessageDialog(null, "Uang Tidak Cukup!");
        } else {
            kembalian = bayar - total;
            kembalianUang.setText(String.valueOf(kembalian));
        }
    }//GEN-LAST:event_txtbayarActionPerformed

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        if (kodeKonsumenComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih Konsumen Terlebih Dahulu!");
        } else if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Pilih Barang Yang Akan Dibeli!");
        } else if (Integer.valueOf(txtbayar.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Bayar Terlebih Dahulu!");
        } else {

            try {
                int baris = jTable1.getRowCount();

                for (int i = 0; i < baris; i++) {
                    String sql6 = "insert into transaksiBarang values ('" + jTable1.getValueAt(i, 0) + "', '" + jTable1.getValueAt(i, 1) + "','" + jTable1.getValueAt(i, 2) + "','" + jTable1.getValueAt(i, 3) + "','" + jTable1.getValueAt(i, 4) + "','" + jTable1.getValueAt(i, 5) + "')";
                    pst = conn4.prepareStatement(sql6);
                    pst.executeUpdate();
                    pst.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error tambah transaksi barang");
            }

            String sql5 = "insert into transaksiKonsumen values ('" + nomorTransaksi.getText() + "',"
                    + "'" + kodeKonsumenComboBox.getSelectedItem() + "',"
                    + "'" + txtTanggal.getText() + "',"
                    + "'" + namaKonsumen.getText() + "',"
                    + "'" + totalBayar.getText() + "')";

            try {
                stm4 = conn4.createStatement();
                stm4.executeUpdate(sql5);
                JOptionPane.showMessageDialog(rootPane, "Berhasil Menambah Laporan Transaksi!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error tambah transaksi konsumen");
            }
            clear();
            utama();
            nomorFaktur();
            kosong();
            tampilInputBarang();
        }
    }//GEN-LAST:event_simpanButtonActionPerformed

    private void refreshBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBarangActionPerformed
        nomorKodeBarang();
        tambahBarang.setEnabled(true);
        offAksesBarang();
        clear5();
    }//GEN-LAST:event_refreshBarangActionPerformed

    private void refreshBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_refreshBarangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshBarangKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jPanel4.removeAll();
        jPanel4.repaint();
        jPanel4.revalidate();

        jPanel4.add(jPanel6);
        jPanel4.repaint();
        jPanel4.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tambahKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahKonsumenActionPerformed
        if (inputNamaKonsumen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi Nama Konsumen!");
        } else if (inputAlamatKonsumen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi Alamat Konsumen!");
        } else {
            String sql7 = "insert into konsumen values ('" + inputKodeKonsumen.getText() + "',"
                    + "'" + inputNamaKonsumen.getText() + "',"
                    + "'" + inputAlamatKonsumen.getText() + "')";
            try {
                stm3 = conn3.createStatement();
                stm3.executeUpdate(sql7);
                tampilInputKonsumen();
                updateComboBoxKonsumen();
                JOptionPane.showMessageDialog(rootPane, "Berhasil Menambah Data!");
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            clear4();
            nomorKodeKonsumen();
        }
    }//GEN-LAST:event_tambahKonsumenActionPerformed

    private void refreshKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshKonsumenActionPerformed
        nomorKodeKonsumen();
        tambahKonsumen.setEnabled(true);
        offAksesKonsumen();
        clear6();
    }//GEN-LAST:event_refreshKonsumenActionPerformed

    private void inputStokBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputStokBarangActionPerformed

    }//GEN-LAST:event_inputStokBarangActionPerformed

    private void inputAlamatKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputAlamatKonsumenActionPerformed

    }//GEN-LAST:event_inputAlamatKonsumenActionPerformed

    private void inputNamaKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNamaKonsumenActionPerformed

    }//GEN-LAST:event_inputNamaKonsumenActionPerformed

    private void inputNamaKonsumenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputNamaKonsumenKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            inputAlamatKonsumen.requestFocus();
        }
    }//GEN-LAST:event_inputNamaKonsumenKeyPressed

    private void inputNamaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputNamaBarangKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            inputSatuanBarang.requestFocus();
        }
    }//GEN-LAST:event_inputNamaBarangKeyPressed

    private void inputSatuanBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputSatuanBarangKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            inputHargaBarang.requestFocus();
        }
    }//GEN-LAST:event_inputSatuanBarangKeyPressed

    private void inputHargaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputHargaBarangKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            inputStokBarang.requestFocus();
        }
    }//GEN-LAST:event_inputHargaBarangKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
    }//GEN-LAST:event_jTable1KeyPressed

    private void editBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBarangActionPerformed
        String sql = "update barang_2 SET nama_brg = '" + inputNamaBarang.getText() + "', satuan_brg = '" + inputSatuanBarang.getText() + "', harga_brg = '" + inputHargaBarang.getText() + "', stok = '" + inputStokBarang.getText() + "' WHERE kd_brg = '" + jTable4.getValueAt(jTable4.getSelectedRow(), 0) + "'";

        int confirm = JOptionPane.showConfirmDialog(null, "Yakin Edit Data ?", "Perhatian !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == 0) {
            try {
                stm = conn.createStatement();
                stm.executeUpdate(sql);
                tampilInputBarang();
                clear5();
                nomorKodeBarang();
                tambahBarang.setEnabled(true);
                offAksesBarang();
                updateComboBoxBarang();
                JOptionPane.showMessageDialog(rootPane, "Berhasil Mengedit Data!");
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_editBarangActionPerformed

    private void hapusBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBarangActionPerformed
        if (jTable4.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih Barang Yang Akan Dihapus!");
        } else {
            String sql = "delete from barang_2 where kd_brg = '" + jTable4.getValueAt(jTable4.getSelectedRow(), 0) + "'";
            int confirm = JOptionPane.showConfirmDialog(null, "Yakin Hapus Data ?", "Perhatian !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm == 0) {
                try {
                    stm = conn.createStatement();
                    stm.executeUpdate(sql);
                    nomorKodeBarang();
                    clear5();
                    tampilInputBarang();
                    tambahBarang.setEnabled(true);
                    offAksesBarang();
                    updateComboBoxBarang();
                    JOptionPane.showMessageDialog(rootPane, "Berhasil Menghapus Data!");
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.getRootFrame();
            }
        }
    }//GEN-LAST:event_hapusBarangActionPerformed

    private void hapusBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hapusBarangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_hapusBarangKeyPressed

    private void inputStokBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputStokBarangKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            tambahBarang.requestFocus();
        }
    }//GEN-LAST:event_inputStokBarangKeyPressed

    private void editKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editKonsumenActionPerformed
        String sql = "update konsumen SET nama_kons = '" + inputNamaKonsumen.getText() + "', alamat = '" + inputAlamatKonsumen.getText() + "' WHERE kd_kons = '" + jTable2.getValueAt(jTable2.getSelectedRow(), 0) + "'";

        int confirm = JOptionPane.showConfirmDialog(null, "Yakin Edit Data ?", "Perhatian !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == 0) {
            try {
                stm = conn.createStatement();
                stm.executeUpdate(sql);
                tampilInputKonsumen();
                clear6();
                nomorKodeKonsumen();
                tambahKonsumen.setEnabled(true);
                offAksesKonsumen();
                updateComboBoxKonsumen();
                JOptionPane.showMessageDialog(rootPane, "Berhasil Mengedit Data!");
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_editKonsumenActionPerformed

    private void hapusKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusKonsumenActionPerformed
        if (jTable2.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih Konsumen Yang Akan Dihapus!");
        } else {
            String sql = "delete from konsumen where kd_kons = '" + jTable2.getValueAt(jTable2.getSelectedRow(), 0) + "'";
            int confirm = JOptionPane.showConfirmDialog(null, "Yakin Hapus Data ?", "Perhatian !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm == 0) {
                try {
                    stm = conn.createStatement();
                    stm.executeUpdate(sql);
                    nomorKodeKonsumen();
                    clear6();
                    tampilInputKonsumen();
                    tambahKonsumen.setEnabled(true);
                    offAksesKonsumen();
                    updateComboBoxKonsumen();
                    JOptionPane.showMessageDialog(rootPane, "Berhasil Menghapus Data!");
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.getRootFrame();
            }
        }
    }//GEN-LAST:event_hapusKonsumenActionPerformed

    private void inputAlamatKonsumenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputAlamatKonsumenKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            tambahKonsumen.requestFocus();
        }
    }//GEN-LAST:event_inputAlamatKonsumenKeyPressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed

    }//GEN-LAST:event_jTable2KeyPressed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        onAksesKonsumen();

        inputKodeKonsumen.setText((String) jTable2.getValueAt(jTable2.getSelectedRow(), 0));
        inputNamaKonsumen.setText((String) jTable2.getValueAt(jTable2.getSelectedRow(), 1));
        inputAlamatKonsumen.setText((String) jTable2.getValueAt(jTable2.getSelectedRow(), 2));

        tambahKonsumen.setEnabled(false);
    }//GEN-LAST:event_jTable2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> barangComboBox;
    private javax.swing.JButton cetakButton;
    private javax.swing.JButton editBarang;
    private javax.swing.JButton editKonsumen;
    private javax.swing.JButton hapusBarang;
    private javax.swing.JButton hapusButton;
    private javax.swing.JButton hapusKonsumen;
    private javax.swing.JButton hapusSemuaItem;
    private javax.swing.JTextField hargaBarang;
    private javax.swing.JTextField inputAlamatKonsumen;
    private javax.swing.JTextField inputHargaBarang;
    private javax.swing.JTextField inputKodeBarang;
    private javax.swing.JTextField inputKodeKonsumen;
    private javax.swing.JTextField inputNamaBarang;
    private javax.swing.JTextField inputNamaKonsumen;
    private javax.swing.JTextField inputSatuanBarang;
    private javax.swing.JTextField inputStokBarang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jumlahPembelian;
    private javax.swing.JTextField kembalianUang;
    private javax.swing.JComboBox<String> kodeKonsumenComboBox;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JTextField namaKonsumen;
    private javax.swing.JTextField nomorTransaksi;
    private javax.swing.JButton refreshBarang;
    private javax.swing.JButton refreshKonsumen;
    private javax.swing.JButton simpanButton;
    private javax.swing.JButton tambahBarang;
    private javax.swing.JButton tambahButton;
    private javax.swing.JButton tambahKonsumen;
    private javax.swing.JTextField totalBayar;
    private javax.swing.JTextField totalHarga;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtbayar;
    // End of variables declaration//GEN-END:variables

}
