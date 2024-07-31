package tasks.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tasks.exception.InvalidOperationException;
import tasks.misc.OperationForm;
import tasks.model.CalculatorRecord;
import tasks.model.CalculatorUser;
import tasks.model.Operation;
import tasks.repository.OperationRepository;
import tasks.repository.RecordRepository;
import tasks.repository.UserRepository;

/**
 * Service class to perform all validations and external invocation. Business
 * logic to connect to repository
 * 
 * @author emiliano.veloso
 *
 */
@Service
public class RecordService {

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OperationRepository operationRepository;

	@Autowired
	RestTemplate restTemplate;

	private static final String RANDOM_URL = "https://www.random.org/strings/?num=10&len=8&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new";

	public CalculatorRecord save(CalculatorRecord record) {
		return recordRepository.save(record);
	}

	public Optional<CalculatorRecord> findByRecordId(Integer userId) {
		return recordRepository.findById(userId);
	}

	public Iterable<CalculatorRecord> findAll() {
		return recordRepository.findAll();
	}

	/**
	 * Searches for all records for current user and operation if selected. Returns
	 * a paginated map
	 * 
	 * @param userId
	 * @param pageable
	 * @param operationId
	 * @return
	 * @throws InvalidOperationException
	 */
	public Map<String, Object> findAllByUserAndOperation(Integer userId, Pageable pageable, Integer operationId)
			throws InvalidOperationException {
		Optional<CalculatorUser> userOptional = null;
		userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			return new HashMap<>();
		}
		Page<CalculatorRecord> records = null;
		if (operationId == 0) {
			records = recordRepository.findByUserAndStatus(userOptional.get(), CalculatorRecord.ACTIVE, pageable);
		} else {
			Operation operationOptional = operationRepository.findById(operationId).orElseThrow(
					() -> new InvalidOperationException("Operation with ID " + operationId + " not found."));
			records = recordRepository.findByUserAndStatusAndOperation(userOptional.get(), CalculatorRecord.ACTIVE,
					operationOptional, pageable);

		}
		return buildMap(records);
	}

	/**
	 * Transactional method to save user balance and record
	 * 
	 * @param operationForm
	 * @param user
	 * @return
	 * @throws InvalidOperationException
	 */
	@Transactional
	public CalculatorRecord save(OperationForm operationForm, CalculatorUser user) throws InvalidOperationException {
		Operation operation = fetchOperationById(operationForm.getOperationId());
		handleRandomOperationIfNeeded(operationForm, operation);
		validateUserBalance(user, operation.getCost());
		CalculatorRecord record = new CalculatorRecord(operation, user, operationForm);
		user.setBalance(user.getBalance() - operation.getCost());
		return saveRecordAndUser(record, user);
	}

	private Operation fetchOperationById(int operationId) throws InvalidOperationException {
		return operationRepository.findById(operationId)
				.orElseThrow(() -> new InvalidOperationException("Invalid operation with ID: " + operationId));
	}

	private void handleRandomOperationIfNeeded(OperationForm operationForm, Operation operation)
			throws InvalidOperationException {
		if (operation.getId() == Operation.RANDOM) {
			String randomString = fetchRandomString();
			operationForm.setRandom(randomString);
		}
	}

	/**
	 * Fetches 8 characters from random string invoking external API
	 * 
	 * @return
	 * @throws InvalidOperationException
	 */
	private String fetchRandomString() throws InvalidOperationException {
		ResponseEntity<String> response = restTemplate.getForEntity(RANDOM_URL, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody().substring(0, 8);
		} else {
			throw new InvalidOperationException(
					"Failed to retrieve random strings. HTTP Status: " + response.getStatusCode());
		}
	}

	/**
	 * Validates balance available to cover transaction cost
	 * 
	 * @param user
	 * @param cost
	 * @throws InvalidOperationException
	 */
	private void validateUserBalance(CalculatorUser user, double cost) throws InvalidOperationException {
		if (user.getBalance() < cost) {
			throw new InvalidOperationException(
					"Insufficient balance. Current balance: " + user.getBalance() + ", Cost: " + cost);
		}
	}

	private CalculatorRecord saveRecordAndUser(CalculatorRecord record, CalculatorUser user) {
		userRepository.save(user);
		return recordRepository.save(record);
	}

	/**
	 * Inactivate record by changing the status to inactive and updates user balance
	 * 
	 * @param calculatorRecord
	 * @return
	 */
	public CalculatorUser delete(CalculatorRecord calculatorRecord) {
		calculatorRecord.setStatus(CalculatorRecord.INACTIVE);
		recordRepository.save(calculatorRecord);
		CalculatorUser user = calculatorRecord.getUser();
		user.setBalance(user.getBalance() + calculatorRecord.getOperation().getCost());
		return userRepository.save(user);
	}

	private Map<String, Object> buildMap(Page<CalculatorRecord> records) {

		Map<String, Object> response = new HashMap<>();
		response.put("content", records.getContent());
		response.put("totalPages", records.getTotalPages());
		response.put("currentPage", records.getNumber());
		response.put("totalElements", records.getTotalElements());
		return response;
	}

}
