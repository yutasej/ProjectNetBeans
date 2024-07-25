-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Waktu pembuatan: 25 Jul 2024 pada 10.47
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `UasPbo`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `kd_barang` int(2) NOT NULL,
  `barang` varchar(12) DEFAULT NULL,
  `satuan` varchar(12) DEFAULT NULL,
  `harga` int(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`kd_barang`, `barang`, `satuan`, `harga`) VALUES
(1, 'Batu Bata', 'Buah', 612),
(2, 'Beton Instan', 'Sak', 51083),
(3, 'Semen', 'Sak', 10000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang_2`
--

CREATE TABLE `barang_2` (
  `kd_brg` char(6) NOT NULL,
  `nama_brg` varchar(20) DEFAULT NULL,
  `satuan_brg` varchar(10) DEFAULT NULL,
  `harga_brg` int(11) DEFAULT NULL,
  `stok` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `barang_2`
--

INSERT INTO `barang_2` (`kd_brg`, `nama_brg`, `satuan_brg`, `harga_brg`, `stok`) VALUES
('B-0001', 'Batu Bata', 'Buah', 1000, 10),
('B-0002', 'Deck Lantai', 'Lembar', 50000, 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `konsumen`
--

CREATE TABLE `konsumen` (
  `kd_kons` char(6) NOT NULL,
  `nama_kons` varchar(20) DEFAULT NULL,
  `alamat` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `konsumen`
--

INSERT INTO `konsumen` (`kd_kons`, `nama_kons`, `alamat`) VALUES
('K-0001', 'Yutase', 'Jl. Kanguru'),
('K-0002', 'Helsa', 'Jl. Sendangguwo');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pelanggan`
--

CREATE TABLE `pelanggan` (
  `nama` varchar(20) DEFAULT NULL,
  `kota` varchar(10) DEFAULT NULL,
  `barang` varchar(12) DEFAULT NULL,
  `satuan` varchar(12) DEFAULT NULL,
  `jumlah` int(12) DEFAULT NULL,
  `totalHarga` int(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksiBarang`
--

CREATE TABLE `transaksiBarang` (
  `no_jual` char(10) NOT NULL,
  `kd_brg` char(6) NOT NULL,
  `nama_brg` varchar(20) DEFAULT NULL,
  `harga_brg` decimal(10,0) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `total` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaksiBarang`
--

INSERT INTO `transaksiBarang` (`no_jual`, `kd_brg`, `nama_brg`, `harga_brg`, `jumlah`, `total`) VALUES
('NT0001', 'B-0001', 'Batu Bata', 1000, 10, 10000),
('NT0001', 'B-0002', 'Deck Lantai', 50000, 10, 500000),
('NT0002', 'B-0001', 'Batu Bata', 1000, 10, 10000),
('NT0002', 'B-0002', 'Deck Lantai', 50000, 10, 500000);

--
-- Trigger `transaksiBarang`
--
DELIMITER $$
CREATE TRIGGER `update_stok_barang` AFTER INSERT ON `transaksiBarang` FOR EACH ROW update barang_2 set stok = stok - new.jumlah where kd_brg = new.kd_brg
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksiKonsumen`
--

CREATE TABLE `transaksiKonsumen` (
  `no_jual` char(10) NOT NULL,
  `kd_kons` char(6) DEFAULT NULL,
  `tgl_jual` date DEFAULT NULL,
  `nama_kons` varchar(20) DEFAULT NULL,
  `totalBeli` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaksiKonsumen`
--

INSERT INTO `transaksiKonsumen` (`no_jual`, `kd_kons`, `tgl_jual`, `nama_kons`, `totalBeli`) VALUES
('NT0001', 'K-0001', '2024-07-21', 'Yutase', 510000),
('NT0002', 'K-0002', '2024-07-21', 'Helsa', 510000);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kd_barang`);

--
-- Indeks untuk tabel `barang_2`
--
ALTER TABLE `barang_2`
  ADD PRIMARY KEY (`kd_brg`);

--
-- Indeks untuk tabel `konsumen`
--
ALTER TABLE `konsumen`
  ADD PRIMARY KEY (`kd_kons`);

--
-- Indeks untuk tabel `transaksiBarang`
--
ALTER TABLE `transaksiBarang`
  ADD PRIMARY KEY (`no_jual`,`kd_brg`);

--
-- Indeks untuk tabel `transaksiKonsumen`
--
ALTER TABLE `transaksiKonsumen`
  ADD PRIMARY KEY (`no_jual`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
