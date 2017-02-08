package com.example.asus.calculator.model.persistent;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 7844562578732631588L;

    @DatabaseField(generatedId = true, dataType = DataType.LONG, columnName = "_id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
