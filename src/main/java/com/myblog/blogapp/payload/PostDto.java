package com.myblog.blogapp.payload;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto
{
        private long id;

        @NotNull
        @Size(min=2, message = "Post should have atleast 2 charachetrs")
        private String title;

        @NotNull
        @Size(min=10, message = "PostDescription should have atleast 10 charachetrs")
        private String description;

        @NotNull
        @NotEmpty
        private String content;

}
