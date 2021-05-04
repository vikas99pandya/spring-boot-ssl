package main.app.model;

import lombok.Data;

import java.util.List;

@Data
public class ModelClass {
    private long id;
    private String name;
    private List<ModelStudent> listStudents;
}
