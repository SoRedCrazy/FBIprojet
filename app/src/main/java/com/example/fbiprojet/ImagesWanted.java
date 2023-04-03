package com.example.fbiprojet;

import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;

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

    public String getImageUrl(int index) {
        return images.get(index);
    }
}
