package tasks.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasks.model.Operation;
import tasks.repository.OperationRepository;

@Service
public class OperationService {

	@Autowired
	OperationRepository operationRepository;

	public Iterable<Operation> findAll() {
		return operationRepository.findAll();
	}

	public Optional<Operation> findById(int id) {
		return operationRepository.findById(id);
	}

}
