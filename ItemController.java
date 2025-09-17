package com.inventory.management.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.inventory.management.model.Item;
import com.inventory.management.repository.ItemRepository;


@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * POST /items -> Add a new item.
     * @param item The item object to be added.
     * @return The created item with its generated ID.
     */
    @PostMapping
    public ResponseEntity<Item> addItem(@Valid @RequestBody Item item) {
        // Validation handled by @Valid and annotations in Item entity
        Item savedItem = itemRepository.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    /**
     * GET /items -> Fetch a paginated and sortable list of items.
     * Supports query parameters: page, size, sortBy, direction, and category (bonus).
     * @param page The page number (default 0).
     * @param size The number of items per page (default 5).
     * @param sortBy The field to sort by (default 'id').
     * @param direction The sorting direction (ASC or DESC, default ASC).
     * @param category An optional category filter (bonus).
     * @return A paginated list of items.
     */
    @GetMapping
    public ResponseEntity<Page<Item>> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String category) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        Page<Item> items;
        if (category != null && !category.isEmpty()) {
            // Bonus: Filter by category (case-insensitive)
            items = itemRepository.findByCategoryContainingIgnoreCase(category, pageable);
        } else {
            items = itemRepository.findAll(pageable);
        }

        return ResponseEntity.ok(items);
    }

    /**
     * GET /items/{id} -> Fetch an item by its ID.
     * @param id The ID of the item to retrieve.
     * @return The item found, or 404 Not Found if not exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));
        return ResponseEntity.ok(item);
    }

    /**
     * PUT /items/{id} -> Update an existing item.
     * @param id The ID of the item to update.
     * @param itemDetails The updated item object.
     * @return The updated item.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @Valid @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));

        item.setName(itemDetails.getName());
        item.setCategory(itemDetails.getCategory());
        item.setQuantity(itemDetails.getQuantity());
        item.setPrice(itemDetails.getPrice());

        Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    /**
     * DELETE /items/{id} -> Delete an item by its ID.
     * @param id The ID of the item to delete.
     * @return A no-content response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}