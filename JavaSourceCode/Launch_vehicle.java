/**
 * @File: Launch_vehicle.java
 *
 * @Desc: This class is used to abstract the launch vehicle. The two
 *        variables in this class represent the first section in car
 *        park and the lift. The function of object of this class is
 *        to pick up cars from the lift and deliver them to the first
 *        section when it is an inbound car in the lift and the first
 *        section is empty.
 *
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz
 * @StudentID:734057
 */

public class Launch_vehicle extends Thread {
	private Section first;// the first section in car park
	private Lift lift;

	public Launch_vehicle(Lift lift, Section section) {
		this.first = section;
		this.lift = lift;
	}

	/*
	 * The Launch_vehicle's run method, move cars from the lift to the
	 * first section when conditions are satisfied.
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this.lift) {
					// wait until there is an inbound car in the lift
					while (this.lift.isEmpty() || !this.lift.isIn())
						this.lift.wait();
					synchronized (this.first) {
						// wait until the first section is empty
						while (!this.first.isEmpty())
							this.first.wait();
						// the lift goes up and pick up the car from
						// it
						sleep(Param.OPERATE_TIME);
						Car c = this.lift.goUp();
						sleep(Param.TOWING_TIME);
						this.lift.notifyAll();
						// deliver the car to the first section
						this.first.arrive(c);
						this.first.notify();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Launch_vehicle was interrupted");
			}
		}
	}
}
