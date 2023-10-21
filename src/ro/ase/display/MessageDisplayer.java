package ro.ase.display;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class MessageDisplayer {
    private static MessageDisplayer instance;
    private Properties properties;

    private MessageDisplayer() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/ro/ase/display/prompts.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MessageDisplayer getInstance() {
        if (instance == null) {
            synchronized (MessageDisplayer.class) {
                if (instance == null) {
                    instance = new MessageDisplayer();
                }
            }
        }
        return instance;
    }

    public String getMessage(String key) {
        return properties.getProperty(key);
    }
}
