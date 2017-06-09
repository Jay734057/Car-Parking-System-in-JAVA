/**
 * @File: Section.java
 *
 * @Desc: This class is used to represent the section. The variables
 *        in this class indicates its index and status(empty or not).
 *        The arrive method will be called when a car is delivered to
 *        this section, while the leave method is used to remove the
 *        car in this section.
 * 
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz
 * @StudentID:734057
 */

public class Section {
	private int index;
	private boolean isEmpty;// indicate the status of section(empty or
							// not)
	private Car car;

	public Section(int i) {
		this.index = i + 1;
		this.isEmpty = true;
		this.car = null;
	}

	/*
	 * The isEmpty method, return the status of the section(empty or
	 * not)
	 */
	public synchronized boolean isEmpty() {
		return this.isEmpty;
	}

	/*
	 * The arrive method, change the status of the section when a car
	 * is put in this section
	 */
	public synchronized void arrive(Car car)
		throws InterruptedException {
		this.car = car;
		this.isEmpty = false;
		System.out
			.println(this.car + " enters section " + this.index);
	}

	/*
	 * The leave method, change the status of the section when a car
	 * is removed from this section
	 */
	public synchronized Car leave() {
		System.out
			.println(this.car + " leaves section " + this.index);
		// keep the car information for the return statement
		Car car = new Car(this.car.getId());
		this.car = null;
		this.isEmpty = true;
		return car;
	}
}
