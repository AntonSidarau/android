package com.example.asus.calculator.model;

import com.example.asus.calculator.model.persistent.Product;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private static final long serialVersionUID = 8917108211861359530L;

    private Product product = new Product();
    private boolean isChecked;
    private int mass = 100;

    public ProductModel() {
    }

    public ProductModel(Product product) {
        this.product.setId(product.getId());
        this.product.setName(product.getName());
        this.product.setCalories(product.getCalories());
        this.product.setCategory(product.getCategory());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getId() {
        return product.getId();
    }

    public void setId(long id) {
        product.setId(id);
    }

    public String getName() {
        return product.getName();
    }

    public void setName(String name) {
        product.setName(name);
    }

    public long getCalories() {
        return product.getCalories();
    }

    public void setCalories(long calories) {
        product.setCalories(calories);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductModel model = (ProductModel) o;

        return product.equals(model.product);

    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }
}
