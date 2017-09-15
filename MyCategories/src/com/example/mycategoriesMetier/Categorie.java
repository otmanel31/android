package com.example.mycategoriesMetier;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Categorie implements Serializable{
	
	@SerializedName("id")
	  private Long idCategorie;
	@SerializedName("nom")
	  private String nomCategorie;
	@SerializedName("description")
	  private String description;
	@SerializedName("photo")
	  private String photo;
	public Long getIdCategorie() {
		return idCategorie;
	}
	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}
	public String getNomCategorie() {
		return nomCategorie;
	}
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Categorie(Long idCategorie, String nomCategorie, String description, String photo) {
		super();
		this.idCategorie = idCategorie;
		this.nomCategorie = nomCategorie;
		this.description = description;
		this.photo = photo;
	}
	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
	  

}
