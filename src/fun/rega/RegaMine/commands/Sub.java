package fun.rega.RegaMine.commands;

import org.bukkit.entity.Player;
import fun.rega.RegaMine.Utils;

public abstract class Sub extends Utils {

  public abstract boolean execute(Player paramPlayer, String[] paramArrayOfString);
  
  public abstract String command();
  
  public abstract String permission();
  
  public abstract String description();
  
  public abstract String[] aliases();
}
