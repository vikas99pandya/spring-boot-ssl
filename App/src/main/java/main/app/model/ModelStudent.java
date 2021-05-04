package main.app.model;

import lombok.Data;

@Data
public class ModelStudent {
    private long id;
    private String name;
    private String registrationNumber;
    private int age;
    private ModelClass modelClass;
}
