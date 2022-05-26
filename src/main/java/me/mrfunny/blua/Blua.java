package me.mrfunny.blua;

import me.mrfunny.blua.bukkitwrapper.BluaRegisteredListener;
import me.mrfunny.blua.scripts.DummyListener;
import me.mrfunny.blua.scripts.Script;
import me.mrfunny.blua.scripts.functions.CommandExecutorFunction;
import me.mrfunny.blua.scripts.functions.EventHandlerFunction;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class Blua extends JavaPlugin  {

    private static final DummyListener registeredListener = new DummyListener();
    public static Set<Class<?>> bukkitEvents;
    public static Blua instance;
    public static Set<Script> runningScripts = new HashSet<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        loadScripts();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public void addScriptEventListener(String eventName, EventPriority priority, boolean ignoreCancelled, LuaFunction executable) {
        for(Class<?> eventClass : Blua.bukkitEvents) {
            if(eventClass.getSimpleName().equalsIgnoreCase(eventName)) {
                Bukkit.getLogger().info("Registering " + eventName);
                try {
                    HandlerList handlerList = (HandlerList) eventClass.getMethod("getHandlerList").invoke(null);
                    handlerList.register(new BluaRegisteredListener(registeredListener, executable, priority, Blua.instance, ignoreCancelled));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void reloadAllScripts() {
        HandlerList.unregisterAll(this);
        runningScripts.clear();
        loadScripts();
    }

    private void loadScripts() {
        Globals globals = JsePlatform.standardGlobals();
        globals.set("EventHandler", new EventHandlerFunction());
        globals.set("CommandExecutor", new CommandExecutorFunction());
        globals.set("Bukkit", CoerceJavaToLua.coerce(Bukkit.class));
        File scripts = new File(Bukkit.getWorldContainer(), "scripts");
        if (!scripts.exists()) scripts.mkdirs();
        Reflections reflections = new Reflections("org.bukkit.event");

        bukkitEvents = reflections.get(Scanners.SubTypes.of(Event.class).asClass());
        for(File file : scripts.listFiles()) {
            Script script = new Script(globals, file);
            runningScripts.add(script);
        }
    }
}
