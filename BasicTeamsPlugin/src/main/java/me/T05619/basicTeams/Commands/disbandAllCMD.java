package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class disbandAllCMD {
    private final BasicTeams plugin;

    public disbandAllCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        plugin.getTeams().clear();
        plugin.getTeamInvites().clear();
        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }

        sender.sendMessage(ChatColor.GREEN + "All teams disband!" + ChatColor.WHITE);
    }
}
