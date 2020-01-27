package com.exterro.jdbc_assignment;

import java.util.Scanner;

/*CarPool - main login program for the application 
 * It calls other functions like registration, updateRide,cancelBooking using car object */

public class CarPool {

	public static void main(String[] args) {

		fun();

	}

	public static void fun() {
		System.out.println("***WELCOME TO CAR_POOL***");

		// Object for Car class

		Scanner sc = new Scanner(System.in);
		int val;
		System.out.println(
				" Enter 1 for Admin\n Enter 2 for SignUp \n Enter 3 for Login & Booking \n Enter 4 for CancelBooking \n Enter 5 to Logout");
		val = sc.nextInt();
		Car car = new Car();
		while (val <= 5) {

			switch (val) {
			case 1:
				car.Admin();
				break;
			case 2:
				car.newUser();
				break;
			case 3:
				String user = car.registerdUser();
				if (!user.equals("false")) {

					car.requestRide(user);
				} else {
					System.out.println("Invalid UserName or Password");
				}
				break;
			case 4:
				car.cancelBooking();
				break;

			case 5:
				System.out.println("Thank You.. :)!!!");
				System.exit(0);
				break;

			}// switch End
			System.out.println("Enter your option");
			val = sc.nextInt();
		} // while End

	}
}
