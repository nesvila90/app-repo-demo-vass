package com.nexos.person.repository.person.impl;

import com.nexos.person.commons.exceptions.business.DataNotFoundedException;
import com.nexos.person.domain.entity.Person;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class PersonRepositoryFacadeImplTest {

    @InjectMocks
    private PersonRepositoryFacadeImpl repositoryFacade;

    @MockBean
    private List<Person> personsList;


    @Before
    public void setUp() throws Exception {

        this.personsList = new ArrayList<>();
        Person p = new Person();
        p.setNumberId(123456789L);
        p.setIdType("CC");
        p.setGenre("F");
        p.setEmail("correo");
        p.setFirstName("Prueba1");
        p.setLastName("Prueba2");
        personsList.add(p);
    }

}