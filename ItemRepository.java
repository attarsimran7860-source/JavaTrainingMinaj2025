package com.inventory.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.management.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
   // Bonus: Add a search filter for category
   Page<Item> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
}