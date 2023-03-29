package jrsperry.neo4jexamplessdn;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PersonRepository extends Neo4jRepository<Person, String> {

    Person findFirstByAge(Integer age);

    Person findFirstByNameAndAge(String name, Integer age);

    Collection<Person> findAllByPersonIdIn(List<String> ids);

}
