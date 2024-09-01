package me.T05619.basicTeams.Commands;

import me.T05619.basicTeams.BasicTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class listInvitesCMD {
    private final BasicTeams plugin;

    public listInvitesCMD(BasicTeams plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return;
        }

        List<List<Object>> teamInvites = plugin.getTeamInvites();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        StringBuilder invitesMessage = new StringBuilder(ChatColor.GREEN + "Teams you're invited to:\n" + ChatColor.WHITE);

        boolean hasInvites = false;
        for (List<Object> invite : teamInvites) {
            String teamName = (String) invite.get(0);
            String invitedPlayer = (String) invite.get(1);

            if (invitedPlayer.equals(sender.getName())) {
                Team team = scoreboard.getTeam(teamName);

                if (team != null) {
                    invitesMessage.append(ChatColor.GRAY).append(team.getPrefix()).append("\n");
                    hasInvites = true;
                }
            }
        }

        if (hasInvites) {
            sender.sendMessage(invitesMessage.toString() + ChatColor.WHITE);
        } else {
            sender.sendMessage(ChatColor.GREEN + "You have no team invites." + ChatColor.WHITE);
        }
    }
}
