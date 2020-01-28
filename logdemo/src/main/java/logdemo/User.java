package logdemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class User{
	 private static Logger logger =LogManager.getLogger(User.class);

	public void hello() {
		logger.info("inside hello method");
	}
}
