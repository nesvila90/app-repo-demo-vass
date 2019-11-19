package com.nexos.person.repository.person;

import com.nexos.person.domain.entity.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

/**
 * The interface Person repository.
 */
@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, BigInteger> {

    /**
     *
     * Find person by number id and id type mono.
     *
     * @author Nestor Villar
     * @param idNumber the id number
     * @param idType   the id type
     * @return the mono
     */
    Mono<Person> findPersonByNumberIdAndIdType(Long idNumber, String idType);

}
