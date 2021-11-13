package fun.rega.WaterFarmer;


import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import fun.rega.WaterFarmer.mines.MinePlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.clip.placeholderapi.*;
import java.util.*;


public class Utils
{
private static FileConfiguration config;
    
    public static FileConfiguration getConfig() {
        return (Utils.config != null) ? Utils.config : (Utils.config = Config.getFile("config.yml"));
    }
    
    public static String color(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    
    public static String getMessage(final String path) {
        return getConfig().getString("messages." + path);
    }
    
    public static void sendMessage(final Player player, final String text) {
        if (text != null) {
            String[] var5;
            for (int var4 = (var5 = text.split(";")).length, var6 = 0; var6 < var4; ++var6) {
                String line = var5[var6];
                if (line.startsWith("actionbar:")) {
                    String response = line.split("actionbar:")[1];
                    final Map<String, String> data = MinePlayer.getData(player);
                    for (final String key : data.keySet()) {
                        response = response.replace("%" + key + "%", data.get(key));
                    }
                    response = PlaceholderAPI.setPlaceholders(player, response);
                    ActionBar.sendActionBar(player, response);
                }
                else if (line.startsWith("title:")) {
                    String response = line.split("title:")[1];
                    final Map<String, String> data = MinePlayer.getData(player);
                    for (final String key : data.keySet()) {
                        response = response.replace("%" + key + "%", data.get(key));
                    }
                    response = PlaceholderAPI.setPlaceholders(player, response);
                    Title.sendTitle(player, response);
                }
                else if (!line.isEmpty()) {
                    final Map<String, String> data2 = MinePlayer.getData(player);
                    for (final String key2 : data2.keySet()) {
                        line = line.replace("%" + key2 + "%", data2.get(key2));
                    }
                    line = PlaceholderAPI.setPlaceholders(player, line);
                    player.sendMessage(color(String.valueOf(getMessage("prefix")) + line));
                }
            }
        }
    }
}
