package org.zephyrcartone.customer.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zephyrcartone.customer.entity.CustomerEntity;
import org.zephyrcartone.customer.utility.Constant;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class CustomerRepository {

    private final DynamoDbTable<CustomerEntity> table;

    public CustomerRepository() {
        Region region = Region.of(Constant.REGION_DYNAMODB_NAME);
        DynamoDbClient client = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient =
                DynamoDbEnhancedClient.builder()
                        .dynamoDbClient(client)
                        .build();

        this.table = enhancedClient.table(
                Constant.CUSTOMER_TABLE_NAME,
                TableSchema.fromBean(CustomerEntity.class)
        );
    }

    public String saveCustomer(CustomerEntity customer){
        try {
            table.putItem(customer);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(customer);
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
