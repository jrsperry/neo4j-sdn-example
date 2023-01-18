package jrsperry.neo4jexamplessdn;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ManagerRepository extends Neo4jRepository<Manager, Long> {


}
