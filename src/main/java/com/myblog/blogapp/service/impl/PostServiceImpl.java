package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entities.Post;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.repositories.PostRepository;
import com.myblog.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postrepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postrepo, ModelMapper modelMapper) {
        this.postrepo = postrepo;
        this.modelMapper= modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto)
    {

        Post post = mapToEntity(postDto);
        Post postEntity = postrepo.save(post);

        PostDto dto = mapToDto(postEntity);
        return dto;
    }
    public Post mapToEntity(PostDto postDto)
    {
        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;

    }

   public PostDto mapToDto (Post post)
   {
       PostDto dto = modelMapper.map(post, PostDto.class);

//       PostDto dto = new PostDto();
//       dto.setId(post.getId());
//       dto.setId(post.getId());
//       dto.setTitle(post.getTitle());
//       dto.setDescription(post.getDescription());
//       dto.setContent(post.getContent());
       return dto;
   }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                   : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postrepo.findAll(pageable);
        List<Post> content = posts.getContent();

        List<PostDto> contents = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(long id)
    {
        Post post = postrepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id));
        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id)
    {
        Post post = postrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postrepo.save(post);
        return mapToDto(newPost);
    }

    @Override
    public void deletePost(long id)
    {
        postrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postrepo.deleteById(id);

    }
}
