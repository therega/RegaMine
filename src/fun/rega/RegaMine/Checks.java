package fun.rega.WaterFarmer;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Checks {
	public static void check(final Player player) {
        if (!player.hasPermission("umine.checks")) {
            if ((player.getAllowFlight() || player.isFlying()) && Utils.getConfig().getBoolean("checks.fly.enable")) {
                Utils.sendMessage(player, Utils.getConfig().getString("checks.fly.message"));
                player.setAllowFlight(false);
                player.setFlying(false);
            }
            if (Utils.getConfig().getBoolean("checks.gamemode.enable")) {
                Utils.getConfig().getStringList("checks.gamemode.modes").forEach(x -> {
                    if (player.getGameMode() == GameMode.valueOf(x.toUpperCase())) {
                        player.setGameMode(GameMode.valueOf(Utils.getConfig().getString("checks.gamemode.setmode").toUpperCase()));
                        Utils.sendMessage(player, Utils.getConfig().getString("checks.gamemode.message"));
                    }
                });
            }
        }
    }
}
