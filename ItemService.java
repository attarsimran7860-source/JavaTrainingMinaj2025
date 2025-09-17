package com.inventory.management.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.management.exception.ResourceNotFoundException;
import com.inventory.management.model.Item;
import com.inventory.management.repository.ItemRepository;

import java.util.Optional;

@Service
public class ItemService {

	private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Page<Item> getAllItems(int page, int size, String sortBy, String direction, String category) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (category != null && !category.trim().isEmpty()) {
            return itemRepository.findByCategoryContainingIgnoreCase(category, pageable);
        } else {
            return itemRepository.findAll(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id " + id));
    }
}