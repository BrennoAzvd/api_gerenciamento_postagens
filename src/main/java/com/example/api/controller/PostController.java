package com.example.api.controller;


import com.example.api.dto.PostDTO;
import com.example.api.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

//  @GetMapping
//  public List<PostDTO> list() {
//    return postService.list();
//  }

  @GetMapping
  public List<PostDTO> listPosts(Pageable pageable) {
    return postService.listPosts(pageable).getContent();
  }

  @GetMapping("/{id}")
  public PostDTO findById(@PathVariable @NotNull UUID id) {
    return postService.findById(id);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public PostDTO create(@RequestBody @Valid @NotNull PostDTO post) {
    return postService.create(post);
  }

  @PutMapping("/{id}")
  public PostDTO update(@PathVariable @NotNull UUID id,
                          @RequestBody @Valid @NotNull PostDTO post) {
    return postService.update(id, post);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NotNull UUID id) {
    postService.delete(id);
  }
}
