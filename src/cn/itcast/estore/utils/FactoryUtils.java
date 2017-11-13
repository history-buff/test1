package cn.itcast.estore.utils;

import java.util.ResourceBundle;

public class FactoryUtils {
	public static <T> T getInstance(Class<T> c) {
		try {
			String interfaceName = c.getSimpleName();
			String instanceClassPath = ResourceBundle.getBundle("tsp")
					.getString(interfaceName);
			return (T) Class.forName(instanceClassPath).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
