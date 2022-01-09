package com.lightlance.app.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem implements Parcelable {
    @SerializedName("id_kategori")
    private String idKategori;
    @SerializedName("image_kategori")
    private String imageKategori;
    @SerializedName("nama_kategori")
    private String namaKategori;

    protected CategoriesItem(Parcel in) {
        idKategori = in.readString();
        imageKategori = in.readString();
        namaKategori = in.readString();
    }

    public static final Creator<CategoriesItem> CREATOR = new Creator<CategoriesItem>() {
        @Override
        public CategoriesItem createFromParcel(Parcel in) {
            return new CategoriesItem(in);
        }

        @Override
        public CategoriesItem[] newArray(int size) {
            return new CategoriesItem[size];
        }
    };

    public String getIdKategori() {
        return idKategori;
    }

    public String getImageKategori() {
        return imageKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idKategori);
        dest.writeString(imageKategori);
        dest.writeString(namaKategori);
    }
}
