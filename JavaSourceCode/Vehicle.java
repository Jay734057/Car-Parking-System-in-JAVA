/**
 * @File: Vehicle.java
 *
 * @Desc: This class is used to abstract a vehicle, in which, it
 *        indicates the two sections that it is going to process, and
 *        it index as well. The function of object of this class is to
 *        pick up cars from one section and deliver them to the next
 *        one when the first section has a car and the next one is
 *        empty.
 *
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz
 * @StudentID:734057
 */

public class Vehicle extends Thread {
	private Section previous, next;// the two section the vehicle is
									// going to process
	private int index;

	public Vehicle(int i, Section section, Section section2) {
		this.index = i;
		this.previous = section;
		this.next = section2;
	}

	/*
	 * The Vehicle's run method, move cars from one section to another
	 * when conditions are satisfied.
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this.previous) {
					// wait until the previous section is not empty
					while (this.previous.isEmpty())
						this.previous.wait();
					synchronized (this.next) {
						// wait until the next section is empty
						while (!this.next.isEmpty())
							this.next.wait();
						// pick up the car from previous section
						Car c = this.previous.leave();
						sleep(Param.TOWING_TIME);
						this.previous.notify();
						// deliver the car to next section
						this.next.arrive(c);
						this.next.notify();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Vehicle was interrupted");
			}
		}
	}
}
