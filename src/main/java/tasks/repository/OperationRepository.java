package tasks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tasks.model.Operation;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Integer> {

}
