package jrsperry.neo4jexamplessdn;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@Slf4j
public class Manager {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    public Manager(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
