package me.mrfunny.blua;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

public final class Blua extends JavaPlugin  {

    private static final MainListener registeredListener = new MainListener();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Globals globals = JsePlatform.standardGlobals();
        File scripts = new File(Bukkit.getWorldContainer(), "scripts");
        if (!scripts.exists()) scripts.mkdirs();
        Reflections reflections = new Reflections("org.bukkit.event");

        Set<Class<?>> events = reflections.get(Scanners.SubTypes.of(Event.class).asClass());
        for(File file : scripts.listFiles()) {
            LuaValue chunk = globals.loadfile(file.getAbsolutePath());
            LuaValue preListeners = chunk.call();
            if(!preListeners.istable()) {
                getLogger().warning("Failed to load " + file.getName() + ". File forgets to return a table.");
                continue;
            }
            LuaTable listeners = preListeners.checktable();
            for(LuaValue listenerKey : listeners.keys()) {
                String eventName = listenerKey.toString().replace("on", "");
                LuaValue preListener = listeners.get(listenerKey);
                if(!preListener.isfunction()) {
                    Bukkit.getLogger().warning(listenerKey.toString() + " is not a function.");
                    continue;
                }
                LuaFunction listener = preListener.checkfunction();
                for(Class<?> event : events) {
                    if(event.getSimpleName().equals(eventName)) {
                        Bukkit.getLogger().info("Registering " + eventName + " listener for " + file.getName());
                        try {
                            HandlerList handlerList = (HandlerList) event.getMethod("getHandlerList").invoke(null);
                            handlerList.register(new BluaListener(registeredListener, listener, EventPriority.NORMAL, this, true));
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
