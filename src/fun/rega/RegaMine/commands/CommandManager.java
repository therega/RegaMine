package fun.rega.WaterFarmer.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fun.rega.WaterFarmer.Utils;

public class CommandManager implements CommandExecutor {

private List<Sub> commands;
    
    public CommandManager() {
        (this.commands = new ArrayList<Sub>()).add(new CreateCommand());
        this.commands.add(new RemoveCommand());
        this.commands.add(new SalaryCommand());
        this.commands.add(new ResetAllCommand());
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (args.length == 0) {
                if (!this.getAllowCommands(player).isEmpty()) {
                    Utils.sendMessage(player, Utils.getMessage("commandmanager.title_help"));
                    for (final Sub c : this.getAllowCommands(player)) {
                        Utils.sendMessage(player, c.description());
                    }
                }
                else {
                    Utils.sendMessage(player, Utils.getMessage("commandmanager.no_commands"));
                }
                return true;
            }
            Sub c = this.getCommand(args[0]);
            if (c != null) {
                if (c.permission() != null && !player.hasPermission(c.permission())) {
                    Utils.sendMessage(player, Utils.getMessage("commandmanager.no_permission"));
                    return true;
                }
                try {
                    final boolean exec = c.execute(player, args);
                    if (!exec) {
                        Utils.sendMessage(player, c.description());
                    }
                }
                catch (Exception var10) {
                    var10.printStackTrace();
                    Utils.sendMessage(player, Utils.getMessage("commandmanager.error"));
                }
            }
            else {
                Utils.sendMessage(player, Utils.getMessage("commandmanager.command_not_found"));
            }
        }
        return true;
    }
    
    public List<Sub> getAllowCommands(final Player player) {
        final List list = new ArrayList();
        for (final Sub c : this.commands) {
            if (c.permission() != null) {
                if (!player.hasPermission(c.permission())) {
                    continue;
                }
                list.add(c);
            }
            else {
                list.add(c);
            }
        }
        return (List<Sub>)list;
    }
    
    public Sub getCommand(final String cmd) {
        for (final Sub c : this.commands) {
            if (c.command().equalsIgnoreCase(cmd)) {
                return c;
            }
            if (c.aliases() == null) {
                continue;
            }
            String[] var5;
            for (int var4 = (var5 = c.aliases()).length, var6 = 0; var6 < var4; ++var6) {
                final String aliases = var5[var6];
                if (aliases.equalsIgnoreCase(cmd)) {
                    return c;
                }
            }
        }
        return null;
    }
}

