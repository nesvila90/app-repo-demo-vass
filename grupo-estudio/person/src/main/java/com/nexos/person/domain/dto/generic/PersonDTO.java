package com.nexos.person.domain.dto.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PersonDTO", description = "Api model for entity person.")
public class PersonDTO implements Serializable {

    @ApiModelProperty(value = "Identification Number ", example = "1110496497")
    private Long numberId;
    @ApiModelProperty(value = "Identification type ", example = "CC")
    private String idType;
    @ApiModelProperty(value = "First Name " , example = "NESTOR")
    private String firstName;
    @ApiModelProperty(value = "Last Name ", example = "VILLAR")
    private String lastName;
    @ApiModelProperty(value = "Birthdate ", example = "1999-09-09")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @ApiModelProperty(value = "Genre ", example = "MALE")
    private String genre;
    @ApiModelProperty(value = "Email ", example = "email@email")
    private String email;


}
