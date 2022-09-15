package jrsperry.neo4jexamplessdn;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.Neo4jContainer;

import java.time.Duration;

@Slf4j
public abstract class ContainerBaseTest {
    static final Neo4jContainer NEO4J_CONTAINER;
    private static final String DOCKER_REGISTRY = "";
    private static final String DOCKER_IMAGE = "neo4j:4.4.9";



    static {
        NEO4J_CONTAINER = new Neo4jContainer(DOCKER_REGISTRY + DOCKER_IMAGE)
                .withoutAuthentication();
        NEO4J_CONTAINER.withStartupTimeout(Duration.ofMinutes(5));
        NEO4J_CONTAINER.start();
        log.info("Test Bolt URL: {}", NEO4J_CONTAINER.getBoltUrl());
        System.setProperty("spring.neo4j.uri", NEO4J_CONTAINER.getBoltUrl());

    }
}
