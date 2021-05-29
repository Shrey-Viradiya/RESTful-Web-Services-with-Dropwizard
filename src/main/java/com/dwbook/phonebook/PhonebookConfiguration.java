package com.dwbook.phonebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

public class PhonebookConfiguration extends Configuration {
    @JsonProperty
    @NotEmpty   // Validating YAML file message property should not be empty
    private String message;

    @JsonProperty
    @Max(10)    // Validating YAML file configuration does not exceed 10
    private int messageRepetitions;

    public String getMessage() {
        return message;
    }

    public int getMessageRepetitions() {
        return messageRepetitions;
    }
}
