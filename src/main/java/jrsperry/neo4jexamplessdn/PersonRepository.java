package jrsperry.neo4jexamplessdn;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findFirstByAge(Integer age);

    Person findFirstByNameAndAge(String name, Integer age);

}
