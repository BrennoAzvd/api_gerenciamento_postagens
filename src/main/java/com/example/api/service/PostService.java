package com.example.api.service;


import com.example.api.dto.PostDTO;
import com.example.api.dto.mapper.PostMapper;
import com.example.api.exception.RecordNotFoundException;
import com.example.api.repository.PostRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Validated
@Service
public class PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;

  public PostService(PostRepository postRepository, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.postMapper = postMapper;
  }

//  public List<PostDTO> list() {
//    return postRepository.findAll()
//            .stream()
//            .map(postMapper::toDTO)
//            .toList();
//  }

  public Page<PostDTO> listPosts(Pageable pageable) {
    return postRepository.findAll(pageable)
            .map(postMapper::toDTO);
  }

  public PostDTO findById(@PathVariable @NotNull UUID id) {
    return postRepository.findById(id).map(postMapper::toDTO) // -> corrigir esse findByID()
            .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public PostDTO create(@Valid @NotNull PostDTO post) {
    return postMapper.toDTO(postRepository.save(postMapper.toEntity(post)));
  }

  public PostDTO update(@NotNull UUID id, @Valid @NotNull PostDTO post) {
    return postRepository.findById(id)
            .map(recordFound -> {
              recordFound.setTitle(post.title());
              recordFound.setDescription(post.description());
              //recordFound.setCreation_date(post.creation_date());
              recordFound.setUpdate_date(post.update_date());
              recordFound.setPost_type(postMapper.convertPostTypeValue(post.post_type()));
              return postMapper.toDTO(postRepository.save(recordFound));
            }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@PathVariable @NotNull UUID id) {
    postRepository.delete(postRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
  }
}
