package org.loose.fis.cja.model;

import java.util.Objects;

public class Order {
    private long id;
    private String client;
    private ProductType productType;
    private Material material;
    private String message;
    private int price;
    private String state;

    public Order(){}

    public Order(long id, String client,ProductType productType, Material material, String message) {
        this.id = id;
        this.client=client;
        this.productType = productType;
        this.material = material;
        this.message = message;
        this.price=productType.getPrice()+material.getPrice();
        this.state="Asteptare";
    }

    public long getId(){return id;}

    public void setId(long id){ this.id = id;}

    public String getClient(){
        return client;
    }

    public void setClient(String client){
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

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state=state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(client,order.client) && price == order.price && Objects.equals(productType, order.productType) && Objects.equals(material, order.material) && Objects.equals(message, order.message) && Objects.equals(state,order.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client,productType, material, message, price,state);
    }
}