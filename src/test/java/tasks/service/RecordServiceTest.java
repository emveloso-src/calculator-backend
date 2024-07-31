package tasks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import tasks.model.CalculatorRecord;
import tasks.model.CalculatorUser;
import tasks.model.Operation;
import tasks.repository.RecordRepository;
import tasks.repository.UserRepository;

public class RecordServiceTest {

	@InjectMocks
	private RecordService recordService;

	@Mock
	private RecordRepository recordRepository;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDelete() {
		CalculatorUser user = new CalculatorUser();
		user.setBalance(1d);
		CalculatorRecord record = new CalculatorRecord();
		record.setUser(user);
		Operation operation = new Operation();
		operation.setCost(1d);
		record.setOperation(operation);
		when(recordRepository.findById(record.getId())).thenReturn(Optional.of(record));
		when(userRepository.save(Mockito.any())).thenReturn(user);
		doNothing().when(recordRepository).delete(record);
		CalculatorUser deletedUser = recordService.delete(record);
		assertEquals(2d, deletedUser.getBalance());
	}
}
