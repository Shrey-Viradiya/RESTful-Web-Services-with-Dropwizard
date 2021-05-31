package com.dwbook.phonebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

public class PhonebookConfiguration extends Configuration {
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory(){
        return database;
    }
}
