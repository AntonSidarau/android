package com.example.asus.calculator.util;

import com.example.asus.calculator.model.Model;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.persistent.Product;

import java.util.ArrayList;
import java.util.List;

public class ModelUtil {
    public static List<Model> copyLists(List<Product> list) {
        if (list != null) {
            List<Model> modelList = new ArrayList<>(list.size());
            for (Product product : list) {
                modelList.add(new ProductModel(product));
            }

            return modelList;
        }
        return null;
    }

    public static <T extends Model> List<Model> convertList(List<T> list) {
        if (list != null) {
            List<Model> modelList = new ArrayList<>(list.size());
            for (T item : list) {
                modelList.add(item);
            }
            return modelList;
        }
        return null;
    }

    public static List<ProductModel> copyList(List<Product> list) {
        if (list != null) {
            List<ProductModel> modelList = new ArrayList<>(list.size());
            for (Product product : list) {
                modelList.add(new ProductModel(product));
            }

            return modelList;
        }
        return null;
    }

    public static List<ProductModel> getComparedList(List<ProductModel> old, List<ProductModel> fresh) {
        for (int i = 0; i < fresh.size(); i++) {
            boolean isEquals = false;
            for (int j = 0; j < old.size(); j++) {
                if (old.get(j).getId() == fresh.get(i).getId()) {
                    isEquals = true;
                }
            }

            if (!isEquals) {
                old.add(fresh.get(i));
            }
        }
        return old;
    }

    public static boolean clearDuplicates(List<ProductModel> src, ProductModel obj) {
        for (int i = 0; i < src.size(); i++) {
            if (src.contains(obj)) {
                src.remove(obj);
                return true;
            }
        }

        return false;
    }
}
