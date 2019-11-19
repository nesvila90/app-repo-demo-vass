package com.nexos.person.domain.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDocument implements Serializable {

    @Id
    @Generated
    private BigInteger id;

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

    private Boolean delete = Boolean.FALSE;

}
