package fun.rega.RegaMine.commands;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.entity.Player;
import fun.rega.RegaMine.Main;
import fun.rega.RegaMine.Utils;
import fun.rega.RegaMine.mines.Mines;

public class CreateCommand extends Sub {
  public boolean execute(Player player, String[] args) {
    if (args.length < 2) {
      return false;
    }
    Selection selection = Main.getWorldEdit().getSelection(player);
    if (selection == null) {
      Utils.sendMessage(player, Utils.getMessage("create.no_selection"));
      return true;
    }  if (Mines.getMine(args[1]) != null) {
      Utils.sendMessage(player, Utils.getMessage("create.already"));
      return true;
    } 
    Mines.saveMine(args[1], selection);
    Utils.sendMessage(player, Utils.getMessage("create.complete"));
    return true;
  }


  
  public String command() {
    return "create";
  }
  
  public String permission() {
    return "regamine.command.create";
  }
  
  public String description() {
    return Utils.getMessage("create.usage");
  }
  
  public String[] aliases() {
    return new String[] { "c" };
  }
}
