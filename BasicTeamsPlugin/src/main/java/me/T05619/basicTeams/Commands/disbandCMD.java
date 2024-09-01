package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Iterator;
import java.util.List;

public class disbandCMD {
    private final BasicTeams plugin;

    public disbandCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Please specify a team." + ChatColor.WHITE);
            return;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team targetTeam = null;
        for (Team team : scoreboard.getTeams()) {
            if (team.getPrefix().trim().replaceAll("§.", "").equals(ChatColor.stripColor(args[1].trim()))) {
                targetTeam = team;
                break;
            }
        }
        if (targetTeam == null) {
            sender.sendMessage(ChatColor.YELLOW + "Team cannot be found." + ChatColor.WHITE);
            return;
        }

        List<List<Object>> teamsList = plugin.getTeams();
        List<Object> targetTeamData = null;
        Iterator<List<Object>> teamsIterator = teamsList.iterator();
        while (teamsIterator.hasNext()) {
            List<Object> teamData = teamsIterator.next();
            String teamNameLoop = (String) teamData.get(0);
            if (teamNameLoop.equals(targetTeam.getName())) {
                targetTeamData = teamData;
                teamsIterator.remove();
                break;
            }
        }

        List<List<Object>> teamInvites = plugin.getTeamInvites();
        Iterator<List<Object>> invitesIterator = teamInvites.iterator();
        while (invitesIterator.hasNext()) {
            List<Object> invite = invitesIterator.next();
            String teamName = (String) invite.get(0);
            if (teamName.equals(targetTeam.getName())) {
                invitesIterator.remove();
            }
        }
        teamsList.remove(targetTeamData);
        scoreboard.getTeam(targetTeam.getName()).unregister();

        sender.sendMessage(ChatColor.GREEN + "Team successfully disband." + ChatColor.WHITE);
    }
}