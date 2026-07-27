package org.zephyrcartone.ordering.utility;

public class Validations {

    public void validateString(String input){
        if (!input.matches("[a-zA-Z0-9+\\-.@ ]+")){
            throw new RuntimeException(Constant.SPECIAL_CHAR_FIELD_MESSAGE);
        }
    }

    public void isValidAddress(String input) {
        if (!input.matches("[a-zA-Z0-9 .,'/&-:]+")){
            throw new RuntimeException(Constant.SPECIAL_CHAR_FIELD_MESSAGE);
        }
    }

    public void validateMandatoryField(String value) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException(Constant.MANDATORY_FIELD_MESSAGE);
        }
    }
}
