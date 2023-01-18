package jrsperry.neo4jexamplessdn;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@Slf4j
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    @Relationship
    private List<Manager> managers;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, Integer age, Person knows) {
        this.name = name;
        this.age = age;
        this.knowsPeople.add(knows);
    }

    @Relationship(type = "KNOWS")
    private Set<Person> knowsPeople = new HashSet<>();

    @Relationship(type = "DISLIKES")
    private Set<Person> dislikes = new HashSet<>();

    @Relationship(type = "LOVES")
    private Set<Person> loves = new HashSet<>();

    @Relationship(type = "TOLERATES")
    private Set<Person> tolerates = new HashSet<>();

    public void addKnow(Person person){
        this.knowsPeople.add(person);
    }

    public void addDislike(Person person){
        this.dislikes.add(person);
    }

    public void addLove(Person person){
        this.loves.add(person);
    }

    public void addTolerates(Person person){
        this.tolerates.add(person);
    }


}
