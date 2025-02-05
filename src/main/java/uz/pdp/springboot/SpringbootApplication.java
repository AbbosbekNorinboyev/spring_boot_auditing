package uz.pdp.springboot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.pdp.springboot.jparepository.Post;
import uz.pdp.springboot.jparepository.PostRepository;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootApplication {

    private final SessionUser sessionUser;

    public SpringbootApplication(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    // @Bean
    ApplicationRunner runner(PostRepository postRepository, ObjectMapper objectMapper) {
        return (args) -> {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            List<Post> posts = objectMapper.readValue(url, new TypeReference<>() {
            });
            postRepository.saveAll(posts);
        };
    }

    @Bean
    AuditorAware<Long> auditorAware() {
        Long id = sessionUser.getId();
        return () -> Optional.ofNullable(id);
    }
}
