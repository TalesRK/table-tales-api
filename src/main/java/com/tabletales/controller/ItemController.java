package com.tabletales.controller;

import com.tabletales.entity.Item;
import com.tabletales.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @PostMapping
    public Item createItem(@RequestBody @Valid Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{itemId}")
    public Item updateItem(
        @PathVariable UUID itemId,
        @RequestBody Item item
    ) {
        var oldItem = itemRepository.findById(itemId).orElseThrow();
        oldItem.setName(item.getName());
        return itemRepository.save(oldItem);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable UUID itemId) {
        itemRepository.deleteById(itemId);
    }

}
