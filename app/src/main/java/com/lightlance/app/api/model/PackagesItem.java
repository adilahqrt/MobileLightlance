package com.lightlance.app.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PackagesItem implements Parcelable {

    @SerializedName("harga_paket")
    private String hargaPaket;

    @SerializedName("id_kategori")
    private String idKategori;

    @SerializedName("id_paket")
    private String idPaket;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("nama_paket")
    private String namaPaket;

    protected PackagesItem(Parcel in) {
        hargaPaket = in.readString();
        idKategori = in.readString();
        idPaket = in.readString();
        deskripsi = in.readString();
        namaPaket = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hargaPaket);
        dest.writeString(idKategori);
        dest.writeString(idPaket);
        dest.writeString(deskripsi);
        dest.writeString(namaPaket);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PackagesItem> CREATOR = new Creator<PackagesItem>() {
        @Override
        public PackagesItem createFromParcel(Parcel in) {
            return new PackagesItem(in);
        }

        @Override
        public PackagesItem[] newArray(int size) {
            return new PackagesItem[size];
        }
    };

    public String getHargaPaket() {
        return hargaPaket;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getIdPaket() {
        return idPaket;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getNamaPaket() {
        return namaPaket;
    }
}