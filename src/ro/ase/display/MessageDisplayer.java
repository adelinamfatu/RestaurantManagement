package ro.ase.display;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * clasa Singleton care se ocupa cu aducerea unor mesaje pe baza unei chei
 * */
public final class MessageDisplayer {
    private static MessageDisplayer instance;
    private Properties properties;

    /**
     * constructorul care incarca mesajele din fisierul properties
     * */
    private MessageDisplayer() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/ro/ase/display/prompts.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda care returneaza instanta unica a clasei
     * */
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

    /**
     * metoda care cauta in properties mesajul dupa cheie
     * @param key - cheia dupa care se cauta mesajul
     * */
    public String getMessage(String key) {
        return properties.getProperty(key);
    }
}
