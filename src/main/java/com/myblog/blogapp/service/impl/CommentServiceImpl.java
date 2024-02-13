package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entities.Comment;
import com.myblog.blogapp.entities.Post;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.repositories.CommentRepository;
import com.myblog.blogapp.repositories.PostRepository;
import com.myblog.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService
{
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper)
    {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto)
    {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = mapToComment(commentDto);

        comment.setPost(post); // for this post
        Comment newComment = commentRepository.save(comment);// save this comment
        return mapToDto(newComment);

    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) 
    {
        //Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto)
    {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId)
    {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.deleteById(commentId);

    }

    Comment mapToComment(CommentDto commentDto)
    {
        Comment comment = modelMapper.map(commentDto, Comment.class);

//                Comment comment = new Comment();
//                comment.setName(commentDto.getName());
//                comment.setEmail(commentDto.getEmail());
//                comment.setBody(commentDto.getBody());
        return comment;
    }

    CommentDto mapToDto(Comment comment)
    {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(newComment.getId());
//        commentDto.setName(newComment.getName());
//        commentDto.setEmail(newComment.getEmail());
//        commentDto.setBody(newComment.getBody());

        return commentDto;

    }
}
