package fun.rega.WaterFarmer.commands;

import org.bukkit.entity.Player;
import fun.rega.WaterFarmer.Utils;
import fun.rega.WaterFarmer.mines.Mines;

public class RemoveCommand extends Sub {

  public boolean execute(Player player, String[] args) {
    if (args.length < 2) {
      return false;
    }
    String mine = args[1];
    if (Mines.getMine(mine) == null) {
      Utils.sendMessage(player, Utils.getMessage("remove.not-found"));
      return true;
    } 
    Mines.removeMine(mine);
    Utils.sendMessage(player, Utils.getMessage("remove.complete"));
    return true;
  }
  
  public String command() {
    return "remove";
  }
  
  public String permission() {
    return "umine.command.remove";
  }
  
  public String description() {
    return Utils.getMessage("remove.usage");
  }
  
  public String[] aliases() {
    return null;
  }
}
