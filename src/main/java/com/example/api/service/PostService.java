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
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    return postRepository.findById(id).map(postMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public PostDTO create(@Valid @NotNull PostDTO post, String image) {

    return postMapper.toDTO(postRepository.save(postMapper.toEntity(post, image)));
  }

  public PostDTO update(@NotNull UUID id, @Valid @NotNull PostDTO post) {
    return postRepository.findById(id)
            .map(recordFound -> {
              recordFound.setTitle(post.title());
              recordFound.setDescription(post.description());
              recordFound.setUpdate_date(post.update_date());
              recordFound.setPost_type(postMapper.convertPostTypeValue(post.post_type()));
              return postMapper.toDTO(postRepository.save(recordFound));
            }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public Boolean delete(@PathVariable @NotNull UUID id) {
    postRepository.delete(postRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
    return true;
  }

  public void updateImageName(@NotNull UUID id, String newImageName) {
    postRepository.findById(id)
            .map(recordFound -> {
              recordFound.setImage_name(newImageName);
              return postMapper.toDTO(postRepository.save(recordFound));
            });
  }


}
