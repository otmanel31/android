package com.example.metier;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Categorie implements Serializable {
	@SerializedName("id")
	private int id;
	@SerializedName("nom")
	private String nom;
	@SerializedName("description")
	private String description;
	@SerializedName("photo")
	private  String photo;
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	public String getPhoto() {return photo;}
	public void setPhoto(String photo) {this.photo = photo;}
	
	public Categorie(int id, String nom, String description, String photo) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.photo = photo;
	}
	public Categorie() {
	}
	
}
