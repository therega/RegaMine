package fun.rega.WaterFarmer.mines;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import fun.rega.WaterFarmer.Main;

public class MineManager {
  private static HashMap blocks = new HashMap<>();
  
  public static void addBlock(Location location, MineBlock block) {
    if (getBlock(location) == null || getBlock(location).isCancelled()) {
      blocks.put(location, new MineBlockUpdate(block, location));
    }
    
    ((MineBlockUpdate)blocks.get(location)).runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
  }
  
  public static MineBlockUpdate getBlock(Location location) {
    return (MineBlockUpdate)blocks.get(location);
  }
  
  public static void resetAll() {
    Iterator<Map.Entry> var1 = blocks.entrySet().iterator();
    
    while (var1.hasNext()) {
      Map.Entry block = var1.next();
      ((MineBlockUpdate)blocks.get(block.getKey())).reset();
    } 
  }
}
