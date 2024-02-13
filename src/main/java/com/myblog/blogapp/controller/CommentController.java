package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/api/"))
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto) {
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable("postId") long postId) {
        List<CommentDto> dto = commentService.getCommentByPostId(postId);
        return dto;
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @RequestBody CommentDto commentDto) {

        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId) {
        commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("Comment is deleted", HttpStatus.OK);


    }
}
