package me.mrfunny.blua.scripts.functions;

import org.bukkit.event.EventPriority;
import org.luaj.vm2.LuaFunction;

public class EventHandlerData {
    public EventPriority priority;
    public boolean ignoreCancelled;
    public String eventName;
    public LuaFunction executable;

    public EventHandlerData(EventPriority priority, boolean ignoreCancelled, String eventName, LuaFunction executable) {
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
        this.eventName = eventName;
        this.executable = executable;
    }
}
