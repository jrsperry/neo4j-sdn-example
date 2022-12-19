package jrsperry.neo4jexamplessdn;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
class Neo4jExamplesSdnApplicationTests {

	@Autowired
	PersonRepository personRepository;

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
		IntStream.range(0, 2000).forEach(i -> {
			if(i % 100 == 0){
				log.info("loaded {}", i);
			}
			Person person = complexPerson("johnny", i);
			personRepository.save(person);
		});
		log.info("people saved");
		// load in batches all the 'johnnnys'
		List<Integer> johhnnyAges = IntStream.range(0, 2000).boxed().collect(Collectors.toList());
		for(int i = 0; i < 25; i++){
			Instant start = Instant.now();
			int batchStart = i * 25;
			List<Person> johnnies = johhnnyAges.stream().skip(batchStart).limit(25).map(age -> personRepository.findFirstByAge(age)).collect(Collectors.toList());
			log.info("time to load 25 johnnies:  {}ms", Duration.between(start, Instant.now()).toMillis());
		}
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
		Person complexPerson = basePerson("johnny", i);
		complexPerson.addKnow(knows);
		complexPerson.addDislike(dislike);
		complexPerson.addLove(love);
		complexPerson.addTolerates(tolerate);
		return complexPerson;
	}

}
