package fun.rega.RegaMine.mines;

import org.bukkit.Location;
import org.bukkit.World;

public class MineInfo {
  private Location min;
  private Location max;
  private World world;
  
  public MineInfo(Location min, Location max, World world) {
    this.min = min;
    this.max = max;
    this.world = world;
  }

  
  public MineInfo() {}
  
  public Location getMin() {
    return this.min;
  }
  
  public Location getMax() {
    return this.max;
  }
  
  public World getWorld() {
    return this.world;
  }
  
  public void setMin(Location min) {
    this.min = min;
  }
  
  public void setMax(Location max) {
    this.max = max;
  }
  
  public void setWorld(World world) {
    this.world = world;
  }
}
