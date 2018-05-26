package cn.hesheng.recommender.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ratings {

    private Long id;
    private Long user;
    private Long item;
    private Float rating;
    private Long timestamp;
    private String revision;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ratings{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", item='" + item + '\'' +
                ", rating='" + rating + '\'' +
                ", timestamp=" + timestamp +
                ", revision='" + revision + '\'' +
                '}';
    }
}
