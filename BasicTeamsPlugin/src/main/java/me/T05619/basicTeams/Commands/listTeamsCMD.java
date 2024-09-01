package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class listTeamsCMD {
    private final BasicTeams plugin;

    public listTeamsCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return;
        }

        List<List<Object>> teams = plugin.getTeams();
        if (teams.isEmpty()) {
            return;
        }
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (List<Object> teamData : teams) {
            String teamName = (String) teamData.get(0);
            List<String> players = (List<String>) teamData.get(1);

            Team team = scoreboard.getTeam(teamName);
            String prefix = team != null ? team.getPrefix() : "No Prefix Found";

            sender.sendMessage(prefix + ChatColor.WHITE + " | " + String.join(", ", players));
        }
    }
}
