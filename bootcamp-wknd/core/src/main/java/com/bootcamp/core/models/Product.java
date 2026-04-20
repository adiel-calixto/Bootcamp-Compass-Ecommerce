package com.bootcamp.core.models;

public interface Product {
    String getSku();
    String getName();
    double getPrice();
    String getFormattedPrice();
    String getType();
    String getDescription();
    boolean isHighlight();
    String getTechStack();
    String getImageUrl();
}
