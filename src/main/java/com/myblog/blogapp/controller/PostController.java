package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.service.PostService;
import com.myblog.blogapp.utils.AppConstants;
import org.hibernate.annotations.SQLDeleteAll;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController
{
    private PostService postservice;

    public PostController(PostService postservice)
    {
        this.postservice = postservice;
    }


    @PreAuthorize("hasRole('ADMIN')")  //it will work only after adding globalsecurity annotation on configure module
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {

            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postservice.createPost(postDto), HttpStatus.CREATED);


    }

    //
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false ) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    )
    {
       return postservice.getAllPosts(pageNo, pageSize, sortBy, sortDir);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id)
    {
       return ResponseEntity.ok(postservice.getPostById(id));

    }

    //updating record
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id)
    {
        PostDto dto = postservice.updatePost(postDto, id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id)
    {
        postservice.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
    }

}
