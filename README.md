# GameVerse - Aplikasi Katalog Video Game

## Informasi Mahasiswa dan Proyek

- **Nama Project**: GameVerse (Aplikasi Katalog Video Game)
- **Nama**: [Nama Mahasiswa]
- **NIM**: [NIM]
- **Shift Praktikum**: [Shift Praktikum]

---

## Deskripsi Proyek

GameVerse adalah aplikasi Android berbasis Kotlin dan Jetpack Compose yang dikembangkan untuk menampilkan katalog video game secara interaktif. Aplikasi ini terhubung langsung dengan REST API publik dari RAWG Video Games Database untuk menyajikan informasi game secara cepat, responsif, dan dinamis.

Aplikasi ini mengusung desain tema kustom Cyberpunk/Neon dengan antarmuka yang bersih, mudah digunakan, serta mendukung fitur pencarian dan penyaringan data (filtering) game berdasarkan berbagai kriteria.

---

## Fitur Utama Aplikasi

1. **Katalog Game Interaktif**
   - Menampilkan daftar video game dalam bentuk grid.
   - Dilengkapi informasi judul, gambar latar belakang, rating, tahun rilis, dan kategori genre.

2. **Pencarian Game Real-Time**
   - Kolom pencarian game berbasis input teks dinamis.
   - Indikator pemuatan data (*loading*) yang hanya muncul pada area konten tanpa mengganggu status input teks atau papan ketik (*soft keyboard*).

3. **Menu Penyaringan Data (Search by filters)**
   - **Genre**: Menyaring game berdasarkan kategori seperti Action, RPG, Shooter, Adventure, Indie, Strategy, Racing, Sports, dan lainnya.
   - **Minimal Rating**: Menyaring game berdasarkan rating minimum (misalnya 4.5+, 4.0+, 3.5+).
   - **Tahun Rilisan**: Menyaring game berdasarkan tahun peluncuran (2024, 2023, 2022, 2021, 2020, 2019).
   - **Reset Filter**: Tombol untuk mengembalikan daftar game ke setelan awal.

4. **Halaman Detail Game**
   - Menyajikan informasi lengkap mengenai game yang dipilih.
   - Menampilkan deskripsi teks lengkap, tanggal rilis, rating, daftar platform yang didukung (PC, PlayStation, Xbox, Nintendo Switch, dll.), genre, dan skor Metacritic.

---

## Tangkapan Layar Aplikasi (Screenshots)

| Halaman Utama & Katalog | Fitur Pencarian & Filter | Halaman Detail Game |
| :---: | :---: | :---: |
| ![Halaman Utama](https://github.com/user-attachments/assets/ae1a7c23-5941-4714-910f-381d43bbcc02) | ![Fitur Pencarian & Filter](https://github.com/user-attachments/assets/cf1ef9fd-e16c-4e04-ac6e-021b40180ff2) | ![Halaman Detail Game](https://github.com/user-attachments/assets/bc2f83d9-3994-419d-98dd-6f24dce1b27d) |
| *Tampilan Utama Katalog* | *Fitur Pencarian & Filter* | *Tampilan Detail Game* |


## Teknologi dan Arsitektur

- **Bahasa Pemrograman**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Pola Arsitektur**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit 2 & Gson Converter
- **Image Loading**: Coil Compose
- **Asynchronous & Reactive Stream**: Kotlin Coroutines & StateFlow
- **Navigasi**: Jetpack Navigation Compose
- **Penyedia Data API**: RAWG Video Games Database API

---

## Struktur Direktori Proyek

```text
com.pemmob.videogame/
├── data/
│   ├── model/         # Data Transfer Object (GameDto, GameDetailDto, GenreDto, dll.)
│   ├── remote/        # Interface Retrofit API Service & ApiClient
│   └── repository/    # GameRepository untuk mengambil dan menyaring data dari API
├── ui/
│   ├── components/    # Komponen UI umum (LoadingView, ErrorView, LoadingGameCard)
│   ├── detail/        # Screen dan ViewModel untuk Halaman Detail Game
│   ├── home/          # Screen, ViewModel, UiState, dan FilterState untuk Halaman Utama
│   ├── navigation/    # Pengaturan rute navigasi aplikasi (AppNavHost)
│   └── theme/         # Sistem tema warna dan tipografi (CyberColors, Theme, Type)
└── MainActivity.kt    # Entry point utama aplikasi Android
```

---

## Petunjuk Penggunaan dan Cara Menjalankan

### Prasyarat

- Android Studio versi terkini (Ladybug / Hedgehog atau yang lebih baru)
- Java Development Kit (JDK 17+)
- Minimum Android SDK: API Level 24 (Android 7.0)
- Target Android SDK: API Level 34 (Android 14)

### Langkah-langkah Jalankan Proyek

1. **Kloning Repository Git**:
   ```bash
   git clone https://github.com/FahriID563/GameVerse.git
   cd GameVerse
   ```

2. **Mendapatkan API Key RAWG**:
   - Dapatkan API Key gratis dengan mendaftar pada situs [RAWG API Docs](https://rawg.io/apidocs).

3. **Konfigurasi API Key**:
   - Buka file `local.properties` pada direktori utama proyek.
   - Tambahkan baris berikut dan masukkan API Key Anda:
     ```properties
     RAWG_API_KEY="masukkan_api_key_rawg_anda_di_sini"
     ```

4. **Kompilasi dan Jalankan**:
   - Buka proyek di Android Studio.
   - Lakukan Gradle Sync.
   - Jalankan aplikasi pada Emulator Android atau Perangkat Fisik.
