/**
 * @File: Operator.java
 *
 * @Desc: This class is used to abstract the Operator. The only one
 *        variable in it is to represent the lift. The function of
 *        this class is to inspect the lift at random time and change
 *        its level if it is empty.
 *
 * @Author: Jianyu Zhu
 * @LoginID:jianyuz
 * @StudentID:734057
 */

public class Operator extends Thread {
	private Lift lift;

	public Operator(Lift l) {
		this.lift = l;
	}

	/*
	 * The Operator's run method, check the lift and change its level
	 * when conditions are satisfied.
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this.lift) {
					// inspect the lift and change its level if it is
					// empty
					this.lift.operate();
				}
				sleep(Param.operateLapse());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Operator was interrupted");
			}
		}
	}
}
