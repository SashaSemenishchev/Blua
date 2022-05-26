package me.mrfunny.blua.commands;

import me.mrfunny.blua.Blua;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadScriptsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp()) {
            sender.sendMessage(ChatColor.GREEN + "Reloading scripts...");
            Blua.instance.reloadAllScripts();
            sender.sendMessage(ChatColor.GREEN + "Scripts reloaded!");
        }
        return true;
    }
}
