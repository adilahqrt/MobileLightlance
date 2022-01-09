package com.lightlance.app.api.responses;

import com.google.gson.annotations.SerializedName;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.model.PackagesItem;

public class OrdersItem {

    @SerializedName("status_pemesanan")
    private String statusPemesanan;

    @SerializedName("id_pemesanan")
    private String idPemesanan;

    @SerializedName("package")
    private PackagesItem packagesItem;

    @SerializedName("tgl_pemesanan")
    private String tglPemesanan;

    @SerializedName("category")
    private CategoriesItem category;

    @SerializedName("alamat")
    private String alamat;

    public String getStatusPemesanan() {
        return statusPemesanan;
    }

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public PackagesItem getPackagesItem() {
        return packagesItem;
    }

    public String getTglPemesanan() {
        return tglPemesanan;
    }

    public CategoriesItem getCategory() {
        return category;
    }

    public String getAlamat() {
        return alamat;
    }
}