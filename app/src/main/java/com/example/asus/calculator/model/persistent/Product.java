package com.example.asus.calculator.model.persistent;

import com.example.asus.calculator.dao.impl.ProductDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(daoClass = ProductDaoImpl.class)
public class Product extends Entity implements Serializable {
    private static final long serialVersionUID = 2423458817687343843L;

    public static final String CATEGORY_ID = "category_id";
    public static final String NAME = "name";
    public static final String CALORIES = "calories";
    public static final String PARTS = "product";

    @DatabaseField(canBeNull = false, columnName = NAME)
    private String name;

    @DatabaseField(canBeNull = false, columnName = CALORIES)
    private long calories;

    @DatabaseField(canBeNull = false, columnName = CATEGORY_ID, foreign = true)
    private Category category;

    @ForeignCollectionField(foreignFieldName = PARTS)
    private ForeignCollection<Part> parts;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
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

        Product product = (Product) o;

        if (calories != product.calories) return false;
        if (!name.equals(product.name)) return false;
        if (!category.equals(product.category)) return false;
        return parts != null ? parts.equals(product.parts) : product.parts == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) (calories ^ (calories >>> 32));
        result = 31 * result + category.hashCode();
        result = 31 * result + (parts != null ? parts.hashCode() : 0);
        return result;
    }
}
