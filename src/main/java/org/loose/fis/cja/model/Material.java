package org.loose.fis.cja.model;

import java.util.Objects;

public class Material {
    private String name;
    private int price;


    public Material(String name, int price ) {
        this.name = name;
        this.price=price;
    }

    public Material() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Material material = (Material) o;
        if(name != null ? !name.equals(material.name) : material.name != null) return false;
        return price==0 ? price== material.price : material.price==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return name;
    }
}

