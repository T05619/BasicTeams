package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Iterator;
import java.util.List;

public class leaveCMD {
    private final BasicTeams plugin;

    public leaveCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return;
        }

        List<List<Object>> teams = plugin.getTeams();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (Iterator<List<Object>> iterator = teams.iterator(); iterator.hasNext();) {
            List<Object> teamData = iterator.next();
            String teamName = (String) teamData.get(0);
            List<String> players = (List<String>) teamData.get(1);

            if (players.contains(sender.getName())) {
                players.remove(sender.getName());

                Team team = scoreboard.getTeam(teamName);
                if (team != null) {
                    team.removeEntry(sender.getName());

                    if (team.getEntries().isEmpty()) {
                        team.unregister();
                        iterator.remove();
                    }
                }

                sender.sendMessage(ChatColor.GREEN + "You have left your team." + ChatColor.WHITE);
                break;
            }
        }
    }
}
