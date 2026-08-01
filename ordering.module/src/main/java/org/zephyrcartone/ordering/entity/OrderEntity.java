package org.zephyrcartone.ordering.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class OrderEntity {
    private String orderId;
    private String customerId;
    private String itemId;
    private String status;
    private String paymentMode;
    private String paymentStatus;
    private String createdBy;
    private String creationDate;
    private String updatedBy;
    private String updateDate;

    @DynamoDbPartitionKey
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", status='" + status + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
