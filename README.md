# Tugas Besar Aljabar Linear dan Geometri
#### Kelompok Chuakz
* Imanuel Sebastian Girsang (13522058)
* Bagas Sambega Rosyada (13522071)
* Ellijah Darrelshane S. (13522097) 

Program ini adalah program yang digunakan untuk mencari penyelesaian dan pengolahan matriks dan/atau 
sistem persamaan linear. Program ini menggunakan Bahasa Java dan terdiri dari beberapa library yang berisi
beberapa metode pengolahan dan penyelesaian matriks/SPL.

## Fitur Utama
#### 1. Mencari Penyelesaian Sistem Linear n-Variabel
#### 2. Mencari Determinan Matriks Ukuran n x n
#### 3. Mencari Invers Matriks
#### 4. Mencari Persamaan Melalui Interpolasi Polinom
#### 5. Regresi Linear Berganda
#### 6. Interpolasi Bicubic Spline
#### 7. Image Scaling


## Struktur Program
```
├── README.md
├── src/
│   ├── Aplikasi
│   ├── Utils
│   ├── matrix
│   └── Main.java
├── bin
├── test/
│   ├── input
│   └── output
└── docs
```

1. src berisi _source code_ dari program.\
   a. Folder Main merupakan file utama yang memanggil seluruh fungsi.\
   b. Folder matrix berisi class untuk matriks dan berisi fungsi-fungsi pengolahan matriks.\
   c. Folder Utils berisi pengolahan data dan input, seperti konversi matriks dari/ke file, pengubahan string menjadi desimal, dsb\
   d. Folder Aplikasi berisi implementasi fungsi-fungsi utama pada fungsi Sistem Persamaan Linear, Determinan, Invers, Interpolasi Polinomial, _Bicubic Spline Interpolation_, Regresi Linear Berganda, dan _Image Scaling_
3. bin berisi compiled code dari src
4. test berisi file-file yang menjadi input dan/atau output untuk diolah menggunakan fungsi-fungsi dalam program
5. docs berisi file laporan tugas besar

## Cara Penggunaan
#### Instalasi
1. Install Java melalui website resmi: https://www.java.com/en/download/manual.jsp
2. Install Java Development Kit (JDK)
3. Download repository atau clone repo


#### Penggunaan Umum
Gunakan Java 8.0 atau ke atas untuk menggunakan program ini. Untuk masuk ke menu utama, masuk ke terminal/cmd dan ubah direktori ke folder tempat program ini berada.
Untuk menjalankan program, run program menggunakan **"run.bat"** di folder utama. 

#### Menu Utama
Menu terdiri atas 7 opsi:
1. Penyelesaian Sistem Linear n-Variabel
2. Determinan Matriks Ukuran n x n
3. Invers Matriks
4. Interpolasi Polinom
5. Regresi Linear Berganda
6. Interpolasi Bicubic Spline
7. Keluar Program

Masukkan input ke terminal berupa angka untuk memilih operasi yang diinginkan.

1. Untuk menggunakan fungsi 1, 4, 5, dan 6, gunakan matriks _augmented_ yang menyimpan persamaan dan hasilnya di kolom paling kanan
2. Untuk menggunakan fungsi Image Scaling, masukkan _full path_ dari gambar ke program
3. Fungsi determinan dan invers tidak memerlukan matriks berbentuk _augmented_

