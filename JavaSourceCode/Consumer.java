/**
 * @File: Consumer.java
 *
 * @Desc: This class is used to abstract the Consumer. The only one
 *        variable in it is to represent the lift. The function of
 *        this class is going to remove cars when they are ready to
 *        depart.
 *
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz 
 * @StudentID:734057
 */

public class Consumer extends Thread {
	private Lift lift;

	public Consumer(Lift l) {
		this.lift = l;
	}

	/*
	 * The Consumer's run method, remove cars when conditions are
	 * satisfied
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this.lift) {
					// wait until lift has an outbound car and is at
					// ground level
					while (this.lift.isEmpty()
						|| this.lift.getLevel() == 1
						|| this.lift.isIn())
						this.lift.wait();
					// remove the car from the lift
					this.lift.depart();
					this.lift.notifyAll();
				}
				sleep(Param.departureLapse());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Consumer was interrupted");
			}
		}
	}
}
