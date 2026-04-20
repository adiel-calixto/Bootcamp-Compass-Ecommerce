package com.bootcamp.core.models.impl;

import com.bootcamp.core.models.Product;
import com.bootcamp.core.models.ProductShowcase;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = ProductShowcase.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ProductShowcaseImpl implements ProductShowcase {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ValueMapValue
    private String apiUrl;

    @ValueMapValue
    private String sectionTitle;

    @ValueMapValue
    private boolean showHighlightsOnly;

    private final List<Product> products = new ArrayList<>();
    private boolean empty = true;

    @PostConstruct
    protected void init() {
        if (apiUrl == null || apiUrl.isEmpty()) {
            logger.warn("API URL not configured for ProductShowcase");
            return;
        }

        try {
            String jsonResponse = fetchFromApi(apiUrl);
            parseProducts(jsonResponse);
        } catch (Exception e) {
            logger.error("Error fetching products from Commerce API: {}", e.getMessage(), e);
        }
    }

    private String fetchFromApi(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    private void parseProducts(String json) {
        org.json.JSONArray productArray;

        productArray = new org.json.JSONArray(json);

        for (int i = 0; i < productArray.length(); i++) {
            org.json.JSONObject p = productArray.getJSONObject(i);
            final String sku = p.optString("sku", "");
            final String name = p.optString("name", "");
            final double price = parsePrice(p.opt("price"));
            final String formattedPrice = formatPrice(price);
            final String type = p.optString("type", "");
            final String description = p.optString("description", "");
            final boolean highlight = p.optBoolean("bootcamp_highlight", false);
            final String techStack = p.optString("tech_stack", "");
            final String imageUrl = p.optString("image_url", "");

            if (showHighlightsOnly && !highlight) {
                continue;
            }

            products.add(new Product() {
                @Override
                public String getSku() {
                    return sku;
                }

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public double getPrice() {
                    return price;
                }

                @Override
                public String getFormattedPrice() {
                    return formattedPrice;
                }

                @Override
                public String getType() {
                    return type;
                }

                @Override
                public String getDescription() {
                    return description;
                }

                @Override
                public boolean isHighlight() {
                    return highlight;
                }

                @Override
                public String getTechStack() {
                    return techStack;
                }

                @Override
                public String getImageUrl() {
                    return imageUrl;
                }
            });
        }
        empty = products.isEmpty();
    }

    private double parsePrice(Object price) {
        if (price == null || price.toString().isEmpty()) {
            return 0.0;
        }
        if (price instanceof Number) {
            return ((Number) price).doubleValue();
        }
        try {
            String str = price.toString().replace(",", ".");
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String formatPrice(double price) {
        return String.format("$%.2f", price);
    }

    @Override
    public String getSectionTitle() {
        return sectionTitle;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }
}
