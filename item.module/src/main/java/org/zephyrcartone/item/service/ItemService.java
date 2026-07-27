package org.zephyrcartone.item.service;

import org.zephyrcartone.item.entity.ItemEntity;

public interface ItemService {
    public String saveItemData(ItemEntity item);
    public String getItemData(String itemId);
}
