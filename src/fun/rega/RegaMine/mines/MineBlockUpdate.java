package fun.rega.RegaMine.mines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import fun.rega.RegaMine.Main;
import fun.rega.RegaMine.Utils;

public class MineBlockUpdate
  extends BukkitRunnable {
  private MineBlock block;
  
  public MineBlockUpdate(MineBlock block, Location location) {
    this.block = block;
    this.location = location;
    this.time = block.getUpdate();
  }

  private Location location;
  private int time;

  public int getTime() {
    return this.time;
  }
  
  public void setTime(int time) {
    this.time = time;
  }
  
  public void run() {
    this.time--;
    if (this.time <= 0) {
      reset();
    } else {
      setBlock(this.location);
    } 
  }

  
  public void reset() {
    this.location.getBlock().setType(this.block.getBlock());
    cancel();
  }
  
  public void setBlock(final Location location) {
    Bukkit.getScheduler().runTask((Plugin)Main.getInstance(), new Runnable() {
          public void run() {
            Material replace = Material.getMaterial(Utils.getConfig().getString("setblock").toUpperCase());
            location.getBlock().setType(replace);
          }
        });
  }
}
