package jrsperry.neo4jexamplessdn;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
class Neo4jExamplesSdnApplicationTests extends ContainerBaseTest{

	@Autowired
	Neo4jClient neo4jClient;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	PersonV2Repository personV2Repository;

	@Test
	void contextLoads() {
	}

	@Test
	void saveTest() {
		// load some people in, warm up
		IntStream.range(0, 50).forEach(i -> {
			Person person = complexPerson("johnny", i);
			personRepository.save(person);
		});

		Instant before = Instant.now();
		Integer iterations = 1000;
		IntStream.range(0, iterations).forEach(i -> {
			Person person = complexPerson("johnny", i);
			personRepository.save(person);
		});
		Instant after = Instant.now();
		Long totalMillisElapsed = Duration.between(before, after).toMillis();
		Long totalSecondsElapsed = Duration.between(before, after).toSeconds();
		log.info("time to save all {} complex people: {}s", iterations, totalSecondsElapsed);
		log.info("average time per complex person:  {}ms", totalMillisElapsed.doubleValue() / iterations);
	}

	@Test
	void loadTest(){
		// save some people first
		int nJohnnies = 250;
		IntStream.range(0, nJohnnies).forEach(i -> {
			if(i % 10 == 0){
				log.info("saved {}", i);
			}
			Person person = complexPerson("johnny", i);
			personRepository.save(person);
		});
		log.info("people saved");
		neo4jClient.query("CREATE TEXT INDEX IF NOT EXISTS FOR (t:Person) ON (t.age)");

		// load in batches all the 'johnnnys'
		List<Integer> johhnnyAges = IntStream.range(0, nJohnnies).boxed().collect(Collectors.toList());
		int batchSize = 10;
		Instant beforeAll = Instant.now();
		int i = 0;
		while(i < johhnnyAges.size()){
			Instant start = Instant.now();
			List<Person> johnnies = johhnnyAges.stream().skip(i).limit(batchSize).map(age -> personRepository.findFirstByNameAndAge("johnny", age)).collect(Collectors.toList());
			log.info("time to load {} johnniesV2:  {}ms", johnnies.size(), Duration.between(start, Instant.now()).toMillis());
			i = i + batchSize;
		}
		log.info("time to load  all {} johnniesV2:  {}s", johhnnyAges.size(), Duration.between(beforeAll, Instant.now()).toSeconds());


	}

	@Test
	void loadTestWithManagers(){
		// save some people first
		int nJohnnies = 250;
		IntStream.range(0, nJohnnies).forEach(i -> {
			if(i % 10 == 0){
				log.info("saved {}", i);
			}
			PersonV2 person = complexPersonV2("johnny", i);
			personV2Repository.save(person);
		});
		log.info("people saved");
		neo4jClient.query("CREATE TEXT INDEX IF NOT EXISTS FOR (t:Person) ON (t.age)");

		// load in batches all the 'johnnnys'
		List<Integer> johhnnyAges = IntStream.range(0, nJohnnies).boxed().collect(Collectors.toList());
		int batchSize = 10;
		Instant beforeAll = Instant.now();
		int i = 0;
		while(i < johhnnyAges.size()){
			Instant start = Instant.now();
			List<PersonV2> johnnies = johhnnyAges.stream().skip(i).limit(batchSize).map(age -> personV2Repository.findFirstByNameAndAge("johnny", age)).collect(Collectors.toList());
			log.info("time to load {} johnnies:  {}ms", johnnies.size(), Duration.between(start, Instant.now()).toMillis());
			i = i + batchSize;
		}
		log.info("time to load  all {} johnnies:  {}s", johhnnyAges.size(), Duration.between(beforeAll, Instant.now()).toSeconds());


	}

	private Person basePerson(String baseName, Integer i){
		Person basePerson = new Person(baseName, i);
		Person knows = new Person("knows", i);
		Person dislike = new Person("dislike", i);
		Person love = new Person("love", i);
		Person tolerate = new Person("tolerate", i);
		basePerson.addKnow(knows);
		basePerson.addDislike(dislike);
		basePerson.addLove(love);
		basePerson.addTolerates(tolerate);
		return basePerson;
	}

	private Person complexPerson(String complexName, Integer i){
		// builds a "complex" person, 25 total person objects
		Person knows = basePerson("knows", i);
		Person dislike = basePerson("dislike", i);
		Person love = basePerson("love", i);
		Person tolerate = basePerson("tolerate", i);
		Person complexPerson = basePerson(complexName, i);
		complexPerson.addKnow(knows);
		complexPerson.addDislike(dislike);
		complexPerson.addLove(love);
		complexPerson.addTolerates(tolerate);
		return complexPerson;
	}

	private PersonV2 basePersonV2(String baseName, Integer i){
		PersonV2 basePerson = new PersonV2(baseName, i);
		PersonV2 knows = new PersonV2("knows", i);
		PersonV2 dislike = new PersonV2("dislike", i);
		PersonV2 love = new PersonV2("love", i);
		PersonV2 tolerate = new PersonV2("tolerate", i);
		basePerson.addKnow(knows);
		basePerson.addDislike(dislike);
		basePerson.addLove(love);
		basePerson.addTolerates(tolerate);
		return basePerson;
	}

	private PersonV2 complexPersonV2(String complexName, Integer i){
		// builds a "complex" person, 25 total person objects
		PersonV2 knows = basePersonV2("knows", i);
		PersonV2 dislike = basePersonV2("dislike", i);
		PersonV2 love = basePersonV2("love", i);
		PersonV2 tolerate = basePersonV2("tolerate", i);
		PersonV2 complexPerson = basePersonV2(complexName, i);
		complexPerson.addKnow(knows);
		complexPerson.addDislike(dislike);
		complexPerson.addLove(love);
		complexPerson.addTolerates(tolerate);
		return complexPerson;
	}

}
