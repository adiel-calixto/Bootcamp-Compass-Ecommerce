package com.bootcamp.core.models;

import java.util.List;

public interface ProductShowcase {
    String getSectionTitle();
    List<Product> getProducts();
    boolean isEmpty();
}
