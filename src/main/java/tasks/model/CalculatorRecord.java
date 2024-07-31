package tasks.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import tasks.exception.InvalidOperationException;
import tasks.misc.OperationForm;

/**
 * This class contains all the information to record all transactions
 * 
 * @author emiliano.veloso
 *
 */
@Entity
public class CalculatorRecord {

	public static final int ACTIVE = 1;
	public static final int INACTIVE = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Operation operation;

	@ManyToOne
	private CalculatorUser user;
	private Double amount;
	private Double balance;
	private String response;
	private Date date;
	private int status;

	/**
	 * Default constructor
	 */
	public CalculatorRecord() {

	}

	/**
	 * Constructor to build a new object with all attributes and validations
	 * 
	 * @param operation
	 * @param user
	 * @param operationForm
	 * @throws InvalidOperationException
	 */
	public CalculatorRecord(Operation operation, CalculatorUser user, OperationForm operationForm)
			throws InvalidOperationException {
		this.operation = operation;
		this.date = new Date();
		this.user = user;
		this.balance = user.getBalance();
		if (user.getBalance() - operation.getCost() < 0) {
			throw new InvalidOperationException("Cost exceeds balance");
		}
		this.balance = balance - operation.getCost();
		this.status = ACTIVE;
		this.setResponse(calculate(operationForm));
	}

	/**
	 * Performs calculations with amounts given for operation selected
	 * 
	 * @param operationForm
	 * @return
	 * @throws InvalidOperationException
	 */
	public String calculate(OperationForm operationForm) throws InvalidOperationException {
		double result;
		switch (operationForm.getOperationId()) {
		case Operation.ADDITION:
			return String.valueOf(operationForm.getAmount1() + operationForm.getAmount2());
		case Operation.SUBSTRACTION:
			return String.valueOf(operationForm.getAmount1() - operationForm.getAmount2());
		case Operation.MULTIPLICATION:
			return String.valueOf(operationForm.getAmount1() * operationForm.getAmount2());
		case Operation.DIVISION:
			if (operationForm.getAmount2() == 0) {
				throw new InvalidOperationException("Cannot divide by zero");
			}
			result = operationForm.getAmount1() / operationForm.getAmount2();
			return String.format("%.2f", result);
		case Operation.SQUAREROOT:
			if (operationForm.getAmount1() < 0) {
				throw new InvalidOperationException("Cannot perform square root for negative numbers");
			}
			return String.format("%.0f", Math.sqrt(operationForm.getAmount1()));
		case Operation.RANDOM:
			return operationForm.getRandom();

		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public CalculatorUser getUser() {
		return user;
	}

	public void setUser(CalculatorUser user) {
		this.user = user;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
