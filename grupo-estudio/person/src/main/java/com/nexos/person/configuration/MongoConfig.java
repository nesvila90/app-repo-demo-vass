package com.nexos.person.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.nexos.person.configuration.core.constants.DeployType;
import com.nexos.person.repository.person.PersonAuditRepository;
import com.nexos.person.repository.person.PersonRepository;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * The type Mongo config.
 */
@Log4j2
@Configuration
@EnableMongoAuditing
@EnableReactiveMongoRepositories(basePackageClasses = {PersonRepository.class, PersonAuditRepository.class}, basePackages = {"com.nexos.person.domain.*"})
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
public class MongoConfig {


    @Bean
    @Profile({DeployType.DEVELOP, DeployType.TEST, DeployType.QA, DeployType.PRODUCTION})
    public ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory() {
        final String urlMongo = urlBuilder(config().username, config().password, config().host, config().database);
        //if(log.isDebugEnabled())
        log.info("URL mongo {}", urlMongo);
        return new SimpleReactiveMongoDatabaseFactory(MongoClients.create(urlMongo), config().database);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MappingMongoConverter mappingMongoConverter) {
        return new ReactiveMongoTemplate(reactiveMongoDatabaseFactory(), mappingMongoConverter);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.data.primary.mongodb")
    public MongoClientConfiguration config() {
        return new MongoClientConfiguration();
    }

    private final String urlBuilder(final String user, final String password, final String host, final String database) {
        if (log.isDebugEnabled())
            log.info("URL PARAMS - USER{0} , PASSWORD{1} , HOST{2}", user, password, host);
        return new StringBuilder().append("mongodb://").append(user).append(":").append(password).append("@").append(host).append("/").append(database).toString();
    }

    @Data
    @ToString
    private static class MongoClientConfiguration {
        private String host;
        private Integer port;
        private String username;
        private String database;
        private String password;
    }
}

