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
public class PersonV2 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    @Relationship
    private List<Manager> managers;

    public PersonV2(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public PersonV2(String name, Integer age, PersonV2 knows) {
        this.name = name;
        this.age = age;
        this.knowsPeople.add(knows);
    }

    @Relationship(type = "KNOWS")
    private Set<PersonV2> knowsPeople = new HashSet<>();

    @Relationship(type = "DISLIKES")
    private Set<PersonV2> dislikes = new HashSet<>();

    @Relationship(type = "LOVES")
    private Set<PersonV2> loves = new HashSet<>();

    @Relationship(type = "TOLERATES")
    private Set<PersonV2> tolerates = new HashSet<>();

    public void addKnow(PersonV2 person){
        this.knowsPeople.add(person);
    }

    public void addDislike(PersonV2 person){
        this.dislikes.add(person);
    }

    public void addLove(PersonV2 person){
        this.loves.add(person);
    }

    public void addTolerates(PersonV2 person){
        this.tolerates.add(person);
    }


}
