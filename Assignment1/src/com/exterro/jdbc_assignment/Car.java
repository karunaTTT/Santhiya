package com.exterro.jdbc_assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/*Car - It has the entire functionality of each and every methods used in  */

public class Car {

	int FK_Uid = 0, FK_Tid = 0;

	CarPool cp = new CarPool();
	// Object of CarBean
	CarBean carbean = new CarBean();

	// Object of DataBase Connection
	Db_Connection db = new Db_Connection();

	private Connection con = null;
	private Statement stmt = null;
	//private ResultSet rs = null;
	private PreparedStatement ps = null;

	public void newUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your UserName");

		String user = sc.next();
		System.out.println("Enter your Password");

		int flag = 0;
		String pwd = sc.next();
		carbean.setUserName(user);
		carbean.setPassword(pwd);

		try {
			con = db.getConnection();
			String str = "select userName,password from carUser";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				String a1 = rs.getString("userName");

				if (a1.equalsIgnoreCase(carbean.getUserName())) {
					System.out.println("UserName already Exist");
					flag = 1;
					break;
				}

			}
			stmt = null;
			if (flag == 0) {
				String str1 = "insert into carUser values('" + carbean.getUserName() + "','" + carbean.getPassword()
						+ "')";
				stmt = con.createStatement();
				stmt.executeUpdate(str1);
				System.out.println("Registered Successfully");
			}

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	public String registerdUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your UserName");
		String user = sc.next();
		System.out.println("Enter your Password");
		String pwd = sc.next();
		String flag = "";
		try {
			con = db.getConnection();
			String str = "select userName,password from carUser";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				String un = rs.getString("userName");
				String p = rs.getString("password");
				if (user.equalsIgnoreCase(un) && pwd.equals(p)) {
					flag = un;
					break;
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		if (flag.equals(user)) {
			return flag;
		}
		return "false";

	}

	public void requestRide(String regUser) {
		System.out.println("Hi  " + regUser);

		String travelStart = "";
		int start1 = 0;
		int startFlag = 0, destFlag = 0, timeFlag = 0;
		// Foreign Keys to insert into table
		int hourflag = 0, minflag1 = 0, minflag2 = 0;
		try {
			con = db.getConnection();
			String str1 = "select u_id,userName from carUser";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str1);
			while (rs.next()) {
				String ss = rs.getString("userName");
				if (ss.equalsIgnoreCase(regUser)) {
					FK_Uid = rs.getInt("u_id");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		// System.out.println(FK_Uid+"UID ####");

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your pick up location");
		String start = sc.next();
		carbean.setStart(start);

		try {

			con = db.getConnection();
			String str = "select T_id,start from Admin where status='Available'";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				String st = rs.getString("start");
				FK_Tid = rs.getInt("T_id");
				if (st.equalsIgnoreCase(carbean.getStart())) {

					startFlag = 1;
					break;
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		if (startFlag == 0) {
			System.out.println("This location is not available...Sorry pickup");
			return;
		}

		System.out.println("Enter your Destination");
		String dest = sc.next();
		carbean.setDesignation(dest);

		try {

			con = db.getConnection();
			String str = "select dest from Admin where status='Available' and start='" + carbean.getStart() + "'";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				String dt1 = rs.getString("dest");

				if (dt1.equalsIgnoreCase(carbean.getDesignation())) {

					destFlag = 1;
					break;
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		if (destFlag == 0) {
			System.out.println("This Destination is not available...Sorry");
			return;
		}

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		carbean.setDate(date);

		System.out.println("Enter Your Timing in HH:MM <- this Format");

		Date time = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String ut = sc.next(); // sdf.format(time);
		carbean.setTime(ut);

		// TIME CHECK

		try {

			con = db.getConnection();
			String str = "select S_time,E_time from Admin where start='" + carbean.getStart() + "' and dest='"
					+ carbean.getDesignation() + "'";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				String st = rs.getString("S_time");
				String et = rs.getString("E_time");
				String a[] = et.split(":");
				String b[] = ut.split(":");

				start1 = Integer.parseInt(st);
				int et_hr = Integer.parseInt(a[0]);// et_0
				int et_m = Integer.parseInt(a[1]);// et_1
				int ut_hr = Integer.parseInt(b[0]);// ut_0
				int ut_m = Integer.parseInt(b[1]);// ut_1

				if ((ut_hr >= start1 && ut_hr <= et_hr)) {
					// System.out.println("hour ride.....");
					hourflag = 1;
				} // else

				if (hourflag == 1 && ut_m >= 00 && ut_m <= 60) {
					minflag1 = 1;
					if (minflag1 == 1 && ut_m <= et_m) {
						minflag2 = 1;
						// System.out.println("ride");
						// return;
					}
					// System.out.println(" ride 2");
				}

				if (hourflag == 1 && (minflag1 == 1 && minflag2 == 1)) {
					timeFlag = 1;
					// System.out.println(" ride.....");
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		if (timeFlag == 0) {

			System.out.println("This timing is not available...");
			return;
		}

		// end of time check

		if (startFlag == 1 && destFlag == 1 && timeFlag == 1) {

			try {
				con = db.getConnection();
				String str = "insert into booking (uid,tid,status,date,time) values(?,?,?,CONVERT(varchar, ?, 23),?)";
				ps = con.prepareStatement(str);

				ps.setInt(1, FK_Uid);
				ps.setInt(2, FK_Tid);
				ps.setString(3, "Ongoing");
				ps.setString(4, carbean.getDate());
				ps.setString(5, carbean.getTime());
				ps.executeUpdate();

				System.out.println("Thank you for your booking!!!");
			} catch (Exception e) {
				System.out.println(e);
			}

			// insert into travel table
			try {
				con = db.getConnection();
				String str = "insert into travel (un,start,dest,dt,tt) values(?,?,?,CONVERT(varchar, ?, 23),?)";
				ps = con.prepareStatement(str);
				ps.setString(1, regUser);
				ps.setString(2, carbean.getStart());
				ps.setString(3, carbean.getDesignation());
				ps.setString(4, carbean.getDate());
				ps.setString(5, carbean.getTime());
				ps.executeUpdate();

				// System.out.println("Thank you for your booking travel inserted!!!");
			} catch (Exception e) {
				System.out.println(e);
			}

			// end of insert into travel table

			try {

				con = db.getConnection();
				String str = "update admin set status= 'Booked' where start='" + carbean.getStart() + "' and dest='"
						+ carbean.getDesignation() + "'";
				ps = con.prepareStatement(str);
				ps.executeUpdate();

				System.out.println("Your Cab has booked succesfully...!!!");

			} catch (Exception e) {

			}

		}

		System.out.println("Have a safe journey...\n Please press number 5 to complete the ride");
		int complete = sc.nextInt();
		// Time calculation

		if (complete == 5) {
			int k = start1 + 1;
			if (k == 24) {
				k = 1;
			}
			String z = Integer.toString(k);
			String updated_eTime = z.concat(":30");

			try {

				con = db.getConnection();
				String str = "update admin set status= 'Available' , S_time='" + k + "' , E_time='" + updated_eTime
						+ "' where start='" + carbean.getStart() + "' and dest='" + carbean.getDesignation() + "'";
				ps = con.prepareStatement(str);
				ps.executeUpdate();

				// System.out.println("Time Updated in admin...!!!");

			} catch (Exception e) {
				System.out.println(e);
			}

			try {

				con = db.getConnection();
				String str = "update booking set status= 'Completed' where uid='" + FK_Uid + "' and tid='" + FK_Tid
						+ "'";
				ps = con.prepareStatement(str);
				ps.executeUpdate();

				// System.out.println("Status Updated in Booking...!!!");

			} catch (Exception e) {
				System.out.println(e);
			}

		}

		/*		*/

		System.out.println("Thank for booking our cab service..");

	}// end of request ride function



	public void cancelBooking() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your UserName");
		String upUser = sc.next();
		int flag1 = 0, flag2 = 0;
		try {
			con = db.getConnection();
			String str = "select un from travel";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				String a1 = rs.getString("un");

				if (a1.equalsIgnoreCase(upUser)) {
					flag1 = 1;
					// System.out.println("Hi " + a1 + " Sorry for the Inconvience");

					String query = "delete from travel where un='" + a1 + "'";
					ps = con.prepareStatement(query);
					ps.executeUpdate();

					//System.out.println("Your Ride is cancelled.");

				} // if end

			} // result set while end

		} // try end
		catch (Exception e) {
			System.out.println(e);
		}

		try {
			con = db.getConnection();
			String str = "select uid,tid from booking";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				String a1 = rs.getString("uid");
				String a2 = rs.getString("tid");

				String uu = Integer.toString(FK_Uid);
				String tt = Integer.toString(FK_Tid);

				if (a1.equalsIgnoreCase(uu) && a2.equalsIgnoreCase(tt)) {
					flag2 = 1;
					// System.out.println("Hi " + a1 + " Sorry for the Inconvience");

					String query = "delete from booking where uid='" + a1 + "' and tid= '" + a2 + "'";
					ps = con.prepareStatement(query);
					ps.executeUpdate();

					//System.out.println("Your Booking is cancelled.");

				} // if end

			} // result set while end

		} // try end
		catch (Exception e) {
			System.out.println(e);
		}

		if (flag1 == 1 && flag2 == 1) {
			System.out.println("Your Ride is cancelled...");
		} else {
			System.out.println("Sorry Wrong User :( !!..");

		}

	}
	
	
	public void Admin() {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Your Name");
		String A_name=sc.next();
		System.out.println("Enter pickUp location");
		String start=sc.next();
		System.out.println("Enter Destination location");
		String dest=sc.next();
		System.out.println("Enter Start time");
		String s_time=sc.next();
		System.out.println("Enter End time");
		String e_time=sc.next();
		
		try {
			con=db.getConnection();
			String str = "insert into Admin (A_name,start,dest,S_time,E_time,status) values(?,?,?,?,?,?)";
			ps = con.prepareStatement(str);
			ps.setString(1, A_name);
			ps.setString(2, start);
			ps.setString(3, dest);
			ps.setString(4, s_time);
			ps.setString(5, e_time);
			ps.setString(6, "Available");
			ps.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}

}
