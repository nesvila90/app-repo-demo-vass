package com.nexos.person.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexos.person.commons.enums.AuditAction;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "personAudit")
@EqualsAndHashCode(callSuper = false, of = "numberId")
@AllArgsConstructor
@NoArgsConstructor
public class PersonAudit {

    @Id
    private BigInteger id;
    private BigInteger idData;
    private Long numberId;
    private String idType;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String genre;
    private String email;
    private String throwable;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedDate;
    @Version
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long version;

    private AuditAction action;

}
