package org.loose.fis.cja.model;

import java.util.Objects;

public class ProductType {
    private String type;
    private int price;

    public ProductType(String type, int price ) {
        this.type = type;
        this.price=price;
    }

    public ProductType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price=price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType productType = (ProductType) o;
        if(type != null ? !type.equals(productType.type) : productType.type != null) return false;
        return price==0 ? price== productType.price : productType.price==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, price);
    }

    public String toString(){
        return type;
    }
}
