package tasks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tasks.model.CalculatorUser;

@Repository
public interface UserRepository extends CrudRepository<CalculatorUser, Integer> {

	CalculatorUser findByUsername(String userName);

	Boolean existsByUsername(String username);

}
