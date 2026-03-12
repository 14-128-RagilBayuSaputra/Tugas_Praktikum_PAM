# Tugas Praktikum PAM - Pertemuan 3: Compose Multiplatform Basics

**Identitas Mahasiswa:**
- **Nama:** Ragil Bayu Saputra
- **NIM:** 123140128

---

## Deskripsi Tugas
Aplikasi "My Profile App" ini dibuat menggunakan **Compose Multiplatform** dengan menerapkan paradigma UI Deklaratif. Aplikasi ini telah memenuhi seluruh kriteria penilaian:

1. **Layout Implementation:** Menggunakan kombinasi `Column` (susunan vertikal utama), `Row` (susunan horizontal pada info kontak), dan `Box` (stacking untuk background foto profil).
2. **Reusable Composables:** Terdapat 3 fungsi Composable yang *reusable* yaitu `ProfileHeader`, `InfoItem`, dan `ProfileCard`.
3. **UI Components:** Menggunakan komponen dasar seperti `Text`, `Button`, `Icon` (dari Material Icons Extended), dan `Card`.
4. **Modifiers:** Menerapkan *styling* menggunakan modifier seperti `padding`, `fillMaxSize`, `clip(CircleShape)`, dan `background`.
5. **🌟 BONUS (+10%):** Mengimplementasikan *state management* sederhana (`remember { mutableStateOf }`) yang dipadukan dengan `AnimatedVisibility` untuk memunculkan dan menyembunyikan informasi kontak dengan animasi transisi yang mulus.

---

## Bukti Screenshot Aplikasi

### 1. Tampilan Awal (Info Kontak Disembunyikan)
![Output Pertama](bukti/Output%20pertama.png)

### 2. Tampilan Setelah Tombol Diklik (Info Kontak Ditampilkan)
![Output Kedua](bukti/Output%20kedua.png)