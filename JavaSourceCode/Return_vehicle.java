/**
 * @File: Return_vehicle.java
 *
 * @Desc: This class is used to abstract the return vehicle, in which,
 *        it has variables to represent the last section and lift in
 *        the car park. The function of object of this class is to
 *        pick up cars from last section and deliver them to the lift
 *        when the last section has a car and the lift is empty and at
 *        level 1.
 *
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz
 * @StudentID:734057
 */

public class Return_vehicle extends Thread {
	private Section last;// the last section in the car park
	private Lift lift;

	public Return_vehicle(Section section, Lift lift) {
		this.last = section;
		this.lift = lift;
	}

	/*
	 * The Return_vehicle's run method, move cars from last section to
	 * the lift when conditions are satisfied.
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this.last) {
					// wait until the last section is not empty
					while (this.last.isEmpty())
						this.last.wait();
					synchronized (this.lift) {
						// wait until the lift is empty and at level 1
						while (!this.lift.isEmpty()
							|| this.lift.getLevel() == 0)
							this.lift.wait();
						// pick up the car from last section
						Car c = this.last.leave();
						sleep(Param.TOWING_TIME);
						this.last.notify();
						// the lift goes down
						this.lift.goDown(c);
						sleep(Param.OPERATE_TIME);
						this.lift.notifyAll();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Return_vehicle was interrupted");
			}
		}
	}
}
