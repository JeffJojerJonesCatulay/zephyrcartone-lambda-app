package org.zephyrcartone.customer.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zephyrcartone.customer.entity.CustomerEntity;
import org.zephyrcartone.customer.utility.Constant;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.Map;

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
            PutItemEnhancedRequest<CustomerEntity> putRequest =
                    PutItemEnhancedRequest.builder(CustomerEntity.class)
                            .item(customer)
                            .conditionExpression(Expression.builder()
                                    .expression("attribute_not_exists(customerId)")
                                    .build())
                            .build();

            table.putItem(putRequest);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Customer saved successfully!");
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customer);
        } catch (DynamoDbException e) {
            System.err.println("Failed to save customer DynamoDbException: " + e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            System.err.println("Failed to save customer JsonProcessingException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getCustomer(String customerId) {
        try {
            CustomerEntity customer = table.getItem(r -> r.key(k -> k.partitionValue(customerId)));
            ObjectMapper mapper = new ObjectMapper();

            if (customer == null){
                throw new RuntimeException("NO RECORD FOUND");
            } else {
                System.out.println("Customer retrieved successfully!");
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customer);
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
