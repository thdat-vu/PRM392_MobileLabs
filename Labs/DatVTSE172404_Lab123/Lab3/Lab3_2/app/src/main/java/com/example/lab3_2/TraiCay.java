package com.example.lab3_2;

public class TraiCay {
    private String Ten;
    private String Mota;
    private String Hinh;

    public TraiCay(String ten, String mota, String hinh) {
        Ten = ten;
        Mota = mota;
        Hinh = hinh;
    }

    public String getHinh() {
        return Hinh;
    }
    public String getTen() {
        return Ten;
    }
    public String getMota() {
        return Mota;
    }
    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public void setMota(String mota) {
        Mota = mota;
    }
    public void setTen(String ten) {
        Ten = ten;
    }
}
