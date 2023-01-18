package jrsperry.neo4jexamplessdn;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonV2Repository extends Neo4jRepository<PersonV2, Long> {

    PersonV2 findFirstByAge(Integer age);

    PersonV2 findFirstByNameAndAge(String name, Integer age);

}
