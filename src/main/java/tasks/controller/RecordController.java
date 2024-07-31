package tasks.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tasks.exception.InvalidOperationException;
import tasks.misc.ErrorResponse;
import tasks.misc.OperationForm;
import tasks.model.CalculatorRecord;
import tasks.model.CalculatorUser;
import tasks.service.OperationService;
import tasks.service.RecordService;

/**
 * Records controller to manage records
 * 
 * @author emiliano.veloso
 *
 */
@CrossOrigin(origins = "https://calculator-frontend20240731-4ffe7a2408f3.herokuapp.com/")
@RestController
@RequestMapping("/api/v0/records")
public class RecordController {

	@Autowired
	RecordService recordService;
	@Autowired
	OperationService operationService;

	/**
	 * Exposes the http endpoint to retrieve list of records from specific user from
	 * database including pagination and filter by operation id
	 * 
	 * @param operationForm
	 * @param user
	 * @return
	 */
	@GetMapping
	public ResponseEntity<?> findByUser(@RequestParam String userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int operationId) {
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
			Map<String, Object> records = recordService.findAllByUserAndOperation(Integer.valueOf(userId), pageable,
					operationId);
			return ResponseEntity.ok(records);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Exposes the http endpoint to delete logically a specific record in database
	 * 
	 * @param operationForm
	 * @param user
	 * @return
	 */
	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam int id) {
		try {
			Optional<CalculatorRecord> record = recordService.findByRecordId(id);
			if (!record.isPresent()) {
				return new ResponseEntity<ErrorResponse>(new ErrorResponse("Record not found"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return ResponseEntity.ok(recordService.delete(record.get()));
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Exposes the http endpoint to save record from user in database
	 * 
	 * @param operationForm
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> saveRecord(@RequestBody OperationForm operationForm, @RequestHeader CalculatorUser user) {
		try {
			return ResponseEntity.ok(recordService.save(operationForm, user));
		} catch (InvalidOperationException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
