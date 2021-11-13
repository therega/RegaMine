package fun.rega.WaterFarmer;

import org.bukkit.*;

public class Logger {
	public static void clear(final String text) {
        Bukkit.getConsoleSender().sendMessage(Utils.color("§e§l(" + Main.getInstance().getName() + ") " + text));
    }
    
    public static void info(final String text) {
        Bukkit.getConsoleSender().sendMessage(Utils.color("§e§l(" + Main.getInstance().getName() + ") " + text));
    }
    
    public static void warn(final String text) {
        Bukkit.getConsoleSender().sendMessage(Utils.color("§e§l(" + Main.getInstance().getName() + ") " + text));
    }
    
    public static void error(final String text) {
        Bukkit.getConsoleSender().sendMessage(Utils.color("§e§l(" + Main.getInstance().getName() + ") " + text));
    }
}