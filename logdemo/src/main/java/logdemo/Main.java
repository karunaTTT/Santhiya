package logdemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	private static Logger logger =LogManager.getLogger(User.class);
 public static void main(String rtas[]) {
	 System.out.println("Hiiii.....");
	 User user=new User();
	 user.hello();

 }
}
