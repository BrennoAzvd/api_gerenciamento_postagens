package com.example.api.model;

import com.example.api.enums.PostType;
import com.example.api.enums.PostTypeConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Table(name ="post")
@Entity
public class Post {

  @Id
  @UuidGenerator //(style = UuidGenerator.Style.TIME)
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

  //@Column(name = "nome_correto_da_coluna")
  private LocalDate creation_date; // = LocalDate.now();

  private LocalDate update_date;

  @NotNull
  @Convert(converter = PostTypeConverter.class)
  private PostType post_type;

  private String image_name;



  @PrePersist
  protected  void onCreated(){
    creation_date = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate(){
    update_date = LocalDate.now();
  }

//  public Post(UUID id, String title, String description, LocalDate creation_date, Date update_date, String post_type) {
//    this.id = id;
//    this.title = title;
//    this.description = description;
//    this.creation_date = creation_date;
//    this.update_date = update_date;
//    this.post_type = post_type;
//  }


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public LocalDate getCreation_date() {
    return creation_date;
  }

  public void setCreation_date(LocalDate creation_date) {
    this.creation_date = creation_date;
  }

  public LocalDate getUpdate_date() {
    return update_date;
  }

  public void setUpdate_date(LocalDate update_date) {
    this.update_date = update_date;
  }

  public @NotNull PostType getPost_type() {
    return post_type;
  }

  public void setPost_type(@NotNull PostType post_type) {
    this.post_type = post_type;
  }

  public String getImage_name() {
    return image_name;
  }

  public void setImage_name(String image_name) {
    this.image_name = image_name;
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
