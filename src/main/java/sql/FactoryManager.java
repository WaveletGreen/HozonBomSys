package sql;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import com.connor.hozon.bom.bomSystem.helper.PropertiesHelper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

public class FactoryManager {
    static Reader reader = null;
    public static SqlSessionFactory factory = null;
    /**
     * where the configuration file path
     */
    public static String resource = "mybatis-config-";
    public static String end = ".xml";
    public static String P = "application.properties";

    static {
        try {
            PropertiesHelper helper = new PropertiesHelper();
            Properties properties = helper.load(P);
            String env = properties.getProperty("spring.profiles.active");
            if (!checkString(env)) {
                throw new IOException(P + "文件没有没有定义spring.profiles.active属性，请定义为dev/test/prod");
            }
            reader = Resources.getResourceAsReader(resource + env + end);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            factory = builder.build(reader);
            factory.getConfiguration();
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
                        PropertiesHelper helper = new PropertiesHelper();
                        Properties properties = helper.load(P);
                        String env = properties.getProperty("spring.profiles.active");
                        if (!checkString(env)) {
                            throw new IOException(P + "文件没有没有定义spring.profiles.active属性，请定义为dev/test/prod");
                        }
                        reader = Resources.getResourceAsReader(resource + env + end);
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
