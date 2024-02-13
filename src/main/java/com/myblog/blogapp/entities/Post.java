package com.myblog.blogapp.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="post",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"} )})
public class Post
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name="title", nullable = false)
        private String title;

        @Column(name="description", nullable = false)
        private String description;

        @Lob
        @Column(name="content", nullable = false)
        private String content;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Comment> comments = new HashSet<>();

}
