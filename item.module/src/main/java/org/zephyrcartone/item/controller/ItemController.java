package org.zephyrcartone.item.controller;

import org.zephyrcartone.item.entity.ItemEntity;
import org.zephyrcartone.item.service.impl.ItemServiceImpl;

public class ItemController {
    ItemServiceImpl service = new ItemServiceImpl();

    public String createItem(ItemEntity item){
        return service.saveItemData(item);
    }

    public String getItem(ItemEntity item){
        return service.getItemData(item.getItemId());
    }
}
