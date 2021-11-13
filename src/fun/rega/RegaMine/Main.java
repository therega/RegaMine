package fun.rega.RegaMine;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import fun.rega.RegaMine.commands.CommandManager;
import fun.rega.RegaMine.events.BlockListener;
import fun.rega.RegaMine.events.MoveListener;
import fun.rega.RegaMine.exceptions.VaultNotFound;
import fun.rega.RegaMine.mines.MineManager;
import fun.rega.RegaMine.mines.Mines;

public class Main extends JavaPlugin {
	private static Main instance;
	   private TitleManagerAPI tmapi = null;

	   public static Main getInstance() {
	      return instance;
	   }

	   public TitleManagerAPI getTitleManagerAPI() {
	      return this.tmapi;
	   }

	   public void onEnable() {
	      Bukkit.getConsoleSender().sendMessage("§6RegaMine made by §cTheRega");
	      instance = this;
	      Mines.loadBlocks();
	      msg();
	      this.tmapi = (TitleManagerAPI)this.getServer().getPluginManager().getPlugin("TitleManager");
	      this.getCommand("rmine").setExecutor(new CommandManager());
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
    Logger.clear("§f   §cRegaMine §8 | §fversion: §81.0");
    Logger.clear("§f §aspecial for: §eWaterStorm");
    Logger.clear("§f §eplugin made by: §bTheRega");
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
