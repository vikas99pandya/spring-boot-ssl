package main.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "devClass")
@Table(name = "classdata")
@Data
public class DevClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @OneToMany(
            mappedBy = "devClass",
            cascade = CascadeType.ALL
    )
    private List<Student> students;


}
