package com.example.api.controller;


import com.example.api.dto.PostDTO;
import com.example.api.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/posts")
public class PostController {

/*
Aqui, "UPLOAD_DIRECTORY" deve ser o nome da variável de ambiente que contém o caminho desejado.
Certifique-se de que esta variável de ambiente está devidamente configurada no sistema onde a aplicação será executada.
Exemplo: C:/Users/brenn/Documents/ImagesAPI/
*/
  public static final String UPLOAD_DIRECTORY = System.getenv("UPLOAD_DIRECTORY");
  //C:/Users/brenn/Documents/ImagesAPI/

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }


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
  public PostDTO create(HttpServletRequest request, @RequestPart("data") @Valid @NotNull PostDTO post, @RequestParam("file") MultipartFile image) {
    String ImageName = null;
    try {
      if (!image.isEmpty()) {
        byte[] bytes = image.getBytes();
        ImageName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path caminho = Paths.get(UPLOAD_DIRECTORY + ImageName);
        Files.write(caminho, bytes);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return postService.create(post, ImageName, request);
  }

  @PutMapping("/{id}")
  public PostDTO update(HttpServletRequest request,@PathVariable @NotNull UUID id,
                          @RequestBody @Valid @NotNull PostDTO post) {
    return postService.update(id, post, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NotNull UUID id) {

      try {
        PostDTO post = postService.findById(id);
        if (post == null || post.image_name() == null || post.image_name().trim().isEmpty()) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found for the given ID");
        }

        Path path = Paths.get(UPLOAD_DIRECTORY + post.image_name());
        if (Files.exists(path)) {
          Files.delete(path);
        } else {
          throw new FileNotFoundException("File not found: " + post.image_name());
        }
      } catch (IOException e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting the image", e);
      }

    postService.delete(id);
  }

  //Rotas referentes à imagem

  @GetMapping("/ShowImage/{id}")
  public byte[] returnImage(@PathVariable UUID id) throws IOException {
    PostDTO post = postService.findById(id);
    if (post == null || post.image_name() == null || post.image_name().trim().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found for the given ID");
    }

    File imageFile = new File(UPLOAD_DIRECTORY + post.image_name());
    return Files.readAllBytes(imageFile.toPath());
  }


  @PutMapping("/updateImage/{id}")
  public ResponseEntity<String> updateImage(@PathVariable UUID id, @RequestParam("file") MultipartFile newImage) {
    String ImageName = null;
    try {
      PostDTO post = postService.findById(id);
      if (post == null) {
        return ResponseEntity.notFound().build();
      }

      if (!newImage.isEmpty()) {
        // Deletar a imagem antiga
        String oldImageName = post.image_name();
        Path oldImagePath = Paths.get(UPLOAD_DIRECTORY + oldImageName);
        if (Files.exists(oldImagePath)) {
          Files.delete(oldImagePath);
        }

        // Salvar a nova imagem
        byte[] bytes = newImage.getBytes();
        ImageName = UUID.randomUUID() + "_" + newImage.getOriginalFilename();
        Path newPath = Paths.get(UPLOAD_DIRECTORY + ImageName);
        Files.write(newPath, bytes);

        // Atualizar o nome da imagem no registro do post
        postService.updateImageName(id, newImage.getOriginalFilename());
      }

      return ResponseEntity.ok("Image updated successfully");
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating the image");
    }
  }


  }
