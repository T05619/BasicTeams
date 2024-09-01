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
import java.util.stream.Collectors;

public class createCMD {
    private final BasicTeams plugin;

    private final List<String> hexColors = List.of(
            "#ff0000", "#eb5d00", "#d08500", "#b4a200", "#95b900",
            "#72cc37", "#49dd70", "#00eaa4", "#00f6d5", "#00ffff",
            "#00ffff", "#00f7d9", "#00edae", "#3ae180", "#63d34f",
            "#84c307", "#a2b000", "#bc9a00", "#d67f00", "#ed5900"
    );

    // Constructor that takes the main plugin class as a parameter
    public createCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return;
        }

        Player player = (Player) sender;

        // Find the smallest missing team number
        List<Integer> existingTeamNumbers = plugin.getTeams().stream()
                .map(teamData -> (String) teamData.get(0))
                .map(name -> name.replaceAll("\\D+", "")) // Extract numbers from team names
                .filter(numStr -> !numStr.isEmpty())
                .map(Integer::parseInt)
                .sorted()
                .toList();

        int teamNumber = 1;
        for (int num : existingTeamNumbers) {
            if (num != teamNumber) {
                break;
            }
            teamNumber++;
        }

        String newTeamName = "team" + teamNumber;

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.registerNewTeam(newTeamName);
        team.addEntry(player.getName());

        int teamColourNum = teamNumber % hexColors.size();
        team.setPrefix("§x" + hexColors.get(teamColourNum - 1).chars().mapToObj(c -> "§" + (char)c).collect(Collectors.joining()) + "[" + teamNumber + "] ");

        List<String> players = new ArrayList<>();
        players.add(player.getName());
        List<Object> teamData = new ArrayList<>();
        teamData.add(team.getName());
        teamData.add(players);

        plugin.getTeams().add(teamData);

        player.sendMessage(ChatColor.GREEN + "Team created!" + ChatColor.WHITE);
    }
}
