package org.zephyrcartone.ordering.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zephyrcartone.ordering.entity.OrderEntity;
import org.zephyrcartone.ordering.utility.Constant;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class OrderRepository {
    private final DynamoDbTable<OrderEntity> table;

    public OrderRepository() {
        Region region = Region.of(Constant.REGION_DYNAMODB_NAME);
        DynamoDbClient client = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient =
                DynamoDbEnhancedClient.builder()
                        .dynamoDbClient(client)
                        .build();

        this.table = enhancedClient.table(
                Constant.ORDERS_TABLE_NAME,
                TableSchema.fromBean(OrderEntity.class)
        );
    }

    public String saveOrder(OrderEntity order){
        try {
            table.putItem(order);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(order);
            System.out.println("Order saved successfully!");
            return jsonResponse;
        } catch (DynamoDbException e) {
            System.err.println("Failed to save order DynamoDbException: " + e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            System.err.println("Failed to save order JsonProcessingException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
