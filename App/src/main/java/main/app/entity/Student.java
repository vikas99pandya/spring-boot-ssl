package main.app.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "studentdata")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="name")
    private String name;

    @Column(name="age")
    private int age;

    @Column(name="registration_number")
    private String registrationNumber;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id")
    private DevClass devClass;

}
