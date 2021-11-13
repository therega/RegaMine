package fun.rega.RegaMine;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    private static JavaPlugin instance = JavaPlugin.getProvidingPlugin(Config.class);

    public static FileConfiguration getFile(String fileName) {
        File file = new File(instance.getDataFolder(), fileName);

        if(instance.getResource(fileName) == null)
            return save(YamlConfiguration.loadConfiguration(file), fileName);

        if(!file.exists()) {
            instance.saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration save(FileConfiguration config, String fileName) {
        try {
            config.save(new File(instance.getDataFolder(), fileName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}
