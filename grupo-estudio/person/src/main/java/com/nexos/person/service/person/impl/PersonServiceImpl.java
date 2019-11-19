package com.nexos.person.service.person.impl;

import com.nexos.person.commons.exceptions.builder.ExceptionBuilder;
import com.nexos.person.commons.exceptions.business.DataCorruptedException;
import com.nexos.person.commons.exceptions.business.DataNotFoundedException;
import com.nexos.person.commons.exceptions.business.base.BusinessException;
import com.nexos.person.domain.dto.generic.PersonDTO;
import com.nexos.person.domain.entity.Person;
import com.nexos.person.repository.person.PersonRepositoryFacade;
import com.nexos.person.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepositoryFacade personRepositoryFacade;

    @Autowired
    public PersonServiceImpl(PersonRepositoryFacade personRepositoryFacade) {
        this.personRepositoryFacade = personRepositoryFacade;
    }


    public Flux<PersonDTO> findAllPerson() throws DataNotFoundedException {
        return personRepositoryFacade.findAllPersons().distinct().map(person ->
                PersonDTO.builder()
                        .numberId(person.getNumberId())
                        .idType(person.getIdType())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .birthDate(person.getBirthDate())
                        .email(person.getEmail())
                        .genre(person.getGenre())
                        .build()
        );

        /*
        return personRepositoryFacade.findAllPersons().collectList().blockOptional().map(people -> people.stream().map(person ->
                PersonDTO.builder()
                        .numberId(person.getNumberId())
                        .idType(person.getIdType())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .birthDate(person.getBirthDate())
                        .email(person.getEmail())
                        .genre(person.getGenre())
                        .build()
        ).collect(Collectors.toList())).get();

         */
    }

    @Override
    public List<PersonDTO> findAll() throws BusinessException, DataNotFoundedException {
        return personRepositoryFacade.findAllPersons().collectList().blockOptional().map(people -> people.stream().map(person ->
                PersonDTO.builder()
                        .numberId(person.getNumberId())
                        .idType(person.getIdType())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .birthDate(person.getBirthDate())
                        .email(person.getEmail())
                        .genre(person.getGenre())
                        .build()
        ).collect(Collectors.toList())).get();

    }

    @Override
    public Set<PersonDTO> findAllUnique() throws DataNotFoundedException {
        return personRepositoryFacade.findAllUniquePersons().collectList().blockOptional().map(people -> people.stream().map(person ->
                PersonDTO.builder()
                        .numberId(person.getNumberId())
                        .idType(person.getIdType())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .birthDate(person.getBirthDate())
                        .email(person.getEmail())
                        .genre(person.getGenre())
                        .build()
        ).collect(Collectors.toSet())).get();

    }

    @Override
    public PersonDTO findById(Long idNumber, String idType) throws BusinessException, DataNotFoundedException {
        return personRepositoryFacade.findPersonByIdentification(idNumber, idType).map(
                person -> PersonDTO.builder()
                        .numberId(person.getNumberId())
                        .idType(person.getIdType())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .birthDate(person.getBirthDate())
                        .email(person.getEmail())
                        .genre(person.getGenre())
                        .build()).blockOptional().orElseThrow(DataNotFoundedException::new);
    }

    @Override
    public Long create(PersonDTO person) throws DataCorruptedException, BusinessException {

        return personRepositoryFacade.createPerson(
                Person.builder()
                        .numberId(person.getNumberId())
                        .idType(person.getIdType())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .birthDate(person.getBirthDate())
                        .email(person.getEmail())
                        .genre(person.getGenre())
                        .build()).log().doOnError(throwable -> ExceptionBuilder.newBuilder().withRootException(throwable).buildBusinessException()).blockOptional().get();

    }

    @Override
    public void update(PersonDTO person, Long idNumber, String type) throws BusinessException {
        personRepositoryFacade.updatePerson(
                Person.builder()
                        .birthDate(person.getBirthDate())
                        .genre(person.getGenre())
                        .email(person.getEmail())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .numberId(person.getNumberId())
                        .idType(person.getIdType())
                        .build(), idNumber, type);
    }

    @Override
    public void delete(PersonDTO person) throws BusinessException, DataCorruptedException {
        personRepositoryFacade.deletePerson(Person.builder().idType(person.getIdType()).numberId(person.getNumberId()).build());
    }
}
