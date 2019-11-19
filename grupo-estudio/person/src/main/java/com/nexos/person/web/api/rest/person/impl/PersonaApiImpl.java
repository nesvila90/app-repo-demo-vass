package com.nexos.person.web.api.rest.person.impl;

import com.nexos.person.commons.exceptions.business.DataCorruptedException;
import com.nexos.person.commons.exceptions.business.DataNotFoundedException;
import com.nexos.person.commons.exceptions.business.base.BusinessException;
import com.nexos.person.domain.dto.generic.PersonDTO;
import com.nexos.person.service.person.PersonService;
import com.nexos.person.web.api.rest.person.PersonaApi;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/persona/v1")
@CrossOrigin(origins = "*")
@Api(value = "Employee Management System")
@Validated
public class PersonaApiImpl implements PersonaApi {

    private final PersonService service;

    @Autowired
    public PersonaApiImpl(PersonService service) {
        this.service = service;
    }

    @ApiOperation(value = "Add a Persona")
    @PostMapping
    @Override
    public ResponseEntity<Long> registerPerson(
            @ApiParam(value = "Employee object store in database table", required = true)
            @Valid @RequestBody PersonDTO person) throws DataCorruptedException, BusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(person));
    }

    @PutMapping("/type/{type}/identification/{numberId}")
    @Override
    public ResponseEntity<Void> updatePerson(@RequestBody @Valid PersonDTO person, @PathVariable("numberId") Long numberId, @PathVariable("type") String type) throws BusinessException {
        service.update(person, numberId , type);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/type/{type}/identification/{id}")
    @Override
    public ResponseEntity<Void> removePerson(@PathVariable("id") Long id, @PathVariable("type") String type) throws DataCorruptedException, BusinessException {
        service.delete(PersonDTO.builder().numberId(id).idType(type).build());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "View a Persona avaible", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved person"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/type/{type}/identification/{identification}")
    @Override
    public ResponseEntity<PersonDTO> findPersonById(
            @PathVariable("type") String type,
            @PathVariable("identification") Long identification) throws DataNotFoundedException, BusinessException {
        return ResponseEntity.ok().body(service.findById(identification, type));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<PersonDTO>> findAll() throws DataNotFoundedException, BusinessException {
        return ResponseEntity.ok().body(service.findAll());
    }
}
