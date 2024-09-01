package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class enabledCMD {
    private final BasicTeams plugin;

    public enabledCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Please pick if you want to enable or disable the plugin." + ChatColor.WHITE);
            return;
        }

        if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
            boolean option = Boolean.parseBoolean(args[1]);
            if (option == plugin.teamsEnabledget()) {
                sender.sendMessage(ChatColor.YELLOW + "Enabled already is " + plugin.teamsEnabledget() + ChatColor.WHITE);
                return;
            }
            plugin.teamsEnabledset(option);
            sender.sendMessage( ChatColor.GREEN + "Enabled is now " + plugin.teamsEnabledget() + ChatColor.WHITE);
        } else {
            sender.sendMessage(ChatColor.YELLOW + "Enter 'true' or 'false'." + ChatColor.WHITE);
        }
    }
}
