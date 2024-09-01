package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class tabCompleter implements TabCompleter {

    private final BasicTeams plugin;

    public tabCompleter(BasicTeams plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> allCommands;
            if (commandSender.isOp()) {
                allCommands = Arrays.asList("create", "leave", "list", "join", "invites", "invite", "teamSize", "disband", "disbandAll", "forceJoin", "enabled");
            } else {
                allCommands = Arrays.asList("create", "leave", "list", "join", "invites", "invite");
            }
            return filterCommands(allCommands, args[0]);
        } else if (args.length == 2) {
            if (commandSender.isOp()) {
                if (args[0].equalsIgnoreCase("disband")) {
                    List<String> teamPrefixes = new ArrayList<>();
                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

                    for (List<Object> team : plugin.getTeams()) {
                        String teamName = (String) team.get(0);
                        Team scoreboardTeam = scoreboard.getTeam(teamName);

                        if (scoreboardTeam != null) {
                            String prefix = scoreboardTeam.getPrefix();
                            String Prefix = prefix.replaceAll("§.", "");
                            teamPrefixes.add(Prefix);
                        }
                    }
                    return filterCommands(teamPrefixes, args[1]);
                }
                if (args[0].equalsIgnoreCase("forceJoin")) {
                    List<String> playerNames = new ArrayList<>();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        playerNames.add(player.getName());
                    }
                    return filterCommands(playerNames, args[1]);
                }
                if (args[0].equalsIgnoreCase("enabled")) {
                    List<String> options = new ArrayList<>();
                    options.add("true");
                    options.add("false");
                    return filterCommands(options, args[1]);
                }
            }
            if (args[0].equalsIgnoreCase("join")) {
                List<String> teamPrefixes = new ArrayList<>();
                Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

                for (List<Object> team : plugin.getTeams()) {
                    String teamName = (String) team.get(0);
                    Team scoreboardTeam = scoreboard.getTeam(teamName);

                    if (scoreboardTeam != null) {
                        String prefix = scoreboardTeam.getPrefix();
                        String Prefix = prefix.replaceAll("§.", "");
                        teamPrefixes.add(Prefix);
                    }
                }
                return filterCommands(teamPrefixes, args[1]);
            }
            if (args[0].equalsIgnoreCase("invite")) {
                List<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerNames.add(player.getName());
                }
                return filterCommands(playerNames, args[1]);
            }

        } else if (args.length == 3) {
            if (commandSender.isOp()) {
                if (args[0].equalsIgnoreCase("forceJoin")) {
                    List<String> teamPrefixes = new ArrayList<>();
                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

                    for (List<Object> team : plugin.getTeams()) {
                        String teamName = (String) team.get(0);
                        Team scoreboardTeam = scoreboard.getTeam(teamName);

                        if (scoreboardTeam != null) {
                            String prefix = scoreboardTeam.getPrefix();
                            String Prefix = prefix.replaceAll("§.", "");
                            teamPrefixes.add(Prefix);
                        }
                    }
                    return filterCommands(teamPrefixes, args[1]);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<String> filterCommands(List<String> commands, String input) {
        List<String> result = new ArrayList<>();
        for (String command : commands) {
            if (command.toLowerCase().startsWith(input.toLowerCase())) {
                result.add(command);
            }
        }
        return result;
    }
}
