package fun.rega.RegaMine.commands;

import org.bukkit.entity.Player;
import fun.rega.RegaMine.Utils;
import fun.rega.RegaMine.mines.MineManager;

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
    return "regamine.command.resetall";
  }
  
  public String description() {
    return Utils.getMessage("reset.usage");
  }
  
  public String[] aliases() {
    return new String[] { "reset" };
  }
}
