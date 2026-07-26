package org.zephyrcartone.customer.utility;

public class Constant {
    public static final String CUSTOMER_TABLE_NAME = System.getenv("CUSTOMER_TABLE_NAME");
    public static final String REGION_DYNAMODB_NAME = System.getenv("REGION_DYNAMODB_NAME");
    public static final String MANDATORY_FIELD_MESSAGE = "Mandatory field cannot be null";
    public static final String SPECIAL_CHAR_FIELD_MESSAGE = "Invalid character provided";
    public static final String PARTITION_KEY_PROVIDED_ERR_MESSAGE = "Field is system generated only.";
}
