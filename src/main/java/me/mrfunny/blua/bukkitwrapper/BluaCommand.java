package me.mrfunny.blua.bukkitwrapper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class BluaCommand extends BukkitCommand {

    private final LuaFunction callable;

    public BluaCommand(String permission, String name, String description, String usageMessage, List<String> aliases, LuaFunction callable) {
        super(name, description, usageMessage, aliases);
        this.callable = callable;
        setName(name);
        setPermission(permission);
        setAliases(aliases);
        setUsage(usageMessage);
        setDescription(description);
        setPermissionMessage(ChatColor.RED + "You do not have permission to use this command.");
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());
            commandMap.register(name, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        LuaValue result = callable.call(CoerceJavaToLua.coerce(sender), CoerceJavaToLua.coerce(args));
        if(result.isboolean()) {
            return result.toboolean();
        }
        return true;
    }
}
