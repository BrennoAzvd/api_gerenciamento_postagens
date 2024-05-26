package com.example.api.dto;

import com.example.api.enums.PostType;
import com.example.api.enums.validation.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


import java.time.LocalDate;
import java.util.UUID;

public record PostDTO (
        @JsonProperty("_id") UUID id,
        @NotBlank @NotNull @Length(min = 5, max = 200) String title,
        @NotBlank @Length(min = 20, max = 500) String description,
        /*@NotBlank*/ @JsonFormat(pattern="yyyy-MM-dd") LocalDate creation_date,
        /*@NotBlank*/ @JsonFormat(pattern="yyyy-MM-dd") LocalDate update_date,
        @NotNull @Length(max = 10) @ValueOfEnum(enumClass = PostType.class) String post_type,
        String image_name,
        String user_registration,
        String user_update
){

}
