package mybaidu.admin.example.com.imoocrestaurant.vo;

import mybaidu.admin.example.com.imoocrestaurant.bean.Product;

/**
 * Created by RealgodJJ
 * on 2019/4/18
 */

public class ProductItem extends Product {
    private int count;

    public ProductItem(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.label = product.getLabel();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.icon = product.getIcon();
        this.restaurant = product.getRestaurant();
    }

    public ProductItem() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
