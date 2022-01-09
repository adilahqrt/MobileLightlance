package com.lightlance.app.api.model;

import com.google.gson.annotations.SerializedName;

public class Receipt{

	@SerializedName("status_pemesanan")
	private String statusPemesanan;

	@SerializedName("id_pemesanan")
	private String idPemesanan;

	@SerializedName("tgl_pemesanan")
	private String tglPemesanan;

	@SerializedName("user")
	private User user;

	@SerializedName("paket")
	private PackagesItem paket;

	@SerializedName("alamat")
	private String alamat;

	public String getStatusPemesanan(){
		return statusPemesanan;
	}

	public String getIdPemesanan(){
		return idPemesanan;
	}

	public String getTglPemesanan(){
		return tglPemesanan;
	}

	public User getUser(){
		return user;
	}

	public PackagesItem getPaket(){
		return paket;
	}

	public String getAlamat(){
		return alamat;
	}
}