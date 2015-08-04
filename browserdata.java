package com.example.razon30.mybrowser;

/**
 * Created by razon30 on 04-05-15.
 */
import android.R.integer;

public class browserdata {

    int id;
    String name, link, sign;



    public browserdata(String name, String link) {
        super();
        this.name = name;
        this.link = link;
    }

    public browserdata(int id, String name, String link, String sign) {
        super();
        this.id = id;
        this.name = name;
        this.link = link;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
