package uz.pdp.springboot.jparepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPostRepository {
    private final EntityManager em;

    public CustomPostRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Post save(@NonNull Post post) {
        em.persist(post);
        return post;
    }
}
