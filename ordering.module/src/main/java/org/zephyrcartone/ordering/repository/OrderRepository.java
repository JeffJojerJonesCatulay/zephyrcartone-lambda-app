package org.zephyrcartone.ordering.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zephyrcartone.ordering.entity.OrderEntity;
import org.zephyrcartone.ordering.utility.Constant;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
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
            PutItemEnhancedRequest<OrderEntity> putRequest =
                    PutItemEnhancedRequest.builder(OrderEntity.class)
                            .item(order)
                            .conditionExpression(Expression.builder()
                                    .expression("attribute_not_exists(orderId)")
                                    .build())
                            .build();

            table.putItem(order);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Order saved successfully!");
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
        } catch (DynamoDbException e) {
            System.err.println("Failed to save order DynamoDbException: " + e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            System.err.println("Failed to save order JsonProcessingException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getOrder(String orderId) {
        try {
            OrderEntity order = table.getItem(r -> r.key(k -> k.partitionValue(orderId)));
            ObjectMapper mapper = new ObjectMapper();

            if (order == null){
                throw new RuntimeException("NO RECORD FOUND");
            } else {
                System.out.println("order retrieved successfully!");
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
            }
        } catch (DynamoDbException e) {
            System.err.println("Failed to retrieve order DynamoDbException: " + e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            System.err.println("Failed to retrieve order JsonProcessingException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
