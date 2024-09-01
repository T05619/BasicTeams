package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class joinCMD {
    private final BasicTeams plugin;

    public joinCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Please specify the team your trying to join." + ChatColor.WHITE);
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

        List<List<Object>> teamInvites = plugin.getTeamInvites();
        List<List<Object>> teamsList = plugin.getTeams();
        Iterator<List<Object>> iterator = teamInvites.iterator();

        while (iterator.hasNext()) {
            List<Object> invite = iterator.next();
            String teamName = (String) invite.get(0);
            String invitedPlayer = (String) invite.get(1);

            if (targetTeam.getName().equals(teamName) && sender.getName().equals(invitedPlayer)) {
                List<Object> targetTeamData = null;

                for (List<Object> teamData : teamsList) {
                    String teamNameLoop = (String) teamData.get(0);
                    if (teamNameLoop.equals(targetTeam.getName())) {
                        targetTeamData = teamData;
                        break;
                    }
                }

                @SuppressWarnings("unchecked")
                List<String> players = (List<String>) targetTeamData.get(1);
                if (players.size() == plugin.teamLimitget()) {
                    sender.sendMessage(ChatColor.YELLOW + "The team your trying to join is full! Team limit: " + plugin.teamLimitget() + ChatColor.WHITE);
                    return;
                }

                iterator.remove();

                if (targetTeamData != null) {
                    teamsList.remove(targetTeamData);

                    players.add(sender.getName());

                    List<Object> updatedTeamData = new ArrayList<>();
                    updatedTeamData.add(targetTeam.getName());
                    updatedTeamData.add(players);
                    teamsList.add(updatedTeamData);

                    targetTeam.addEntry(sender.getName());
                    sender.sendMessage(ChatColor.GREEN + "You have joined team " + targetTeam.getPrefix() + "." + ChatColor.WHITE);
                }

                break;
            }
        }
    }
}
