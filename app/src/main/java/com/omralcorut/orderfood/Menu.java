package com.omralcorut.orderfood;

/**
 * Created by omral on 23.12.2016.
 */

//Menu titles
public class Menu {

    private String baslik;
    private int image;
    private int content;

    public Menu(String baslik, int image, int content) {
        this.image = image;
        this.baslik = baslik;
        this.content = content;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "baslik='" + baslik + '\'' +
                ", image=" + image +
                '}';
    }
}
