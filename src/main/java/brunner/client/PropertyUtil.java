package brunner.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyUtil {
	
    static Properties prop = null;

    public static String get(String key) throws IOException{
        if(prop == null) {           
            prop = new Properties();
            InputStream input = null;

            try {
                input = PropertyUtil.class.getClassLoader().getResourceAsStream( "application.properties");
                 prop.load(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                if(input != null)
                    try {
                        input.close();
                    } catch (IOException e) {
                        throw e;
                    }
            }
        }
        return (String) prop.get(key);
    }

    public static void set(String key, String value) throws IOException{
        prop = new Properties();
        InputStream input = null;

        try {
            input = PropertyUtil.class.getClassLoader().getResourceAsStream( "application.properties");
            prop.load(input);
            prop.setProperty(key, value);

            File file = new File(PropertyUtil.class.getClassLoader().getResource("application.properties").getFile());

            OutputStream out = new FileOutputStream(file);
            prop.store(out, "The last value of LookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if(input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    throw e;
                }
        }
    }
}