package fun.rega.RegaMine.mines;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import fun.rega.RegaMine.Config;
import fun.rega.RegaMine.Money;



public class MinePlayer
{
	private static FileConfiguration config;
	   private static FileConfiguration settings;

	   public static Map<String, String> getData(Player player) {
	      Map<String, String> data = Maps.newHashMap();
	      Iterator var3 = getSettings().getConfigurationSection("ores").getKeys(false).iterator();

	      while(var3.hasNext()) {
	         String ore = (String)var3.next();
	         data.put(ore, String.valueOf(getConfig().getInt(player.getName() + "." + ore)));
	      }

	      data.put("backpackMax", String.valueOf(getBackpackMax(player)));
	      data.put("backpackCurrent", String.valueOf(getBackpack(player)));
	      double booster = getBooster(player);
	      double salary = Money.getSalary(player);
	      int total = (int)Math.ceil(salary);
	      data.put("booster", String.valueOf(booster));
	      data.put("salary", String.valueOf(total));
	      return data;
	   }

	   public static int getBackpackMax(Player player) {
	      ConfigurationSection groups = getSettings().getConfigurationSection("groups");
	      int backpackMax = groups.getInt("default.max");
	      Iterator var4 = groups.getKeys(false).iterator();

	      while(var4.hasNext()) {
	         String group = (String)var4.next();
	         int max = groups.getInt(group + ".max");
	         if (player.hasPermission("group." + group) && max > backpackMax) {
	            backpackMax = max;
	         }
	      }

	      return backpackMax;
	   }

	   public static double getBooster(Player player) {
	      ConfigurationSection groups = getSettings().getConfigurationSection("groups");
	      double availableBooster = groups.getDouble("default.booster");
	      Iterator var5 = groups.getKeys(false).iterator();

	      while(var5.hasNext()) {
	         String group = (String)var5.next();
	         double booster = groups.getDouble(group + ".booster");
	         if (player.hasPermission("group." + group) && booster > availableBooster) {
	            availableBooster = booster;
	         }
	      }

	      return availableBooster;
	   }

	   public static FileConfiguration getSettings() {
	      return settings != null ? settings : (settings = Config.getFile("config.yml"));
	   }

	   public static FileConfiguration getConfig() {
	      return config != null ? config : (config = Config.getFile("players.yml"));
	   }

	   public static void saveConfig() {
	      Config.save(getConfig(), "players.yml");
	   }

	   public static void resetOres(Player player) {
	      getConfig().set(player.getName(), (Object)null);
	      saveConfig();
	      resetBackpack(player);
	   }

	   public static int get(Player player, String ore) {
	      if (getConfig().getString(player.getName()) == null) {
	         getConfig().set(player.getName() + "." + ore, 0);
	         saveConfig();
	      }

	      return getConfig().getInt(player.getName() + "." + ore);
	   }

	   public static void set(Player player, String ore, int set) {
	      getConfig().set(player.getName() + "." + ore, set);
	      saveConfig();
	   }

	   public static void add(Player player, String ore, int add) {
	      getConfig().set(player.getName() + "." + ore, get(player, ore) + add);
	      saveConfig();
	      increaseBackpack(player);
	   }

	   public static int getBackpack(Player player) {
	      if (!getConfig().contains(player.getName() + ".backpack")) {
	         resetBackpack(player);
	      }

	      return getConfig().getInt(player.getName() + ".backpack");
	   }

	   public static void resetBackpack(Player player) {
	      getConfig().set(player.getName() + ".backpack", 0);
	   }

	   public static void increaseBackpack(Player player) {
	      getConfig().set(player.getName() + ".backpack", getBackpack(player) + 1);
	   }
	}
