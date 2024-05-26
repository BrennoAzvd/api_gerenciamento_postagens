package com.example.api.service;


import com.example.api.dto.PostDTO;
import com.example.api.dto.mapper.PostMapper;
import com.example.api.exception.RecordNotFoundException;
import com.example.api.repository.PostRepository;
import com.example.api.repository.UserRepository;
import com.example.api.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;


@Validated
@Service
public class PostService {


  private final PostRepository postRepository;
  //@Autowired
  private final UserRepository userRepository;
  private final PostMapper postMapper;
  @Autowired
  protected TokenService tokenService;

  public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.postMapper = postMapper;
  }


  public Page<PostDTO> listPosts(Pageable pageable) {
    return postRepository.findAll(pageable)
            .map(postMapper::toDTO);
  }

  public PostDTO findById(@PathVariable @NotNull UUID id) {
    return postRepository.findById(id).map(postMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public PostDTO create(@Valid @NotNull PostDTO post, String image, HttpServletRequest request) {

    return postMapper.toDTO(postRepository.save(postMapper.toEntity(post, image, request)));
  }

  public PostDTO update(@NotNull UUID id, @Valid @NotNull PostDTO post, HttpServletRequest request) {
    var token = tokenService.recoverToken(request);
    var login = tokenService.validateToken(token);
    UserDetails user = userRepository.findUsersByUsername(login);
    
    return postRepository.findById(id)
            .map(recordFound -> {
              recordFound.setTitle(post.title());
              recordFound.setDescription(post.description());
              recordFound.setUpdate_date(post.update_date());
              recordFound.setPost_type(postMapper.convertPostTypeValue(post.post_type()));
              recordFound.setUser_update(user.getUsername());
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
