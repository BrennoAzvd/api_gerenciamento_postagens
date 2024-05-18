package com.example.api.dto.mapper;

import com.example.api.dto.PostDTO;
import com.example.api.enums.PostType;
import com.example.api.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

  public PostDTO toDTO(Post post) {
    if (post == null) {
      return null;
    }
//    List<LessonDTO> lessons = course.getLessons()
//            .stream()
//            .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(),
//                    lesson.getYoutubeUrl()))
//            .collect(Collectors.toList());
    return new PostDTO(post.getId(), post.getTitle(), post.getDescription(), post.getCreation_date(),
      post.getUpdate_date(), post.getPost_type().getValue());
  }

  public Post toEntity(PostDTO postDTO) {

    if (postDTO == null) {
      return null;
    }

    Post post = new Post();
    if (postDTO.id() != null) {
      post.setId(postDTO.id());
    }
    post.setTitle(postDTO.title());
    post.setDescription(postDTO.description());
    post.setCreation_date(postDTO.creation_date());
    post.setUpdate_date(postDTO.update_date());
    post.setPost_type(convertPostTypeValue(postDTO.post_type()));
    return post;
  }

  public PostType convertPostTypeValue(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "Noticia" -> PostType.NOTICIA;
      case "Edital" -> PostType.EDITAL;
      case "Divulgacao" -> PostType.DIVULGACAO;
      default -> throw new IllegalArgumentException("Tipo de postagem inválida: " + value);
    };
  }

}
