package tasks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class to support all operations available in app
 * 
 * @author emiliano.veloso
 *
 */
@Entity
public class Operation {

	public static final int ADDITION = 1;
	public static final int SUBSTRACTION = 2;
	public static final int MULTIPLICATION = 3;
	public static final int DIVISION = 4;
	public static final int SQUAREROOT = 5;
	public static final int RANDOM = 6;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Double cost;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

}
