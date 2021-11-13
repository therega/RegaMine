package fun.rega.RegaMine.events;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import fun.rega.RegaMine.Checks;
import fun.rega.RegaMine.Main;
import fun.rega.RegaMine.Utils;
import fun.rega.RegaMine.mines.MinePlayer;
import fun.rega.RegaMine.mines.Mines;

public class MoveListener
  implements Listener
{
  private FileConfiguration config;
  private Map<Player, BukkitTask> taskMap;
  
  public MoveListener(FileConfiguration config) {
    this.config = config;
    this.taskMap = Maps.newHashMap();
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    final Player player = event.getPlayer();
    
    if (Mines.getMine(player.getLocation())) {
      Checks.check(player);
    }
    if (Mines.getMine(player.getLocation()) && 
      !this.taskMap.containsKey(player)) {
      
      Main.getInstance().getTitleManagerAPI()
        .removeScoreboard(player);
      
      BukkitTask task = Main.getInstance().getServer().getScheduler().runTaskTimer(
          (Plugin)Main.getInstance(), new Runnable()
          {
            boolean stop = false;
            
            public void run() {
              if (!Bukkit.getOnlinePlayers().contains(player) || !MoveListener.this.taskMap.containsKey(player) || this.stop)
                return; 
              if (Mines.getMine(player.getLocation())) {
                
                Main.getInstance().getTitleManagerAPI()
                  .giveScoreboard(player);
                
                Main.getInstance().getTitleManagerAPI()
                  .setScoreboardTitle(player, Utils.color(MoveListener.this.config.getString("scoreboard.title")));
                
                Map<String, String> data = MinePlayer.getData(player);
                List<String> values = MoveListener.this.config.getStringList("scoreboard.values");
                
                for (int index = 0; index < values.size(); index++) {
                  String value = Utils.color(values.get(index));
                  
                  for (String key : data.keySet()) {
                    value = value.replace("%" + key + "%", data.get(key));
                  }
                  
                  value = PlaceholderAPI.setPlaceholders(player, value);
                  
                  Main.getInstance().getTitleManagerAPI()
                    .setScoreboardValueWithPlaceholders(player, index + 1, value);
                }
              
              } else {
                  Main.getInstance().getTitleManagerAPI().removeScoreboard(player);
                  Main.getInstance().getTitleManagerAPI().giveDefaultScoreboard(player);
                  MoveListener.this.taskMap.remove(player).cancel();
                  this.stop = true;
              }
          }
      }, 0L, 1L);
      this.taskMap.put(player, task);
    } 
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    
    if (this.taskMap.containsKey(player))
      ((BukkitTask)this.taskMap.remove(player)).cancel(); 
  }
}
