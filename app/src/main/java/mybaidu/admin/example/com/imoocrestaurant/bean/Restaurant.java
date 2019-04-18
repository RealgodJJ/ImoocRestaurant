package mybaidu.admin.example.com.imoocrestaurant.bean;

import java.io.Serializable;

/**
 * Created by RealgodJJ
 * on 2019/4/17
 */

public class Restaurant implements Serializable {
    private int id;
    private String icon;
    private String name;
    private float price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
