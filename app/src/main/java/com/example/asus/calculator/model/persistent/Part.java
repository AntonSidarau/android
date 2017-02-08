package com.example.asus.calculator.model.persistent;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Part extends Entity implements Serializable {
    private static final long serialVersionUID = -1985491234737752294L;

    public static final String PRODUCT_ID = "product_id";
    public static final String DISH_ID = "dish_id";
    public static final String MASS = "mass";

    @DatabaseField(canBeNull = false, foreign = true, columnName = PRODUCT_ID)
    private Product product;

    @DatabaseField(canBeNull = false, foreign = true, columnName = DISH_ID)
    private Dish dish;

    @DatabaseField(canBeNull = false, columnName = MASS)
    private long mass;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public long getMass() {
        return mass;
    }

    public void setMass(long mass) {
        this.mass = mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Part part = (Part) o;

        if (mass != part.mass) return false;
        if (!product.equals(part.product)) return false;
        return dish.equals(part.dish);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + product.hashCode();
        result = 31 * result + dish.hashCode();
        result = 31 * result + (int) (mass ^ (mass >>> 32));
        return result;
    }
}
