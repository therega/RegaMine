package fun.rega.WaterFarmer.mines;

import com.sk89q.worldedit.bukkit.selections.Selection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import fun.rega.WaterFarmer.Config;
import fun.rega.WaterFarmer.Utils;

public class Mines {
  private static FileConfiguration config;
  private static List blocks = new ArrayList();
  
  public static FileConfiguration getConfig() {
    return (config != null) ? config : (config = Config.getFile("mines.yml"));
  }
  
  public static void saveConfig() {
    Config.save(getConfig(), "mines.yml");
  }
  
  public static void removeMine(String name) {
    getConfig().set(name, null);
    saveConfig();
  }
  
  public static void saveMine(String name, Selection selection) {
    getConfig().set(String.valueOf(name) + ".min.x", Double.valueOf(selection.getMinimumPoint().getX()));
    getConfig().set(String.valueOf(name) + ".min.y", Double.valueOf(selection.getMinimumPoint().getY()));
    getConfig().set(String.valueOf(name) + ".min.z", Double.valueOf(selection.getMinimumPoint().getZ()));
    getConfig().set(String.valueOf(name) + ".max.x", Double.valueOf(selection.getMaximumPoint().getX()));
    getConfig().set(String.valueOf(name) + ".max.y", Double.valueOf(selection.getMaximumPoint().getY()));
    getConfig().set(String.valueOf(name) + ".max.z", Double.valueOf(selection.getMaximumPoint().getZ()));
    getConfig().set(String.valueOf(name) + ".world", selection.getWorld().getName());
    saveConfig();
  }
  
  public static void loadBlocks() {
    Iterator<String> var1 = Utils.getConfig().getConfigurationSection("ores").getKeys(false).iterator();
    
    while (var1.hasNext()) {
      String ores = var1.next();
      MineBlock block = new MineBlock();
      block.setBlock(Material.getMaterial(ores.toUpperCase()));
      block.setMoney(Double.parseDouble(Utils.getConfig().getString("ores." + ores + ".money")));
      block.setUpdate(Utils.getConfig().getInt("ores." + ores + ".update"));
      block.setPrefix((Utils.getConfig().getString("ores." + ores + ".prefix") != null) ? Utils.getConfig().getString("ores." + ores + ".prefix") : block.getBlock().name());
      blocks.add(block);
    } 
  }

  
  public static MineBlock getBlock(Material material) {
    Iterator<MineBlock> var2 = Mines.blocks.iterator();
    
    while (var2.hasNext()) {
      MineBlock blocks = var2.next();
      if (blocks.getBlock() == material) {
        return blocks;
      }
    } 
    
    return null;
  }
  
  public static MineInfo getMine(String name) {
    if (getConfig().getString(name) == null) {
      return null;
    }
    MineInfo info = new MineInfo();
    World world = Bukkit.getWorld(getConfig().getString(String.valueOf(name) + ".world"));
    info.setMax(new Location(world, getConfig().getDouble(String.valueOf(name) + ".max.x"), getConfig().getDouble(String.valueOf(name) + ".max.y"), getConfig().getDouble(String.valueOf(name) + ".max.z")));
    info.setMin(new Location(world, getConfig().getDouble(String.valueOf(name) + ".min.x"), getConfig().getDouble(String.valueOf(name) + ".min.y"), getConfig().getDouble(String.valueOf(name) + ".min.z")));
    info.setWorld(world);
    return info;
  }
  public static boolean getMine(Location location) {
    MineInfo info;
    double x, y, z;
    Iterator<String> var2 = getConfig().getConfigurationSection("").getKeys(false).iterator();




    
    do {
      if (!var2.hasNext()) {
        return false;
      }
      
      String mine = var2.next();
      info = getMine(mine);
      x = location.getX();
      y = location.getY();
      z = location.getZ();
    } while (Math.min(info.getMin().getX(), info.getMax().getX()) > x || Math.min(info.getMin().getY(), info.getMax().getY()) > y || Math.min(info.getMin().getZ(), info.getMax().getZ()) > z || Math.max(info.getMin().getX(), info.getMax().getX()) < x || Math.max(info.getMin().getY(), info.getMax().getY()) < y || Math.max(info.getMin().getZ(), info.getMax().getZ()) < z);
    
    return true;
  }
}
