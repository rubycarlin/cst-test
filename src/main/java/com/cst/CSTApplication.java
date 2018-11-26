package com.cst;

import com.cst.module.CSTModule;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.elasticmq.NodeAddress;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;

public class CSTApplication extends Application<CSTConfiguration> {


    public static void main(String[] args) throws Exception {
        new CSTApplication().run(args);
    }

    @Override
    public String getName() {
        return "cst-application";
    }

    @Override
    public void initialize(Bootstrap<CSTConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor()
                )
        );

        GuiceBundle<CSTConfiguration> guiceBundle = GuiceBundle.<CSTConfiguration>newBuilder()
                .addModule(new CSTModule())
                .setConfigClass(CSTConfiguration.class)
                .enableAutoConfig(getClass().getPackage().getName())
                .build();
        bootstrap.addBundle(guiceBundle);

    }


    public void run(CSTConfiguration cstConfiguration, Environment environment) throws Exception {

        SQSRestServerBuilder.withPort(9324)
                .withServerAddress(new NodeAddress("http", "localhost", 9324, "")).start();

    }
}
