package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class teamSizeCMD {
    private final BasicTeams plugin;

    public teamSizeCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Please pick what to change the limit to." + ChatColor.WHITE);
            return;
        }

        int newLimit;
        try {
            newLimit = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.YELLOW + "Please enter a number." + ChatColor.WHITE);
            return;
        }

        if (newLimit == plugin.teamLimitget()) {
            sender.sendMessage(ChatColor.YELLOW + "The limit is already set to " + plugin.teamLimitget() + ChatColor.WHITE);
            return;
        }

        plugin.teamLimitset(newLimit);
        sender.sendMessage(ChatColor.GREEN + "Team limit updated to " + plugin.teamLimitget() + ChatColor.WHITE);
    }
}
