package csci331.team.red.example;

import java.util.LinkedList;
import java.util.List;

/**
 * Stub for good toString, equals, and hashCode.<br>
 * Also uses getter and setters
 * 
 * @author jourmeob
 */
public class Example {

	/**
	 * The default number of marks
	 */
	public static final int SIZE_OF_MARKS = 50;

	private int number;
	private String name;
	private List<Double> marks;

	protected double secretVersion = 0.2;

	/**
	 * Creates a new {@link Example}
	 * 
	 * @param number
	 *            to be used
	 * @param name
	 *            to be used
	 */
	public Example(int number, String name) {
		this.number = number;
		this.name = name;

		marks = new LinkedList<Double>();
		for (int i = 0; i < SIZE_OF_MARKS; i++) {
			marks.add(Math.random());
		}
	}

	/**
	 * Creates a new {@link Example}
	 * 
	 * @param number
	 *            to be used
	 * @param name
	 *            to be used
	 * @param marks
	 *            to be used
	 */
	public Example(int number, String name, List<Double> marks) {
		this.number = number;
		this.name = name;
		this.marks = marks;
	}

	/**
	 * @return number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            to be changed to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return List of double
	 */
	public List<Double> getMarks() {
		return marks;
	}

	/**
	 * @return a string representation of this object
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ");
		sb.append(name);
		sb.append(" number: ");
		sb.append(number);
		sb.append(" marks: ");
		for (Double d : marks) {
			sb.append(d);
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * @param obj
	 * @return true iff obj is not null, obj is an instance of {@link Example},
	 *         and all fields in obj are equal to the fields in this. Does NOT
	 *         consider the values in {@link Example#getMarks())
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Example) {

			Example other = (Example) obj;
			if ((this.name == null) || (!name.equals(other.name))) {
				return false;
			} else if (this.number != other.number) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * @return a unique (ish) hash code of this object. Does NOT consider the
	 *         values in {@link Example#getMarks())
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + number;
		hash = hash * 31 + (name == null ? 0 : name.hashCode());
		return hash;
	}
}
