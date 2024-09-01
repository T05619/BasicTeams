package me.T05619.basicTeams;

import me.T05619.basicTeams.Commands.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class BasicTeams extends JavaPlugin implements CommandExecutor {

    private final enabledCMD enabledCMD = new enabledCMD(this);
    private final createCMD createCMD = new createCMD(this);
    private final leaveCMD leaveCMD = new leaveCMD(this);
    private final listTeamsCMD listTeamsCMD = new listTeamsCMD(this);
    private final disbandCMD disbandCMD  = new disbandCMD(this);
    private final disbandAllCMD disbandAllCMD  = new disbandAllCMD(this);
    private final inviteCMD inviteCMD = new inviteCMD(this);
    private final listInvitesCMD listInvitesCMD = new listInvitesCMD(this);
    private final joinCMD joinCMD  = new joinCMD(this);
    private final teamSizeCMD teamSizeCMD = new teamSizeCMD(this);
    private final forceJoin forceJoin = new forceJoin(this);

    private List<List<Object>> teams = new ArrayList<>();
    private List<List<Object>> teamInvites = new ArrayList<>();
    private boolean teamsEnabled;
    private int teamLimit;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("teams").setExecutor(this);
        this.getCommand("teams").setTabCompleter(new tabCompleter(this));
        this.teamsEnabled = true;
        this.teamLimit = 4;
        loadScoreboardTeams();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /teams <subcommand>");
            return true;
        }

        if (args[0].toLowerCase().equals("enabled")) {
            if (sender.isOp()) {
                enabledCMD.execute(sender, args);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Only operators can execute this command!" + ChatColor.WHITE);
                return true;
            }
        }

        if (!this.teamsEnabled) {
            sender.sendMessage( ChatColor.RED + "Teams plugin is disabled. Do '/teams enabled true' to reactivate the plugin" + ChatColor.WHITE);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                createCMD.execute(sender, args);
                break;
            case "leave":
                leaveCMD.execute(sender, args);
                break;
            case "list":
                listTeamsCMD.execute(sender, args);
                break;
            case "invite":
                inviteCMD.execute(sender, args);
                break;
            case "invites":
                listInvitesCMD.execute(sender, args);
                break;
            case "join":
                joinCMD.execute(sender, args);
                break;
            case "teamsize":
                if (sender.isOp()) {
                    teamSizeCMD.execute(sender, args);
                    break;
                } else {
                    sender.sendMessage(ChatColor.RED + "Only operators can execute this command!" + ChatColor.WHITE);
                    break;
                }
            case "disband":
                if (sender.isOp()) {
                    disbandCMD.execute(sender, args);
                    break;
                } else {
                    sender.sendMessage(ChatColor.RED + "Only operators can execute this command!" + ChatColor.WHITE);
                    break;
                }
            case "disbandall":
                if (sender.isOp()) {
                    disbandAllCMD.execute(sender, args);
                    break;
                } else {
                    sender.sendMessage(ChatColor.RED + "Only operators can execute this command!" + ChatColor.WHITE);
                    break;
                }
            case "forcejoin":
                if (sender.isOp()) {
                    forceJoin.execute(sender, args);
                    break;
                } else {
                    sender.sendMessage(ChatColor.RED + "Only operators can execute this command!" + ChatColor.WHITE);
                    break;
                }
            default:
                sender.sendMessage(ChatColor.RED + "Command not recognised." + ChatColor.WHITE);
        }
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadScoreboardTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (Team team : scoreboard.getTeams()) {
            List<String> players = new ArrayList<>();

            for (String entry : team.getEntries()) {
                players.add(entry);
            }

            List<Object> teamData = new ArrayList<>();
            teamData.add(team.getName());
            teamData.add(players);
            teams.add(teamData);
        }
    }

    public List<List<Object>> getTeams() {
        return teams;
    }

    public List<List<Object>> getTeamInvites() {
        return teamInvites;
    }

    public boolean teamsEnabledget() {
        return teamsEnabled;
    }

    public void teamsEnabledset(boolean option) {
        this.teamsEnabled = option;
    }

    public int teamLimitget() {
        return teamLimit;
    }

    public void teamLimitset(int limit) {
        this.teamLimit = limit;
    }
}
