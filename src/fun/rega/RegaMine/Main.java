package fun.rega.WaterFarmer;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import fun.rega.WaterFarmer.commands.CommandManager;
import fun.rega.WaterFarmer.events.BlockListener;
import fun.rega.WaterFarmer.events.MoveListener;
import fun.rega.WaterFarmer.exceptions.VaultNotFound;
import fun.rega.WaterFarmer.mines.MineManager;
import fun.rega.WaterFarmer.mines.Mines;

public class Main extends JavaPlugin {
	private static Main instance;
	   private TitleManagerAPI tmapi = null;

	   public static Main getInstance() {
	      return instance;
	   }

	   public TitleManagerAPI getTitleManagerAPI() {
	      return this.tmapi;
	   }

	   // $FF: synthetic method
	   // $FF: bridge method
	   public void onEnable() {
	      Bukkit.getConsoleSender().sendMessage("§6WaterFarmer special for WaterStorm by TheRega");
	      instance = this;
	      Mines.loadBlocks();
	      msg();
	      this.tmapi = (TitleManagerAPI)this.getServer().getPluginManager().getPlugin("TitleManager");
	      this.getCommand("wfarmer").setExecutor(new CommandManager());
	      this.getServer().getPluginManager().registerEvents(new BlockListener(), this);
	      this.getServer().getPluginManager().registerEvents(new MoveListener(this.getConfig()), this);

	      try {
	         new Money();
	      } catch (VaultNotFound var2) {
	         var2.printStackTrace();
	      }

	   }

  
  private static void msg() {
    Logger.clear("§e-----------------------------------------------------------");
    Logger.clear("§r");
    Logger.clear("§f  §cWaterFarmer §8 | §fversion: Â§d1.0");
    Logger.clear("§f §aspecil for: §eWaterStorm");
    Logger.clear("§f plugin made by: §eTheRega");
    Logger.clear("§r");
    Logger.clear("§e-----------------------------------------------------------");
  }
  
  public void onDisable() {
    MineManager.resetAll();
  }
  
  public static WorldEditPlugin getWorldEdit() {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldEdit");
    return (plugin != null && plugin instanceof WorldEditPlugin) ? (WorldEditPlugin)plugin : null;
  }
}
