package com.example.dao;

import com.example.model.Item;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemDAO {
    private static ItemDAO INSTANCE = new ItemDAO();
    private Map<Integer, Item> store = new LinkedHashMap<>();
    private AtomicInteger seq = new AtomicInteger(1);

    private ItemDAO() {
        // sample data
        save(new Item(0, "Sample 1", "First item"));
        save(new Item(0, "Sample 2", "Second item"));
    }

    public static ItemDAO getInstance() { return INSTANCE; }

    public List<Item> findAll() { return new ArrayList<>(store.values()); }

    public Item find(int id) { return store.get(id); }

    public Item save(Item item) {
        if (item.getId() == 0) {
            int id = seq.getAndIncrement();
            item.setId(id);
            store.put(id, item);
            return item;
        } else {
            store.put(item.getId(), item);
            return item;
        }
    }

    public void delete(int id) { store.remove(id); }
}