package sql.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
	private final static String filePath = "redis.properties";
	private Properties Properties;

	public PropertyLoader() {
		try {
			defaultLoader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean defaultLoader() throws IOException {
		Properties = new Properties();
		InputStream in = null;
		try {
			in = this.getClass().getResourceAsStream(filePath);
			Properties.load(in);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return false;
	}

	public Properties getProperties() {
		return Properties;
	}
}
