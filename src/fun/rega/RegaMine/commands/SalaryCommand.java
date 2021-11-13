package fun.rega.WaterFarmer.commands;

import org.bukkit.entity.Player;
import fun.rega.WaterFarmer.Money;
import fun.rega.WaterFarmer.Utils;
import fun.rega.WaterFarmer.mines.MinePlayer;

public class SalaryCommand
  extends Sub {

	public boolean execute(Player player, String[] args) {
	      double check = Money.getSalary(player);
	      if (check == 0.0D) {
	         Utils.sendMessage(player, Utils.getMessage("salary.low"));
	         return true;
	      } else {
	         double earn = Money.getSalary(player);
	         Utils.sendMessage(player, Utils.getMessage("salary.complete"));
	         Money.addMoney(player, earn);
	         Money.setSalary(player, 0.0D);
	         MinePlayer.resetOres(player);
	         return true;
	      }
	   }

	   public String command() {
	      return "salary";
	   }

	   public String permission() {
	      return "umine.command.salary";
	   }

	   public String description() {
	      return Utils.getMessage("salary.usage");
	   }

	   public String[] aliases() {
	      return null;
	   }
	}