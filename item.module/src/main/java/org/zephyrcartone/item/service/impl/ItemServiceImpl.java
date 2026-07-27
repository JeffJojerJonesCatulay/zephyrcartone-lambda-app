package org.zephyrcartone.item.service.impl;

import org.zephyrcartone.item.entity.ItemEntity;
import org.zephyrcartone.item.repository.ItemRepository;
import org.zephyrcartone.item.service.ItemService;
import org.zephyrcartone.item.utility.Constant;
import org.zephyrcartone.item.utility.Validations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ItemServiceImpl implements ItemService {
    ItemRepository repository = new ItemRepository();

    @Override
    public String saveItemData(ItemEntity item) {
        if (item.getItemId() != null){
            throw new RuntimeException(Constant.PARTITION_KEY_PROVIDED_ERR_MESSAGE);
        }

        Validations validations = new Validations();
        // Validate mandatory fields
        validations.validateMandatoryField(item.getItemName());
        validations.validateMandatoryField(item.getItemDescription());
        validations.validateMandatoryField(item.getPrice());
        validations.validateMandatoryField(item.getStock());

        // Filter characters not allowed
        validations.validateString(item.getItemName());
        validations.validateString(item.getItemDescription());
        validations.validateString(item.getPrice());
        validations.validateString(item.getStock());

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        item.setCreationDate(dateTime.format(formatter));

        String itemId = UUID.randomUUID().toString();
        item.setItemId(itemId);

        return repository.saveItem(item);
    }

    @Override
    public String getItemData(String itemId) {
        Validations validations = new Validations();
        validations.validateString(itemId);

        return repository.getItem(itemId);
    }
}
