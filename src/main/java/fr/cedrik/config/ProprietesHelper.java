package fr.cedrik.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ProprietesHelper {

    private final Properties prop;
    private final File file;
    private final String pluginName;

    public ProprietesHelper(File file, String pluginName) {
        prop = new Properties();
        this.file = file;
        this.pluginName = pluginName;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("[" + pluginName + "] a rencontre une IOException lors de la creation de la configuration");
                e.printStackTrace();
            }
        }
    }

    public static boolean hasSettingsFile(File f) {
        return f.exists();
    }

    public static void deleteSettingsFile(File f) {
        if (f.exists()) {
            f.delete();
        }
    }

    public void loadConfig() {
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            prop.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("[" + pluginName + "] echec du chargement de la configuration");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[" + pluginName + "] a rencontre une IOException lors du chargement de la configuration");
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            prop.store(fos, "");
            fos.close();
        } catch (IOException e) {
            System.out.println("[" + pluginName + "] a rencontre une IOException lors de la sauvegarde de la configuration");
        }
    }

    public void setProperty(String key, String value) {
        prop.setProperty(key, value);
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
