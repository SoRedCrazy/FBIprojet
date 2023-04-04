package com.example.fbiprojet.models;

import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objet qui regroupe les images d'une personne recherchée
 * (à la base, la tentative était d'utiliser Gson)
 */
public class ImagesWanted implements Serializable {
    public ArrayList<String> images;
    public ImagesWanted(JsonArray jso) {
        images = new ArrayList<String>();
        for(int i=0;i<jso.size();i++) {
            String url = jso.get(i).getAsJsonObject().get("original").toString();
            StringBuilder correct = new StringBuilder(url);
            correct.deleteCharAt(0); correct.deleteCharAt(correct.length()-1);
            images.add(correct.toString());
        }
    }

    /**
     * Renvoie l'URL de l'image à l'index renseigné
     * @param index index de l'image recherchée
     * @return l'url de l'image
     */
    public String getImageUrl(int index) {
        return images.get(index);
    }
}
