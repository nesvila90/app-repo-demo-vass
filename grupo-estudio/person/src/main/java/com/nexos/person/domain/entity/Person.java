package com.nexos.person.domain.entity;


import com.nexos.person.domain.entity.base.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * The type Person.
 */
@Data
@Builder
@Document(collection = "person")
@EqualsAndHashCode(callSuper = false, of = "numberId")
@AllArgsConstructor
@NoArgsConstructor
public class Person extends BaseDocument {

    @Indexed(unique = true)
    private Long numberId;
    private String idType;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String genre;
    private String email;


    /**
     * Instantiates a new Person.
     *
     * @param idNumber the id number
     * @param idType   the id type
     */
    public Person(Long idNumber, String idType) {
        super();
        this.numberId = idNumber;
        this.idType = idType;
    }
}
