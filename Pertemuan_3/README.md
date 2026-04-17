# Aplikasi Catatan (Notes App) - Pertemuan 3 - Pertemuan 5

Aplikasi pencatat berbasis **Kotlin Multiplatform (KMP)** yang dibangun menggunakan **Jetpack Compose**. Proyek ini difokuskan pada implementasi arsitektur Navigasi (Routing) antar layar dan desain antarmuka pengguna (UI/UX) yang responsif menggunakan Compose Navigation.

## 🚀 Fitur Utama

* **Bottom Navigation Bar:** Navigasi utama yang persisten di bagian bawah layar untuk perpindahan cepat antar menu utama (Notes, Favorites, Profile).
* **Notes Screen & FAB:** Layar utama yang menampilkan daftar catatan, dilengkapi dengan *Floating Action Button* (FAB) untuk menambah catatan baru.
* **Add Note Form:** Formulir interaktif dengan *Top App Bar* (tombol kembali) dan *TextField* untuk menginput Judul serta Isi Catatan.
* **Profile Screen Dinamis:** Halaman profil pengguna yang memuat informasi data diri, lengkap dengan fitur *Toggle Dark Mode* interaktif dan menu *collapsible* untuk informasi kontak.
* **State Preserved Navigation:** Perpindahan antar layar (routing) yang mulus tanpa kehilangan *state* halaman sebelumnya.

## 🛠️ Teknologi yang Digunakan

* **Kotlin Multiplatform (KMP)**
* **Jetpack Compose** (UI Toolkit)
* **Compose Navigation** (`androidx.navigation:navigation-compose`)
* **Material Design 3** (Material3 Components)

## 📸 Tampilan Aplikasi (Screenshots)

Berikut adalah hasil dari implementasi antarmuka dan navigasi aplikasi:

### 1. Halaman Notes & Favorites
Dua layar utama yang saling terhubung melalui Bottom Navigation. Halaman Notes dilengkapi dengan tombol aksi melayang (+).

|         Halaman Notes          |            Halaman Favorites            |
|:------------------------------:|:---------------------------------------:|
| ![Notes Screen](bukti/Note.png) | ![Favorites Screen](bukti/Favorite.png) |

### 2. Formulir Tambah Catatan (Add Note)
Layar detail yang terbuka saat tombol FAB ditekan, mendemonstrasikan perpindahan *route* yang lebih dalam (nested navigation).

|          Formulir Tambah Catatan          |
|:-----------------------------------------:|
| ![Add Note Screen](bukti/Tambah_note.png) |

### 3. Halaman Profil
Menampilkan identitas *Mobile Developer*, pengaturan tema aplikasi (Dark Mode), dan tombol aksi.

|           Halaman Profil            |
|:-----------------------------------:|
| ![Profile Screen](bukti/Profile.png) |

---
*Dibuat untuk memenuhi tugas Pemrograman Aplikasi Mobile.*