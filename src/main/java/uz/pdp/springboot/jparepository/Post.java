package uz.pdp.springboot.jparepository;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Post.getAllPostsByUserId",
                query = "select p from Post p where p.userId = ?1"
        )  // named query
})
@SqlResultSetMapping(
        name = "PostDTO_MAPPING",
        classes = @ConstructorResult(
                targetClass = PostDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "body", type = String.class)
                }
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Post.getAllPostByUserId.Native",
                query = "select p.* from post p where p.user_id = ?1",
                resultClass = Post.class
        ),  // named native query
        @NamedNativeQuery(
                name = "Post.find.all.by.projection",
                query = "select p.* from post p;",
                resultSetMapping = "PostDTO_MAPPING"
        )
})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String body;
}
