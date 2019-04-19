package mybaidu.admin.example.com.imoocrestaurant.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RealgodJJ
 * on 2019/4/17
 */

public class Order implements Serializable {
    public static class ProductVo implements Serializable {
        public Product product;
        public int count;
    }

    private int id;
    private Date date;
    private Restaurant restaurant;
    private int count;
    private float price;
    private List<Product> productList = new ArrayList<>();
    public List<ProductVo> productVos = new ArrayList<>();
    public Map<Product, Integer> productQuantityMap = new HashMap<>();

    public void addProduct(Product product) {
        Integer count = productQuantityMap.get(product);
        if (count == null || count == 0) {
            productQuantityMap.put(product, 1);
        } else {
            productQuantityMap.put(product, count + 1);
        }
    }

    public void subProduct(Product product) {
        Integer count = productQuantityMap.get(product);
        if (count == null || count == 0) {
            return;
        }
        productQuantityMap.put(product, count - 1);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    //    public List<ProductVo> getProductVos() {
//        return productVos;
//    }

//    public void setProductVos(List<ProductVo> productVos) {
//        this.productVos = productVos;
//    }

//    public Map<Product, Integer> getProductQuantityMap() {
//        return productQuantityMap;
//    }
//
//    public void setProductQuantityMap(Map<Product, Integer> productQuantityMap) {
//        this.productQuantityMap = productQuantityMap;
//    }
}
