package com.nexos.person.repository.person;

import com.nexos.person.commons.exceptions.business.DataCorruptedException;
import com.nexos.person.commons.exceptions.business.DataNotFoundedException;
import com.nexos.person.commons.exceptions.business.base.BusinessException;
import com.nexos.person.domain.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Person repository facade.
 */
public interface PersonRepositoryFacade {

    /**
     * Find all persons flux.
     *
     * @return the flux
     * @throws DataNotFoundedException the data not founded exception
     */
    Flux<Person> findAllPersons() throws DataNotFoundedException;

    /**
     * Find all unique persons flux.
     *
     * @return the flux
     * @throws DataNotFoundedException the data not founded exception
     */
    Flux<Person> findAllUniquePersons() throws DataNotFoundedException;

    /**
     * Find person by identification mono.
     *
     * @param idNumber the id number
     * @param idType   the id type
     * @return the mono
     * @throws BusinessException the business exception
     */
    Mono<Person> findPersonByIdentification(Long idNumber, String idType) throws BusinessException;

    /**
     * Create person mono.
     *
     * @param person the person
     * @return the mono
     * @throws DataCorruptedException the data corrupted exception
     */
    Mono<Long> createPerson(Person person) throws DataCorruptedException;

    /**
     * Update person mono.
     *
     * @param person   the person
     * @param idNumber the id number
     * @param type     the type
     * @return the mono
     * @throws BusinessException the business exception
     */
    Mono<Person> updatePerson(Person person, Long idNumber, String type) throws BusinessException;

    /**
     * Delete person mono.
     *
     * @param person the person
     * @return the mono
     * @throws BusinessException      the business exception
     * @throws DataCorruptedException the data corrupted exception
     */
    Mono<Person> deletePerson(Person person) throws BusinessException, DataCorruptedException;

}
