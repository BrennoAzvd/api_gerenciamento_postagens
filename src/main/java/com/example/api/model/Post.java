package com.example.api.model;

import com.example.api.enums.PostType;
import com.example.api.enums.PostTypeConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@Table(name ="post")
@Entity
public class Post {

  @Setter
  @Getter
  @Id
  @UuidGenerator
  @JsonProperty("_id")
  private UUID id;

  @NotBlank
  @NotNull
  @Length(min = 5, max = 200)
  private String title;

  @NotBlank
  @NotNull
  @Length(min = 20, max = 500)
  private String description;

  @Setter
  @Getter
  private LocalDate creation_date;

  @Setter
  @Getter
  private LocalDate update_date;

  @NotNull
  @Convert(converter = PostTypeConverter.class)
  private PostType post_type;

  @Setter
  @Getter
  private String image_name;

  @Setter
  @Getter
  private String user_registration;

  @Setter
  @Getter
  private String user_update = null;



  @PrePersist
  protected  void onCreated(){
    creation_date = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate(){
    update_date = LocalDate.now();
  }


  public @NotBlank @NotNull @Length(min = 5, max = 200) String getTitle() {
    return title;
  }

  public void setTitle(@NotBlank @NotNull @Length(min = 5, max = 200) String title) {
    this.title = title;
  }

  public @NotBlank @NotNull @Length(min = 20, max = 500) String getDescription() {
    return description;
  }

  public void setDescription(@NotBlank @NotNull @Length(min = 20, max = 500) String description) {
    this.description = description;
  }

  public @NotNull PostType getPost_type() {
    return post_type;
  }

  public void setPost_type(@NotNull PostType post_type) {
    this.post_type = post_type;
  }

  @Override
  public String toString() {
    return "Post{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", creation_date=" + creation_date +
            ", update_date=" + update_date +
            ", post_type=" + post_type +
            '}';
  }
}
