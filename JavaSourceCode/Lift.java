/**
 * @File: Lift.java
 *
 * @Desc: This class is used to abstract the Lift. The variables in
 *        this class indicates its index and status(level, empty or
 *        not, inbound or outbound car), and also the parking capacity
 *        for the whole car park(full or not).
 *
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz
 * @StudentID:734057
 */

public class Lift {
	private Car car;
	private boolean isEmpty;// indicate the lift is empty or not
	private int level;// indicate the level of lift
	private boolean isIn;// indicate the car in the lift is inbound or
							// outbound
	private int remaining;// indicate the number of remaining empty
							// sections in the car park

	public Lift() {
		this.isEmpty = true;
		this.level = 0;
		this.isIn = true;
		this.remaining = Param.SECTIONS;
	}

	/*
	 * The isEmpty method, return the status of the lift(empty or not)
	 */
	public boolean isEmpty() {
		return this.isEmpty;
	}

	/*
	 * The isIn method, return the status of the car in the
	 * lift(inbound or outbound)
	 */
	public boolean isIn() {
		return this.isIn;
	}

	/*
	 * The getLevel method, return the Level of the lift(0 or 1)
	 */
	public int getLevel() {
		return this.level;
	}

	/*
	 * The getRemaining method, return the number of empty sections
	 */
	public int getRemaining() {
		return this.remaining;
	}

	/*
	 * The enter method, change the status of lift when a car comes in
	 */
	public void enter(Car car) {
		this.car = car;
		this.isEmpty = false;
	}

	/*
	 * The arrive method, change the status of lift when a new car is
	 * handed to by the Producer
	 */
	public synchronized void arrive(Car c) {
		// set the isIn flag as true
		this.isIn = true;
		this.enter(c);
		System.out.println(this.car + " enters lift to go up");
		this.remaining--;
	}

	/*
	 * The goUp method, change the level of lift and remove the car in
	 * it after the Producer sending a new car
	 */
	public synchronized Car goUp() {
		this.level = 1;
		Car car = new Car(this.car.getId());
		this.car = null;
		this.isEmpty = true;
		// set the isIn flag as false to avoid incorrect operation
		// from launch vehicle
		this.isIn = false;
		System.out.println(car + " leaves the lift");
		return car;
	}

	/*
	 * The goDown method, deliver the car to the lift and change its
	 * level
	 */
	public synchronized void goDown(Car c) {
		this.isIn = false;
		this.enter(c);
		System.out.println(this.car + " enters lift to go down");
		this.level = 0;
	}

	/*
	 * The depart method, remove the outbound car in the lift when the
	 * Consumer comes
	 */
	public synchronized void depart() {
		System.out.println(this.car + " departs");
		this.car = null;
		this.isEmpty = true;
		// change the number of remaining sections
		this.remaining++;
	}

	/*
	 * The operate method, change the level of lift if it is empty and
	 * the car park is not full
	 */
	public synchronized void operate() throws InterruptedException {
		if (this.isEmpty && this.remaining > 0) {
			// lift operation time
			Thread.sleep(Param.OPERATE_TIME);
			if (this.level == 1) {
				this.level = 0;
				System.out.println("lift goes down");
			} else {
				this.level = 1;
				System.out.println("lift goes up");
			}
			this.notifyAll();
		}
	}

}
