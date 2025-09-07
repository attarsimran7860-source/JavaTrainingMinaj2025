package com.day5.lab2;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchProduct {

	   public Optional<Product> findFirstByCategory(List<Product> products, String category) {
	        if (category == null) {
	            return Optional.empty(); 
	        }

	        return products.stream()
	                .filter(p -> p != null) // Ensure product object itself is not null
	                .filter(p -> Optional.ofNullable(p.getCategory()) // Wrap product's category in Optional
	                                     .map(c -> c.equalsIgnoreCase(category)) // Map to true if categories match (case-insensitive)
	                                     .orElse(false)) // If product's category is null, or Optional is empty, consider it false
	                .findFirst(); // Get the first matching product
	    }

	    /**
	     * Returns all products whose price falls within the given range [minPrice, maxPrice].
	     * Skips products with null prices.
	     *
	     * @param products The list of products to search within.
	     * @param minPrice The minimum price (inclusive).
	     * @param maxPrice The maximum price (inclusive).
	     * @return A list of products within the specified price range.
	     */
	    public List<Product> findByPriceRange(List<Product> products, double minPrice, double doublemaxPrice) {
	        return products.stream()
	                .filter(p -> p != null) // Ensure product object itself is not null
	                .filter(p -> Optional.ofNullable(p.getPrice()) // Wrap product's price in Optional
	                                     .filter(price -> price >= minPrice && price <= doublemaxPrice) // Filter if price is in range
	                                     .isPresent()) // Only include if price exists and is in range
	                .collect(Collectors.toList()); // Collect all matching products into a list
	    }

	    /**
	     * Returns the list of product names (ignoring products where name is null).
	     *
	     * @param products The list of products to extract names from.
	     * @return A list of non-null product names.
	     */
	    public List<String> findProductNames(List<Product> products) {
	        return products.stream()
	                .filter(p -> p != null) // Ensure product object itself is not null
	                .map(Product::getName) // Get the name of each product
	                .filter(name -> name != null) // Filter out null names
	                .collect(Collectors.toList()); // Collect remaining non-null names
	    }
	}
