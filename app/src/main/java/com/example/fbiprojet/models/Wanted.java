package com.example.fbiprojet.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe qui représente une personne recherchée
 */
public class Wanted implements Serializable {
    public String uid;
    public String title;
    public String description;
    public ImagesWanted images;
    public String warning;
    public String nationalite;
    public ArrayList<String> categories;
    public String remarques;


    public Wanted() {
        super();
        categories = new ArrayList<String>();
    }
//
//    public Wanted(String uid, String title, String description, ImagesWanted images, String warning) {
//        this.uid = uid;
//        this.title = title;
//        this.description = description;
//        this.images = images;
//        this.warning = warning;
//    }

    public Wanted(String test) {
        this.uid = test;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImagesWanted getImages() {
        return images;
    }

    public void setImages(ImagesWanted images) {
        this.images = images;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public void addCategorie(String c) {
        this.categories.add(c);
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public String getNationalite() {
        return nationalite;
    }

    /**
     * Fonction qui retourne les catégories selon l'API de la fiche Most Wanted
     * (exemple: contre espionnage, braquage, meurtre, disparition, ...)
     * @return
     */
    public String getCategories() {
        String res = "";
        for(String s : categories) {
            res += s + " | ";
        }
        return res;
    }

    public String getRemarques() {
        return remarques;
    }
}
