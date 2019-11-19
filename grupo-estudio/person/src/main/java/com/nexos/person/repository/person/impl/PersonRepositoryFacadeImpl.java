package com.nexos.person.repository.person.impl;

import com.nexos.person.commons.enums.AuditAction;
import com.nexos.person.commons.enums.ErrorCode;
import com.nexos.person.commons.exceptions.builder.ExceptionBuilder;
import com.nexos.person.domain.entity.Person;
import com.nexos.person.domain.entity.PersonAudit;
import com.nexos.person.repository.person.PersonAuditRepository;
import com.nexos.person.repository.person.PersonRepository;
import com.nexos.person.repository.person.PersonRepositoryFacade;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * The type Person repository facade.
 */
@Component
@Log4j2
public class PersonRepositoryFacadeImpl implements PersonRepositoryFacade {

    private final PersonRepository personRepository;

    private final PersonAuditRepository personAuditRepository;

    /**
     * Instantiates a new Person repository facade.
     *
     * @param personRepository      the person repository
     * @param personAuditRepository the person audit repository
     */
    @Autowired
    public PersonRepositoryFacadeImpl(PersonRepository personRepository, PersonAuditRepository personAuditRepository) {
        this.personRepository = personRepository;
        this.personAuditRepository = personAuditRepository;
    }

    @Override
    public Flux<Person> findAllPersons() {
        log.info("Seeking All Persons...");
        return getPeople().log().switchIfEmpty(Mono.error(ExceptionBuilder.newBuilder()
                .withMessage("Not found Persons, Empty list...")
                .buildDataNotFoundedException()));

    }

    @Override
    public Flux<Person> findAllUniquePersons() {
        log.info("Seeking All Uniques Persons...");
        return getPeople().distinct().log().doOnError(throwable -> ExceptionBuilder.newBuilder()
                .withMessage("Not found Persons, Empty list...")
                .withRootException(throwable)
                .withRootException(throwable)
                .buildDataNotFoundedException());

    }

    @Override
    public Mono<Person> findPersonByIdentification(Long idNumber, String idType) {
        log.info("Seeking Person by ID {} and type {}", idNumber, idType);
        return personRepository.findPersonByNumberIdAndIdType(idNumber, idType).log()
                .doOnError(throwable -> ExceptionBuilder.newBuilder()
                        .withCode(ErrorCode.BUSINESS_EXCEPTION)
                        .withRootException(throwable)
                        .withMessage("Person not found by identification")
                        .buildBusinessException())
                .doAfterSuccessOrError((person1, throwable) -> auditProcess(ObjectUtils.isEmpty(person1) ? new Person(idNumber, idType) : person1, throwable, AuditAction.SELECT));

    }

    private Flux<Person> getPeople() {
        return personRepository.findAll().log().switchIfEmpty(Mono.error(() -> ExceptionBuilder.newBuilder()
                .withCode(ErrorCode.BUSINESS_EXCEPTION)
                .withMessage("Person not found by identification")
                .buildBusinessException()));
    }

    @Override
    public Mono<Long> createPerson(Person person) {
        log.info("Persist Person... {}", person.toString());
        Mono<Person> personSaved = personRepository.insert(person)
                .doOnError(throwable -> ExceptionBuilder.newBuilder()
                        .withCode(ErrorCode.PERSISTENCE_EXCEPTION)
                        .withRootException(throwable)
                        .withMessage("Error Persist Person.")
                        .buildDataCorruptedException())
                .doAfterSuccessOrError((person1, throwable) -> auditProcess(person1, throwable, AuditAction.INSERT));
        return personSaved.map(Person::getNumberId);


    }

    @Override
    public Mono<Person> updatePerson(Person person, Long idNumber, String type) {
        log.info("Update Person... {}", person.toString());
        personRepository.findPersonByNumberIdAndIdType(idNumber, type).subscribe(p -> {
            Person personUpdate = mappingPerson(person, p);
            personRepository.save(personUpdate).log()
                    .onErrorContinue((throwable, o) -> ExceptionBuilder.newBuilder()
                            .withCode(ErrorCode.BUSINESS_EXCEPTION)
                            .withRootException(throwable)
                            .withMessage("Person to be Updated, not could be found")
                            .buildBusinessException())
                    .doAfterSuccessOrError((person1, throwable) -> auditProcess(personUpdate, throwable, AuditAction.UPDATE))
                    .subscribe();
        });

        return Mono.justOrEmpty(person);

    }

    @Override
    public Mono<Person> deletePerson(Person person) {
        log.info("Remove Person... {}", person.toString());
        personRepository.findPersonByNumberIdAndIdType(person.getNumberId(), person.getIdType()).log()
                .subscribe(personDelete -> personRepository.delete(personDelete)
                        .onErrorContinue((throwable, o) -> ExceptionBuilder.newBuilder()
                                .withRootException(throwable)
                                .withCode(ErrorCode.BUSINESS_EXCEPTION)
                                .withMessage("Person to be Removed, not could be found.")
                                .buildBusinessException())
                        .doAfterSuccessOrError((aVoid, throwable) -> auditProcess(personDelete, throwable, AuditAction.DELETE)).subscribe());
        return Mono.justOrEmpty(person);
    }

    private void auditProcess(Person person, Throwable throwable, AuditAction action) {
        personAuditRepository.insert(mapperPersonAudit(person, throwable, action)).subscribe();

    }


    private Person mappingPerson(Person person, Person personFounded) {
        return mapperPerson(person, personFounded);
    }


    private Person mappingPerson(Person person, Mono<Person> personFounded) {
        Person personMapped = personFounded.blockOptional().get();
        return mapperPerson(person, personMapped);
    }

    private Person mapperPerson(Person person, Person personMapped) {
        personMapped.setNumberId(person.getNumberId());
        personMapped.setIdType(person.getIdType());
        personMapped.setFirstName(person.getFirstName());
        personMapped.setLastName(person.getLastName());
        personMapped.setBirthDate(person.getBirthDate());
        personMapped.setEmail(person.getEmail());
        personMapped.setGenre(person.getGenre());
        return personMapped;
    }

    private PersonAudit mapperPersonAudit(Person person, Throwable throwable, AuditAction action) {
        PersonAudit personMapped = new PersonAudit();
        if (!ObjectUtils.isEmpty(person.getId())) {
            personMapped.setIdData(person.getId());
        }
        personMapped.setNumberId(person.getNumberId());
        personMapped.setIdType(person.getIdType());
        personMapped.setFirstName(person.getFirstName());
        personMapped.setLastName(person.getLastName());
        personMapped.setBirthDate(person.getBirthDate());
        personMapped.setEmail(person.getEmail());
        personMapped.setGenre(person.getGenre());
        personMapped.setAction(action);
        personMapped.setThrowable(ObjectUtils.nullSafeToString(throwable));
        return personMapped;
    }

}
