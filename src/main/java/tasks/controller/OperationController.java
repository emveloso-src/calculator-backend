package tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tasks.misc.ErrorResponse;
import tasks.service.OperationService;

/**
 * Controller to retrieve all operations displayed
 * 
 * @author emiliano.veloso
 *
 */
@CrossOrigin(origins = "https://calculator-frontend20240731-4ffe7a2408f3.herokuapp.com/")
//@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class OperationController {

	@Autowired
	OperationService operationService;

	@GetMapping("/api/v0/operations")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(operationService.findAll());
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
