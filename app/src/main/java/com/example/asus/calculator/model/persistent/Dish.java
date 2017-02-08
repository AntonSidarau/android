package com.example.asus.calculator.model.persistent;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;

public class Dish extends Entity implements Serializable {
    private static final long serialVersionUID = -8274217613843665022L;

    public static final String CATEGORY_ID = "category_id";
    public static final String NAME = "name";
    public static final String PARTS = "dish";

    @DatabaseField(canBeNull = false, columnName = NAME)
    private String name;

    @DatabaseField(canBeNull = false, columnName = CATEGORY_ID, foreign = true)
    private Category category;

    @ForeignCollectionField(foreignFieldName = PARTS)
    private ForeignCollection<Part> parts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ForeignCollection<Part> getParts() {
        return parts;
    }

    public void setParts(ForeignCollection<Part> parts) {
        this.parts = parts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Dish dish = (Dish) o;

        if (!name.equals(dish.name)) return false;
        if (!category.equals(dish.category)) return false;
        return parts != null ? parts.equals(dish.parts) : dish.parts == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + (parts != null ? parts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return (NAME + ": " + getName()) +
                ", Category : " + getCategory().getName();
    }
}
