package gitp.problembank.config;

import gitp.problembank.converter.Neo4jYearConverter;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;

import java.util.Collections;
import java.util.Set;

@Configuration
public class Neo4jConfig {

    @Bean
    org.neo4j.cypherdsl.core.renderer.Configuration cypherdslConfiguration() {
        return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig()
                .withDialect(Dialect.NEO4J_5)
                .build();
    }

    @Bean
    public Neo4jConversions neo4jConversions() {
        Set<GenericConverter> converterSet = Collections.singleton(new Neo4jYearConverter());
        return new Neo4jConversions(converterSet);
    }
}
