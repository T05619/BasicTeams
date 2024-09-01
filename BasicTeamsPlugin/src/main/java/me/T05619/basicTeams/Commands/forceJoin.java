package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class forceJoin {
    private final BasicTeams plugin;

    public forceJoin(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.YELLOW + "Please specify a team and a player." + ChatColor.WHITE);
            return;
        }
        boolean ignoreTeamLimit;
        if (args.length == 4 && args[3].equalsIgnoreCase("true")) {
            ignoreTeamLimit = true;
        } else  {
            ignoreTeamLimit = false;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team targetTeam = null;
        for (Team team : scoreboard.getTeams()) {
            if (team.getPrefix().trim().replaceAll("§.", "").equals(ChatColor.stripColor(args[2].trim()))) {
                targetTeam = team;
                break;
            }
        }
        if (targetTeam == null) {
            sender.sendMessage(ChatColor.YELLOW + "Team cannot be found." + ChatColor.WHITE);
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.YELLOW + "Player cannot be found or isn't online." + ChatColor.WHITE);
            return;
        }

        List<List<Object>> teamsList = plugin.getTeams();

        List<Object> targetTeamData = null;

        for (List<Object> teamData : teamsList) {
            String teamNameLoop = (String) teamData.get(0);
            if (teamNameLoop.equals(targetTeam.getName())) {
                targetTeamData = teamData;
                break;
            }
        }

        List<String> targetTeamPlayers;
        targetTeamPlayers = (List<String>) targetTeamData.get(1);
        if (!ignoreTeamLimit) {
            if (targetTeamPlayers.size() == plugin.teamLimitget()) {
                sender.sendMessage(ChatColor.YELLOW + "The team is already full. Team Limit: " + plugin.teamLimitget() + ChatColor.WHITE);
                return;
            }
        }

        teamsList.remove(targetTeamData);

        targetTeamPlayers.add(targetPlayer.getName());

        List<Object> updatedTeamData = new ArrayList<>();
        updatedTeamData.add(targetTeam.getName());
        updatedTeamData.add(targetTeamPlayers);
        teamsList.add(updatedTeamData);

        targetTeam.addEntry(targetPlayer.getName());
        sender.sendMessage(ChatColor.GREEN + targetPlayer.getName() + " has joined team " + targetTeam.getPrefix() + "." + ChatColor.WHITE);
    }
}
