package main.app.repository;


import main.app.entity.DevClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<DevClass, Long> {

}

