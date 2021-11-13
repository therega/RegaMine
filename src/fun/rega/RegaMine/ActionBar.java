package fun.rega.WaterFarmer;

import java.lang.reflect.Constructor;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBar {
   public static void sendActionBar(Player player, String s) {
    s = ChatColor.translateAlternateColorCodes('&', s);
    try {
      sendPacket(player,
              (Objects.requireNonNull(getNMS("PacketPlayOutChat")))
                      .getConstructor(getNMS("IChatBaseComponent"), getNMS("ChatMessageType"))
                      .newInstance((Objects.requireNonNull(getNMS("IChatBaseComponent")))
                      .getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + s + "\"}"), (Objects.requireNonNull(getNMS("ChatMessageType")))
                      .getField("GAME_INFO").get(null)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendPacket(Player player, Object o) {
    try {
      Object invoke = player.getClass().getMethod("getHandle").invoke(player);
      Object value = invoke.getClass().getField("playerConnection").get(invoke);
      value.getClass().getMethod("sendPacket", getNMS("Packet")).invoke(value, o);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Class<?> getNMS(String NMS) {
    String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try {
      return Class.forName("net.minecraft.server." + version + "." + NMS);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
}
