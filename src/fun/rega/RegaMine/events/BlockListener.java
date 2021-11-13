package fun.rega.RegaMine.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import fun.rega.RegaMine.Money;
import fun.rega.RegaMine.Utils;
import fun.rega.RegaMine.mines.MineBlock;
import fun.rega.RegaMine.mines.MineManager;
import fun.rega.RegaMine.mines.MinePlayer;
import fun.rega.RegaMine.mines.Mines;

public class BlockListener
  implements Listener
{
  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    if (Mines.getMine(e.getBlock().getLocation())) {
      if (MineManager.getBlock(e.getBlock().getLocation()) != null && e.getBlock().getType() == Material.getMaterial(Utils.getConfig().getString("setblock").toUpperCase())) {
        e.setCancelled(true);
      } else if (Mines.getBlock(e.getBlock().getType()) == null) {
        if (!e.getPlayer().hasPermission("regamine.breakblocks") && Utils.getConfig().getBoolean("block-break.enable")) {
          Utils.sendMessage(e.getPlayer(), Utils.getConfig().getString("block-break.message"));
          e.setCancelled(true);
        }
      
      } else {
        
        Player player = e.getPlayer();
        
        if (MinePlayer.getBackpack(player) == MinePlayer.getBackpackMax(player)) {
          Utils.sendMessage(player, Utils.getMessage("block.full"));
          e.setCancelled(true);
          
          return;
        } 
        if (Utils.getConfig().getBoolean("resoures.straight")) {
            e.getBlock().getDrops().forEach(x -> e.getPlayer().getInventory().addItem(new ItemStack[] { x }));
        }


        
        e.setDropItems(Utils.getConfig().getBoolean("resoures.drop"));
        if (!Utils.getConfig().getBoolean("exp")) {
          e.setExpToDrop(0);
        }
        
        MineBlock mineBlock = Mines.getBlock(e.getBlock().getType());
        double earn = mineBlock.getMoney() * MinePlayer.getBooster(player);
        int total = (int)Math.floor(earn);
        
        String response = Utils.getMessage("block.complete")
          .replace("%earn%", String.valueOf(total));
        
        Utils.sendMessage(player, response);
        Money.addSalary(player, total);
        MineManager.addBlock(e.getBlock().getLocation(), mineBlock);
        MinePlayer.add(player, mineBlock.getBlock().name().toLowerCase(), 1);
      } 
    }
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    if (!e.getPlayer().hasPermission("regamine.placeblocks") && Utils.getConfig().getBoolean("block-place.enable") && Mines.getMine(e.getBlock().getLocation())) {
      Utils.sendMessage(e.getPlayer(), Utils.getConfig().getString("block-place.message"));
      e.setCancelled(true);
    } 
  }
}
