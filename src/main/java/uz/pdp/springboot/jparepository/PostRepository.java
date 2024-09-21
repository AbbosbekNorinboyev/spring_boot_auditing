package uz.pdp.springboot.jparepository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {
    //    @Query(value = "select p from Post p where p.userId = ?1")  // jpql (jakarta persistence query language)
//    @Query(nativeQuery = true, value = "select p.* from post p where p.user_id = ?1")  // native query
//    @Query(name = "Post.getAllPostsByUserId")  // named query
    @Query(nativeQuery = true, name = "Post.getAllPostsByUserId.Native")
    // named native      query
    List<Post> getAllPostsByUserId(Integer userId);

    @Query(value = "select p from Post p")
    List<Post> getAllByPostsWithSortedColumns(Sort sort);

    //    @Query(value = "select p from Post p")
    @Query(nativeQuery = true, value = "select p.* from post p", countQuery = "select count(1) from post p")
    Page<Post> getAllPostsPaged(Pageable pageable);

    @Query("from Post p where p.userId in (?1)")
    List<Post> getAllPostsByUserIds(Collection<Integer> userIds);

    @Modifying
    @Query("delete Post p where p.userId = ?1")
    void deletePostsByUserId(Integer userId);

    List<Post> findAllByTitle(String title);

    Post findByTitle(String title);
    //    Optional<Post> findByTitle(String title);

    Optional<Post> findById(Integer id);
    Post findByTitleIgnoreCase(String title);
    Post findByTitleIgnoreCaseAndUserId(String title, Integer userId);
    List<Post> findAllByTitleStartingWith(String title);
    List<Post> findAllByTitleEndingWith(String title);
    List<IPostDTO> findAllByUserIdLessThanEqual(Integer userId);
    List<PostDTO> findAllByUserIdGreaterThanEqual(Integer userId);
//    @Query("select new uz.pdp.springboot.jparepository.PostDTO(p.id, p.title, p.body) from Post p")  // JPQL
//    @Query(nativeQuery = true, name = "Post.find.all.by.projection")  // native query
    @Query(nativeQuery = true, value = "select id, title from post")
    List<Object[]> findAllByClassProjection();
}
