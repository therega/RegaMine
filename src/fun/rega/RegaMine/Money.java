package fun.rega.WaterFarmer;

import java.text.DecimalFormat;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import fun.rega.WaterFarmer.exceptions.VaultNotFound;
import net.milkbowl.vault.economy.Economy;

public class Money {
	private static Economy economy;
	   private static HashMap<Player, Double> salary = new HashMap();

	   public Money() throws VaultNotFound {
	      RegisteredServiceProvider<Economy> provider = Bukkit.getServicesManager().getRegistration(Economy.class);
	      if (provider != null) {
	         economy = (Economy)provider.getProvider();
	      } else {
	         throw new VaultNotFound();
	      }
	   }

	   public static void addMoney(Player player, double money) {
	      economy.depositPlayer(player, money);
	   }

	   public static void addSalary(Player player, double money) {
	      if (salary.get(player) == null) {
	         salary.put(player, 0.0D);
	      }

	      salary.put(player, (Double)salary.get(player) + money);
	   }

	   public static void takeSalary(Player player, double money) {
	      if (salary.get(player) == null) {
	         salary.put(player, 0.0D);
	      }

	      salary.put(player, (Double)salary.get(player) - money);
	      if (getSalary(player) < 0.0D) {
	         salary.put(player, 0.0D);
	      }

	   }

	   public static void setSalary(Player player, double money) {
	      salary.put(player, money);
	   }

	   public static double getSalary(Player player) {
	      if (salary.get(player) == null) {
	         salary.put(player, 0.0D);
	      }

	      return (Double)salary.get(player);
	   }

	   public static String getSalaryFormat(Player player) {
	      return (new DecimalFormat("#0.00")).format(getSalary(player));
	   }

	   public static String getSalaryFormat(double salary) {
	      return (new DecimalFormat("#0.00")).format(salary);
	   }
	}
