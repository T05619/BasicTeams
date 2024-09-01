package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class inviteCMD {
    private final BasicTeams plugin;

    public inviteCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Please specify a player to invite." + ChatColor.WHITE);
            return;
        }

        String targetPlayerName = args[1];
        sender.sendMessage(targetPlayerName);
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.YELLOW + "Player not found or is not online." + ChatColor.WHITE);
            return;
        }

        if (targetPlayer == sender) {
            sender.sendMessage(ChatColor.YELLOW + "You cannot invite yourself to your own team." + ChatColor.WHITE);
            return;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team senderTeam = scoreboard.getEntryTeam(sender.getName());
        if (senderTeam == null) {
            sender.sendMessage(ChatColor.YELLOW + "You are not part of any team." + ChatColor.WHITE);
            return;
        }

        String senderTeamName = senderTeam.getName();
        List<List<Object>> teamInvites = plugin.getTeamInvites();
        for (List<Object> invite : teamInvites) {
            String teamName = (String) invite.get(0);
            String invitedPlayer = (String) invite.get(1);

            if (teamName.equals(senderTeamName) && invitedPlayer.equals(targetPlayerName)) {
                sender.sendMessage(ChatColor.YELLOW + "This player has already been invited to your team." + ChatColor.WHITE);
                return;
            }
        }

        List<Object> newInvite = List.of(senderTeamName, targetPlayerName);
        teamInvites.add(newInvite);
        sender.sendMessage( ChatColor.GREEN + targetPlayerName + " has been invited to your team." + ChatColor.WHITE);
        targetPlayer.sendMessage(ChatColor.GREEN + "You have been invited to join " + sender.getName() + "'s team. Do /teams join " + senderTeam.getPrefix() + " to accept!" + ChatColor.WHITE);
    }
}
