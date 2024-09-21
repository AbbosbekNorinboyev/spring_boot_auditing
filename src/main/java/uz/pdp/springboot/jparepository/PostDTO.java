package uz.pdp.springboot.jparepository;

import lombok.Getter;

@Getter
public class PostDTO {
    Integer id;
    String title;
    String body;

    public PostDTO(Integer id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
    public PostDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }
}
