package tasks.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tasks.exception.InvalidOperationException;
import tasks.misc.OperationForm;

public class CalculatorRecordTest {

	@Test
	public void testConstructorNoBalanceAvailable() {
		Operation operation = new Operation();
		operation.setCost(200D);
		CalculatorUser user = new CalculatorUser();
		user.setBalance(100D);
		OperationForm form = new OperationForm();
		InvalidOperationException thrownException = assertThrows(InvalidOperationException.class,
				() -> new CalculatorRecord(operation, user, form), "Cost exceeds balance");

		assertTrue(thrownException.getMessage().contains("Cost exceeds balance"));
	}

	@Test
	public void testConstructorOK() {
		Operation operation = new Operation();
		operation.setCost(20D);
		CalculatorUser user = new CalculatorUser();
		user.setBalance(100D);
		OperationForm form = new OperationForm();

		try {
			assertNotNull(new CalculatorRecord(operation, user, form));
		} catch (InvalidOperationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConstructorInvalidDivision() {
		Operation operation = new Operation();
		operation.setCost(20D);
		CalculatorUser user = new CalculatorUser();
		user.setBalance(100D);
		OperationForm form = new OperationForm();
		form.setAmount2(0d);
		form.setOperationId(Operation.DIVISION);

		InvalidOperationException thrownException = assertThrows(InvalidOperationException.class,
				() -> new CalculatorRecord(operation, user, form), "Cannot divide by zero");

		assertTrue(thrownException.getMessage().contains("Cannot divide by zero"));
	}

}
