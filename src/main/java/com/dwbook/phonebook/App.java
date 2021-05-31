package com.dwbook.phonebook;

import com.dwbook.phonebook.resources.ContactResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<PhonebookConfiguration>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws Exception
    {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> b) {}

    @Override
    public void run(PhonebookConfiguration configuration, Environment environment) {
        LOGGER.info("Method App#run() called");

        // Create DBI Factory and build a JDBI instance
        final DBIFactory Factory = new DBIFactory();
        final DBI jdbi = Factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        environment.jersey().register(new ContactResource(jdbi));
    }
}
