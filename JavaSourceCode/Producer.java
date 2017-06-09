/**
 * @File: Producer.java
 *
 * @Desc: This class is used to abstract the Producer. The only one
 *        variable in it is to represent the lift. The function of
 *        this class is going to generate new cars and hand them to
 *        the life when the lift is empty and at ground level.
 *
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz
 * @StudentID:734057
 */

public class Producer extends Thread {
	private Lift lift;

	public Producer(Lift l) {
		this.lift = l;
	}

	/*
	 * The Producer's run method, generate cars and hand them to the
	 * lift when conditions are satisfied.
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this.lift) {
					// generate new car
					Car c = Car.getNewCar();
					// wait until the lift is empty and at ground level
					while (!this.lift.isEmpty()
						|| this.lift.getLevel() != 0)
						this.lift.wait();
					// hand the new car to the lift
					this.lift.arrive(c);
					this.lift.notifyAll();
				}
				sleep(Param.arrivalLapse());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Producer was interrupted");
			}
		}
	}
}
