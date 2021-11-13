package fun.rega.WaterFarmer.commands;

import org.bukkit.entity.Player;
import fun.rega.WaterFarmer.Utils;
import fun.rega.WaterFarmer.mines.MineManager;

public class ResetAllCommand extends Sub {
  public boolean execute(Player player, String[] args) {
    MineManager.resetAll();
    Utils.sendMessage(player, Utils.getMessage("reset.complete"));
    return true;
  }
  
  public String command() {
    return "resetall";
  }
  
  public String permission() {
    return "wmines.command.resetall";
  }
  
  public String description() {
    return Utils.getMessage("reset.usage");
  }
  
  public String[] aliases() {
    return new String[] { "reset" };
  }
}
