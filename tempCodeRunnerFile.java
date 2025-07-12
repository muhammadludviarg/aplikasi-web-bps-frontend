import java.util.*;
import java.io.File;

// Abstract User class
abstract class User {
    protected int id;
    protected String nama;
    protected String username;
    protected String password;
    
    public User(int id, String nama, String username, String password) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.password = password;
    }
    
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    public void logout() {
        System.out.println("User " + nama + " has logged out.");
    }
    
    public int getID() {
        return id;
    }
    
    public String getNama() {
        return nama;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

// Nasabah class
class Nasabah extends User {
    private String alamat;
    private String telepon;
    private double saldo;
    
    public Nasabah(int id, String nama, String username, String password, 
                   String alamat, String telepon) {
        super(id, nama, username, password);
        this.alamat = alamat;
        this.telepon = telepon;
        this.saldo = 0.0;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public List<Setoran> getRiwayatSetoran(int idNasabah) {
        List<Setoran> riwayat = new ArrayList<>();
        return riwayat;
    }
    
    public boolean ajukanTarikSaldo(int idNasabah, double jumlah) {
        if (jumlah <= saldo && jumlah > 0) {
            return true;
        }
        return false;
    }
    
    public List<TarikSaldo> getRiwayatTarikSaldo(int idNasabah) {
        List<TarikSaldo> riwayat = new ArrayList<>();
        return riwayat;
    }
    
    public String getAlamat() {
        return alamat;
    }
    
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public String getTelepon() {
        return telepon;
    }
    
    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}

// Petugas class
class Petugas extends User {
    
    public Petugas(int id, String nama, String username, String password) {
        super(id, nama, username, password);
    }
    
    public boolean inputSetoran(String idNasabah, double berat) {
        try {
            System.out.println("✅ Setoran berhasil dicatat untuk nasabah: " + idNasabah + 
                             " dengan berat: " + berat + " kg");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error saat mencatat setoran: " + e.getMessage());
            return false;
        }
    }
    
    public List<Setoran> getRiwayatSetoran(int idNasabah) {
        List<Setoran> riwayat = new ArrayList<>();
        return riwayat;
    }
}

// Mitra class
class Mitra extends User {
    
    public Mitra(int id, String nama, String username, String password) {
        super(id, nama, username, password);
    }
    
    public SummarySampah getSummarySampah() {
        SummarySampah summary = new SummarySampah(0.0);
        return summary;
    }
}

// Admin class
class Admin extends User {
    
    public Admin(int id, String nama, String username, String password) {
        super(id, nama, username, password);
    }
    
    public boolean inputHargaSampah(String jenisSampah, double harga) {
        try {
            HargaSampah hargaBaru = new HargaSampah(this.id, harga, new Date(), null, true);
            System.out.println("✅ Harga sampah " + jenisSampah + " berhasil diupdate: Rp " + String.format("%.0f", harga));
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error saat mengupdate harga: " + e.getMessage());
            return false;
        }
    }
    
    public Laporan generateLaporan(Date periode) {
        Laporan laporan = new Laporan(this.id, periode, new Date());
        return laporan;
    }
    
    public void kelolaSummarySampah() {
        System.out.println("📊 Mengelola summary sampah...");
    }
    
    public boolean tambahUser(String role, Object data) {
        try {
            System.out.println("✅ User baru dengan role " + role + " berhasil ditambahkan");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error saat menambah user: " + e.getMessage());
            return false;
        }
    }
    
    public List<User> lihatUser(String role) {
        List<User> users = new ArrayList<>();
        return users;
    }
    
    public boolean editUser(int id, Object data) {
        try {
            System.out.println("✅ User dengan ID " + id + " berhasil diupdate");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error saat mengedit user: " + e.getMessage());
            return false;
        }
    }
    
    public boolean ubahStatusUser(int id, String status) {
        try {
            System.out.println("✅ Status user dengan ID " + id + " berhasil diubah ke " + status);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error saat mengubah status user: " + e.getMessage());
            return false;
        }
    }
}

// Setoran class
class Setoran {
    private int idSetoran;
    private int idNasabah;
    private int idPetugas;
    private Date tanggalSetoran;
    private String jenisSampah;
    private double beratSampah;
    private double nilaiSetoran;
    
    public Setoran(int idNasabah, int idPetugas, String jenisSampah, double beratSampah) {
        this.idSetoran = generateIdSetoran();
        this.idNasabah = idNasabah;
        this.idPetugas = idPetugas;
        this.tanggalSetoran = new Date();
        this.jenisSampah = jenisSampah;
        this.beratSampah = beratSampah;
        this.nilaiSetoran = hitungNilaiSetoran();
        updateSaldoNasabah();
    }
    
    public double hitungNilaiSetoran() {
        double hargaPerKg = 5000.0;
        return beratSampah * hargaPerKg;
    }
    
    public String getDetailSetoran() {
        return String.format("ID: %d, Nasabah: %d, Tanggal: %s, Jenis: %s, Berat: %.2f kg, Nilai: Rp %.0f",
                           idSetoran, idNasabah, tanggalSetoran.toString(), jenisSampah, beratSampah, nilaiSetoran);
    }
    
    private int generateIdSetoran() {
        return (int) (Math.random() * 100000);
    }
    
    private void updateSaldoNasabah() {
        System.out.println("💰 Saldo nasabah " + idNasabah + " bertambah Rp " + String.format("%.0f", nilaiSetoran));
    }
    
    // Getters
    public int getIdSetoran() { return idSetoran; }
    public int getIdNasabah() { return idNasabah; }
    public int getIdPetugas() { return idPetugas; }
    public Date getTanggalSetoran() { return tanggalSetoran; }
    public String getJenisSampah() { return jenisSampah; }
    public double getBeratSampah() { return beratSampah; }
    public double getNilaiSetoran() { return nilaiSetoran; }
}

// HargaSampah class
class HargaSampah {
    private int idAdmin;
    private int idHarga;
    private double hargaPerKg;
    private Date tanggalBerlaku;
    private Date tanggalBerakhir;
    private boolean statusAktif;
    
    public HargaSampah(int idAdmin, double hargaPerKg, Date tanggalBerlaku, 
                       Date tanggalBerakhir, boolean statusAktif) {
        this.idHarga = generateIdHarga();
        this.idAdmin = idAdmin;
        this.hargaPerKg = hargaPerKg;
        this.tanggalBerlaku = tanggalBerlaku;
        this.tanggalBerakhir = tanggalBerakhir;
        this.statusAktif = statusAktif;
    }
    
    public double getHargaBerlaku(Date tanggal) {
        if (isHargaValid(tanggal)) {
            return hargaPerKg;
        }
        return 0.0;
    }
    
    public boolean isHargaValid(Date tanggal) {
        if (!statusAktif) return false;
        
        boolean afterStart = tanggal.compareTo(tanggalBerlaku) >= 0;
        boolean beforeEnd = tanggalBerakhir == null || tanggal.compareTo(tanggalBerakhir) <= 0;
        
        return afterStart && beforeEnd;
    }
    
    public void updateHarga(double hargaBaru, int idAdmin) {
        this.hargaPerKg = hargaBaru;
        this.idAdmin = idAdmin;
        System.out.println("✅ Harga berhasil diupdate menjadi Rp " + String.format("%.0f", hargaBaru) + " per kg");
    }
    
    private int generateIdHarga() {
        return (int) (Math.random() * 100000);
    }
    
    // Getters
    public int getIdHarga() { return idHarga; }
    public int getIdAdmin() { return idAdmin; }
    public double getHargaPerKg() { return hargaPerKg; }
    public Date getTanggalBerlaku() { return tanggalBerlaku; }
    public Date getTanggalBerakhir() { return tanggalBerakhir; }
    public boolean isStatusAktif() { return statusAktif; }
    public void setStatusAktif(boolean statusAktif) { this.statusAktif = statusAktif; }
}

// TarikSaldo class
class TarikSaldo {
    public enum StatusPenarikan {
        MENUNGGU, BERHASIL, GAGAL
    }
    
    private int idPenarikan;
    private int idAdmin;
    private int idNasabah;
    private Date tanggalPengajuan;
    private double jumlahPenarikan;
    private Date tanggalVerifikasi;
    private StatusPenarikan status;
    
    public TarikSaldo(int idNasabah, double jumlahPenarikan) {
        this.idPenarikan = generateIdPenarikan();
        this.idNasabah = idNasabah;
        this.jumlahPenarikan = jumlahPenarikan;
        this.tanggalPengajuan = new Date();
        this.status = StatusPenarikan.MENUNGGU;
    }
    
    public boolean ajukanTarikSaldo(int idNasabah, double jumlah) {
        try {
            System.out.println("✅ Pengajuan penarikan sebesar Rp " + String.format("%.0f", jumlah) + " berhasil diajukan");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error saat mengajukan penarikan: " + e.getMessage());
            return false;
        }
    }
    
    public String getStatusPenarikan() {
        return status.toString();
    }
    
    public void updateStatus(int idAdmin) {
        this.idAdmin = idAdmin;
        this.tanggalVerifikasi = new Date();
        System.out.println("✅ Status penarikan diupdate oleh admin " + idAdmin);
    }
    
    public StrukPenarikan getStruk(int idPenarikan) {
        if (this.status == StatusPenarikan.BERHASIL) {
            return new StrukPenarikan(idPenarikan);
        }
        return null;
    }
    
    private int generateIdPenarikan() {
        return (int) (Math.random() * 100000);
    }
    
    // Getters
    public int getIdPenarikan() { return idPenarikan; }
    public int getIdAdmin() { return idAdmin; }
    public int getIdNasabah() { return idNasabah; }
    public Date getTanggalPengajuan() { return tanggalPengajuan; }
    public double getJumlahPenarikan() { return jumlahPenarikan; }
    public Date getTanggalVerifikasi() { return tanggalVerifikasi; }
    public StatusPenarikan getStatus() { return status; }
    public void setStatus(StatusPenarikan status) { this.status = status; }
}

// SummarySampah class
class SummarySampah {
    private double totalBerat;
    private Date lastUpdate;
    
    public SummarySampah(double totalBerat) {
        this.totalBerat = totalBerat;
        this.lastUpdate = new Date();
    }
    
    public void updateStok(double beratTambahan) {
        if (validateStokUpdate()) {
            this.totalBerat += beratTambahan;
            this.lastUpdate = new Date();
            System.out.println("📦 Stok berhasil diupdate. Total berat: " + String.format("%.1f", totalBerat) + " kg");
        }
    }
    
    public boolean kurangiStok(double beratDikurangi) {
        if (beratDikurangi <= totalBerat && beratDikurangi > 0) {
            this.totalBerat -= beratDikurangi;
            this.lastUpdate = new Date();
            System.out.println("📦 Stok berkurang " + String.format("%.1f", beratDikurangi) + " kg. Sisa: " + String.format("%.1f", totalBerat) + " kg");
            return true;
        }
        return false;
    }
    
    public double getStokTersedia() {
        return totalBerat;
    }
    
    public String getStatusStok() {
        return hitungStatusStok();
    }
    
    private boolean validateStokUpdate() {
        return true;
    }
    
    private String hitungStatusStok() {
        if (totalBerat > 1000) {
            return "Stok Tinggi";
        } else if (totalBerat > 500) {
            return "Stok Sedang";
        } else if (totalBerat > 100) {
            return "Stok Rendah";
        } else {
            return "Stok Kritis";
        }
    }
    
    // Getters
    public double getTotalBerat() { return totalBerat; }
    public Date getLastUpdate() { return lastUpdate; }
}

// StrukPenarikan class
class StrukPenarikan {
    private int idStruk;
    private int idPenarikan;
    private Date tanggal;
    private String fileUrl;
    private String format;
    
    public StrukPenarikan(int idPenarikan) {
        this.idStruk = generateIdStruk();
        this.idPenarikan = idPenarikan;
        this.tanggal = new Date();
        this.format = "PDF";
        this.fileUrl = generateFileUrl();
    }
    
    public static StrukPenarikan generate(int idPenarikan) {
        StrukPenarikan struk = new StrukPenarikan(idPenarikan);
        System.out.println("📄 Struk penarikan berhasil dibuat untuk ID: " + idPenarikan);
        return struk;
    }
    
    public String getData() {
        return String.format("Struk ID: %d, Penarikan ID: %d, Tanggal: %s, Format: %s",
                           idStruk, idPenarikan, tanggal.toString(), format);
    }
    
    public File download() {
        System.out.println("⬇️ Mengunduh struk dari: " + fileUrl);
        return new File(fileUrl);
    }
    
    private int generateIdStruk() {
        return (int) (Math.random() * 100000);
    }
    
    private String generateFileUrl() {
        return "/files/struk_" + idStruk + "." + format.toLowerCase();
    }
    
    // Getters
    public int getIdStruk() { return idStruk; }
    public int getIdPenarikan() { return idPenarikan; }
    public Date getTanggal() { return tanggal; }
    public String getFileUrl() { return fileUrl; }
    public String getFormat() { return format; }
}

// Laporan class
class Laporan {
    private int idLaporan;
    private int idAdmin;
    private Date tanggalMulai;
    private Date tanggalSelesai;
    private String fileUrl;
    
    public Laporan(int idAdmin, Date tanggalMulai, Date tanggalSelesai) {
        this.idLaporan = generateIdLaporan();
        this.idAdmin = idAdmin;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.fileUrl = generateFileUrl();
    }
    
    public String getDetailLaporan() {
        return String.format("Laporan ID: %d, Admin: %d, Periode: %s - %s, File: %s",
                           idLaporan, idAdmin, tanggalMulai.toString(), 
                           tanggalSelesai.toString(), fileUrl);
    }
    
    public File download() {
        System.out.println("⬇️ Mengunduh laporan dari: " + fileUrl);
        return new File(fileUrl);
    }
    
    private int generateIdLaporan() {
        return (int) (Math.random() * 100000);
    }
    
    private String generateFileUrl() {
        return "/files/laporan_" + idLaporan + ".pdf";
    }
    
    // Getters
    public int getIdLaporan() { return idLaporan; }
    public int getIdAdmin() { return idAdmin; }
    public Date getTanggalMulai() { return tanggalMulai; }
    public Date getTanggalSelesai() { return tanggalSelesai; }
    public String getFileUrl() { return fileUrl; }
}

// Main class
public class EcoBankSystem {
    public static void main(String[] args) {
        System.out.println("🌱 ===============================================");
        System.out.println("🌱    ECOBANK MANAGEMENT SYSTEM DEMO");
        System.out.println("🌱    Sistem Manajemen Bank Sampah Digital");
        System.out.println("🌱 ===============================================\n");
        
        // Create users
        System.out.println("👥 === MEMBUAT PENGGUNA SISTEM ===");
        Admin admin = new Admin(1, "Admin Utama", "admin", "admin123");
        System.out.println("🔧 Admin created: " + admin.getNama());
        
        Nasabah nasabah = new Nasabah(1, "John Doe", "john", "pass123", 
                                     "Jl. Merdeka No. 1", "08123456789");
        System.out.println("👤 Nasabah created: " + nasabah.getNama());
        
        Petugas petugas = new Petugas(1, "Jane Smith", "jane", "pass456");
        System.out.println("👷 Petugas created: " + petugas.getNama());
        
        Mitra mitra = new Mitra(1, "PT Daur Ulang", "mitra", "pass789");
        System.out.println("🤝 Mitra created: " + mitra.getNama());
        
        // Demo login
        System.out.println("\n🔐 === DEMO LOGIN SISTEM ===");
        boolean loginResult = nasabah.login("john", "pass123");
        System.out.println("🔑 Login nasabah: " + (loginResult ? "✅ Berhasil" : "❌ Gagal"));
        
        boolean wrongLogin = nasabah.login("john", "wrongpass");
        System.out.println("🔑 Login dengan password salah: " + (wrongLogin ? "✅ Berhasil" : "❌ Gagal"));
        
        // Demo harga sampah
        System.out.println("\n💰 === DEMO PENGATURAN HARGA SAMPAH ===");
        admin.inputHargaSampah("Plastik", 3000.0);
        admin.inputHargaSampah("Kertas", 2500.0);
        admin.inputHargaSampah("Logam", 8000.0);
        
        // Demo setoran
        System.out.println("\n📦 === DEMO SETORAN SAMPAH ===");
        nasabah.setSaldo(0.0);
        System.out.println("💰 Saldo awal nasabah: Rp " + String.format("%.0f", nasabah.getSaldo()));
        
        // Simulasi setoran
        Setoran setoran1 = new Setoran(nasabah.getID(), petugas.getID(), "Plastik", 2.5);
        petugas.inputSetoran(String.valueOf(nasabah.getID()), 2.5);
        nasabah.setSaldo(nasabah.getSaldo() + setoran1.getNilaiSetoran());
        
        Setoran setoran2 = new Setoran(nasabah.getID(), petugas.getID(), "Kertas", 3.0);
        petugas.inputSetoran(String.valueOf(nasabah.getID()), 3.0);
        nasabah.setSaldo(nasabah.getSaldo() + setoran2.getNilaiSetoran());
        
        System.out.println("💰 Saldo nasabah setelah setoran: Rp " + String.format("%.0f", nasabah.getSaldo()));
        
        // Demo penarikan
        System.out.println("\n💸 === DEMO PENARIKAN SALDO ===");
        double jumlahTarik = 15000.0;
        boolean tarikResult = nasabah.ajukanTarikSaldo(nasabah.getID(), jumlahTarik);
        System.out.println("💸 Pengajuan penarikan Rp " + String.format("%.0f", jumlahTarik) + ": " + 
                          (tarikResult ? "✅ Berhasil" : "❌ Gagal"));
        
        if (tarikResult) {
            TarikSaldo penarikan = new TarikSaldo(nasabah.getID(), jumlahTarik);
            penarikan.setStatus(TarikSaldo.StatusPenarikan.BERHASIL);
            penarikan.updateStatus(admin.getID());
            
            // Generate struk
            StrukPenarikan struk = StrukPenarikan.generate(penarikan.getIdPenarikan());
            System.out.println("📄 " + struk.getData());
            
            // Update saldo nasabah
            nasabah.setSaldo(nasabah.getSaldo() - jumlahTarik);
            System.out.println("💰 Saldo nasabah setelah penarikan: Rp " + String.format("%.0f", nasabah.getSaldo()));
        }
        
        // Demo penarikan gagal (saldo tidak cukup)
        System.out.println("\n💸 === DEMO PENARIKAN GAGAL (SALDO TIDAK CUKUP) ===");
        double jumlahTarikBesar = 50000.0;
        boolean tarikGagal = nasabah.ajukanTarikSaldo(nasabah.getID(), jumlahTarikBesar);
        System.out.println("💸 Pengajuan penarikan Rp " + String.format("%.0f", jumlahTarikBesar) + ": " + 
                          (tarikGagal ? "✅ Berhasil" : "❌ Gagal (Saldo tidak cukup)"));
        
        // Demo summary sampah
        System.out.println("\n📊 === DEMO SUMMARY SAMPAH ===");
        SummarySampah summary = mitra.getSummarySampah();
        summary.updateStok(150.5);
        summary.updateStok(75.3);
        System.out.println("📊 Status stok: " + summary.getStatusStok());
        System.out.println("📦 Total stok tersedia: " + String.format("%.1f", summary.getStokTersedia()) + " kg");
        
        // Demo pengurangan stok
        boolean kurangiStok = summary.kurangiStok(50.0);
        System.out.println("📦 Pengurangan stok: " + (kurangiStok ? "✅ Berhasil" : "❌ Gagal"));
        
        // Demo laporan
        System.out.println("\n📋 === DEMO GENERATE LAPORAN ===");
        Laporan laporan = admin.generateLaporan(new Date());
        System.out.println("📋 " + laporan.getDetailLaporan());
        
        // Demo manajemen user
        System.out.println("\n👥 === DEMO MANAJEMEN USER ===");
        admin.tambahUser("Nasabah", "Data nasabah baru");
        admin.tambahUser("Petugas", "Data petugas baru");
        admin.editUser(2, "Data yang diupdate");
        admin.ubahStatusUser(2, "Aktif");
        
        // Demo kelola summary
        System.out.println("\n📊 === DEMO KELOLA SUMMARY ===");
        admin.kelolaSummarySampah();
        
        // Summary sistem
        System.out.println("\n🌱 ===============================================");
        System.out.println("🌱    RINGKASAN SISTEM");
        System.out.println("🌱 ===============================================");
        System.out.println("👤 Total Nasabah: 1 orang");
        System.out.println("👷 Total Petugas: 1 orang");
        System.out.println("🤝 Total Mitra: 1 orang");
        System.out.println("🔧 Total Admin: 1 orang");
        System.out.println("📦 Total Setoran: 2 transaksi");
        System.out.println("💸 Total Penarikan: 1 transaksi");
        System.out.println("📊 Total Stok Sampah: " + String.format("%.1f", summary.getStokTersedia()) + " kg");
        System.out.println("💰 Saldo Nasabah Saat Ini: Rp " + String.format("%.0f", nasabah.getSaldo()));
        
        System.out.println("\n🌱 ===============================================");
        System.out.println("🌱    DEMO SELESAI - SISTEM BERJALAN NORMAL");
        System.out.println("🌱 ===============================================");
    }
}
