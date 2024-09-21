package uz.pdp.springboot.jparepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostRepository postRepository;
    private final CustomPostRepository customPostRepository;

    public PostController(PostRepository postRepository, CustomPostRepository customPostRepository) {
        this.postRepository = postRepository;
        this.customPostRepository = customPostRepository;
    }

    @GetMapping()
    public Page<Post> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Sort sort = Sort.by(Sort.Order.desc("title"), Sort.Order.asc("userId"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return postRepository.findAll(pageable);
    }

    @GetMapping("/paged")
    public Page<Post> getAllPaged(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Sort sort = Sort.by(Sort.Order.desc("title"), Sort.Order.asc("user_id"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return postRepository.getAllPostsPaged(pageable);
    }

    @GetMapping("/byusers/{userIds}")
    public List<Post> getAllPaged(@PathVariable("userIds") Collection<Integer> userIds) {
        return postRepository.getAllPostsByUserIds(userIds);
    }

    @GetMapping("/{userId}")
    public List<Post> getAllByCreatorId(@PathVariable("userId") Integer userId) {
        return postRepository.getAllPostsByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deletePostsByUserId(@PathVariable("userId") Integer userId) {
        postRepository.deletePostsByUserId(userId);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sortbyid")
    public List<Post> getAllByPostsWithSortedColumns() {
//        Sort sort = Sort.by(Sort.Direction.DESC, "title")
//                .and(Sort.by(Sort.Direction.ASC, "id"));
        Sort.Order title = Sort.Order.desc("title");
        Sort.Order id = Sort.Order.asc("id");
        Sort sort = Sort.by(title, id);
        return postRepository.findAll(sort);
    }

    @PostMapping()
    public Post save(@RequestBody Post post) {
        return customPostRepository.save(post);
    }

    @GetMapping("/query/{title}")
    public Post findByTitle(@PathVariable("title") String title) {
        return postRepository.findByTitle(title);
    }

    @GetMapping("/query/by/{id}")
    public Optional<Post> findByBody(@PathVariable("id") String id) {
        return postRepository.findById(Integer.valueOf(id));
    }

    @GetMapping("/by/title/{title}")
    public Post findByTitleIgnoreCase(@PathVariable("title") String title) {
        return postRepository.findByTitleIgnoreCase(title);
    }

    @GetMapping("/by/title/and/user/id/{title}/{userId}")
    public Post findByTitleIgnoreCaseAndUserId(@PathVariable("title") String title, @PathVariable("userId") Integer userId) {
        return postRepository.findByTitleIgnoreCaseAndUserId(title, userId);
    }

    @GetMapping("/by/title/start/with/{title}")
    public List<Post> findAllByTitleStartWith(@PathVariable("title") String title) {
        return postRepository.findAllByTitleStartingWith(title);
    }

    @GetMapping("/by/title/end/with/{title}")
    public List<Post> findAllByTitleEndingWith(@PathVariable("title") String title) {
        return postRepository.findAllByTitleEndingWith(title);
    }

    @GetMapping("/interface-projection/{userId}")
    public List<IPostDTO> interfaceProjection(@PathVariable("userId") Integer userId) {
        return postRepository.findAllByUserIdLessThanEqual(userId);
    }

    @GetMapping("/class-projection/{userId}")
    public List<PostDTO> classProjection(@PathVariable("userId") Integer userId) {
        return postRepository.findAllByUserIdGreaterThanEqual(userId);
    }

    @GetMapping("/class-projection")
    public List<PostDTO> classProjection() {
//        return postRepository.findAllByClassProjection();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Object[] row : postRepository.findAllByClassProjection()) {
            postDTOS.add(new PostDTO((Integer) row[0], (String) row[1]));
        }
        return postDTOS;
    }
}
