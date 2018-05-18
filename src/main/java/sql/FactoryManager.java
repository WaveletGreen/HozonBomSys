package sql;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class FactoryManager {
	static Reader reader = null;
	public static SqlSessionFactory factory = null;
	/**where the configuration file path*/
	public static String resource = "mybatis-config.xml";
	static {
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getInstance() {
		if (factory == null) {
			synchronized (reader) {
				if (factory == null)
					try {
						reader = Resources.getResourceAsReader(resource);
						SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
						factory = builder.build(reader);
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		return factory;
	}
}
