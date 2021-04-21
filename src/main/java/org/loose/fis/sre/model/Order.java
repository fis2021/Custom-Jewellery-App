package org.loose.fis.sre.model;

import java.util.Objects;

public class Order {
    private User client;
    private ProductType productType;
    private Material material;
    private String message;
    private int price;

    public Order(User client,ProductType productType, Material material, String message) {
        this.client=client;
        this.productType = productType;
        this.material = material;
        this.message = message;
        this.price=productType.getPrice()+material.getPrice();

    }

    public User getClient(){
        return client;
    }
    public void setClient(User client){
        this.client=client;
    }
    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(client,order.client) && price == order.price && Objects.equals(productType, order.productType) && Objects.equals(material, order.material) && Objects.equals(message, order.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client,productType, material, message, price);
    }
}