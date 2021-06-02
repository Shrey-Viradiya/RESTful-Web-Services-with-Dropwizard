package com.dwbook.phonebook;

import com.dwbook.phonebook.resources.ContactResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdbi.v3.core.Jdbi;

public class App extends Application<PhonebookConfiguration>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws Exception
    {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> b) {

    }

    @Override
    public void run(PhonebookConfiguration configuration, Environment environment) {
        LOGGER.info("Method App#run() called");

        // Create DBI Factory and build a JDBI instance
        final JdbiFactory Factory = new JdbiFactory();
        final Jdbi jdbi = Factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        environment.jersey().register(new ContactResource(jdbi));
    }
}
