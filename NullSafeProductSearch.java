package com.day5.lab2;

import java.util.*;

public class NullSafeProductSearch {

	 public static void main(String[] args) {
	        
	        List<Product> catalog = Arrays.asList(
	                new Product("Laptop", "Electronics", 75000.0),
	                new Product("Shirt", "Fashion", 1500.0),
	                new Product("Book", null, 500.0), // Category is null
	                new Product("Smartphone", "Electronics", null), // Price is null
	                new Product("Coffee Maker", "Home Appliances", 3000.0),
	                new Product(null, "Food", 100.0), // Name is null
	                new Product("Desk", "FURNITURE", 12000.0),
	                new Product("Headphones", "Electronics", 750.0),
	                null 
	        );

	        SearchProduct searchService = new SearchProduct();

	        System.out.println("--- findFirstByCategory ---");
	        // Test 1: Find first product in "Electronics"
	        Optional<Product> electronicsProduct = searchService.findFirstByCategory(catalog, "Electronics");
	        electronicsProduct.ifPresentOrElse(
	                product -> System.out.println("First Electronics product: " + product),
	                () -> System.out.println("No Electronics product found.")
	        ); // Expected: Laptop

	        // Test 2: Find first product in "Fashion"
	        Optional<Product> fashionProduct = searchService.findFirstByCategory(catalog, "Fashion");
	        fashionProduct.ifPresentOrElse(
	                product -> System.out.println("First Fashion product: " + product),
	                () -> System.out.println("No Fashion product found.")
	        ); // Expected: Shirt

	        // Test 3: Find first product in "Books" (category is null for one product, should not match)
	        Optional<Product> booksProduct = searchService.findFirstByCategory(catalog, "Books");
	        booksProduct.ifPresentOrElse(
	                product -> System.out.println("First Books product: " + product),
	                () -> System.out.println("No Books product found.")
	        ); // Expected: No Books product found.

	        // Test 4: Find first product in a non-existent category
	        Optional<Product> toysProduct = searchService.findFirstByCategory(catalog, "Toys");
	        toysProduct.ifPresentOrElse(
	                product -> System.out.println("First Toys product: " + product),
	                () -> System.out.println("No Toys product found.")
	        ); // Expected: No Toys product found.

	        // Test 5: Search with null category
	        Optional<Product> nullCategorySearch = searchService.findFirstByCategory(catalog, null);
	        nullCategorySearch.ifPresentOrElse(
	                product -> System.out.println("First product with null category search: " + product),
	                () -> System.out.println("Search with null category returned empty Optional (correct).")
	        ); // Expected: Search with null category returned empty Optional (correct).

	        System.out.println("\n--- findByPriceRange ---");
	        // Test 1: Products between 1000 and 10000
	        List<Product> midRangeProducts = searchService.findByPriceRange(catalog, 1000.0, 10000.0);
	        System.out.println("Products between 1000 and 10000: " + midRangeProducts);
	        // Expected: [Shirt, Coffee Maker, Headphones (Smartphone is skipped due to null price)]

	        // Test 2: Products between 0 and 600
	        List<Product> lowRangeProducts = searchService.findByPriceRange(catalog, 0.0, 600.0);
	        System.out.println("Products between 0 and 600: " + lowRangeProducts);
	        // Expected: [Book]

	        // Test 3: Products with no match
	        List<Product> highRangeProducts = searchService.findByPriceRange(catalog, 100000.0, 200000.0);
	        System.out.println("Products between 100000 and 200000: " + highRangeProducts);
	        // Expected: []

	        System.out.println("\n--- findProductNames ---");
	        // Test 1: Get all product names
	        List<String> productNames = searchService.findProductNames(catalog);
	        System.out.println("All product names: " + productNames);
	        // Expected: [Laptop, Shirt, Book, Smartphone, Coffee Maker, Desk, Headphones] (null name is skipped)
	    }
	}