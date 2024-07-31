package tasks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tasks.model.CalculatorRecord;
import tasks.model.CalculatorUser;
import tasks.model.Operation;

@Repository
public interface RecordRepository extends CrudRepository<CalculatorRecord, Integer> {

	Page<CalculatorRecord> findByUser(CalculatorUser user, Pageable pageable);

	Page<CalculatorRecord> findByUserAndStatus(CalculatorUser calculatorUser, int status, Pageable pageable);

	Page<CalculatorRecord> findByUserAndStatusAndOperation(CalculatorUser calculatorUser, int active,
			Operation operationOptional, Pageable pageable);
}
