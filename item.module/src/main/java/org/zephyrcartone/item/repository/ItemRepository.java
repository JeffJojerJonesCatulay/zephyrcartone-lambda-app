package org.zephyrcartone.item.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zephyrcartone.item.entity.ItemEntity;
import org.zephyrcartone.item.utility.Constant;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class ItemRepository {

    private final DynamoDbTable<ItemEntity> table;

    public ItemRepository() {
        Region region = Region.of(Constant.REGION_DYNAMODB_NAME);
        DynamoDbClient client = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();

        this.table = enhancedClient.table(
                Constant.ITEMS_TABLE_NAME,
                TableSchema.fromBean(ItemEntity.class));
    }

    public String saveItem(ItemEntity item) {
        try {
            PutItemEnhancedRequest<ItemEntity> putRequest = PutItemEnhancedRequest.builder(ItemEntity.class)
                    .item(item)
                    .conditionExpression(Expression.builder()
                            .expression("attribute_not_exists(itemId)")
                            .build())
                    .build();

            table.putItem(putRequest);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Item saved successfully!");
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);
        } catch (DynamoDbException e) {
            System.err.println("Failed to save item DynamoDbException: " + e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            System.err.println("Failed to save item JsonProcessingException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getItem(String itemId) {
        try {
            ItemEntity item = table.getItem(r -> r.key(k -> k.partitionValue(itemId)));
            ObjectMapper mapper = new ObjectMapper();

            if (itemId == null) {
                throw new RuntimeException("NO RECORD FOUND");
            } else {
                System.out.println("Customer retrieved successfully!");
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);
            }
        } catch (DynamoDbException e) {
            System.err.println("Failed to retrieve customer DynamoDbException: " + e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            System.err.println("Failed to retrieve customer JsonProcessingException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
