package models;

import java.util.Collection;
import java.util.SortedMap;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

public enum Shop {
	INSTANCE;

	SortedMap<Long, Item> items = new ConcurrentSkipListMap<>();
	AtomicLong seq = new AtomicLong();
	
	//@Override
	public Collection<Item> list() {
		return new ArrayList<>(items.values());
	}
	
	//@Override
	public Item create(String name, Double price) {
		Long id = seq.incrementAndGet();
		Item item = new Item(id, name, price);
		items.put(id, item);
		return item;
	}
	
	//@Override
	public Item get(Long id) {
		return items.get(id);
	}
	
	//@Override
	public synchronized Item update(Long id, String name, Double price) {
		Item item = items.get(id);
		if (item != null) {
			Item updated = new Item(id, name, price);
			items.put(id, updated);
			return updated;
		} else return null;
	}
	
	//@Override
	public Boolean delete(Long id) {
		return items.remove(id) != null;
	}
}