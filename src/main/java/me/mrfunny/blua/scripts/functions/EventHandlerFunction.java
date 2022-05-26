package me.mrfunny.blua.scripts.functions;

import me.mrfunny.blua.Blua;
import org.bukkit.event.EventPriority;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class EventHandlerFunction extends FourArgsFunction {

    @Override
    public LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3, LuaValue var4) {
        EventPriority priority = (EventPriority) CoerceLuaToJava.coerce(var1, EventPriority.class);
        boolean ignoreCancelled = var2.toboolean();
        String eventName = var3.checkstring().toString();
        LuaFunction executable = var4.checkfunction();
        Blua.instance.addScriptEventListener(eventName, priority, ignoreCancelled, executable);
        return LuaValue.NIL;
    }

    @Override
    public LuaValue call(LuaValue luaValue) {
        throw new LuaError("EventHandler must be called at least with 2 arguments, where 1st is name of event, and 2nd is function to execute");
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
        String eventName = luaValue.checkstring().toString();
        LuaFunction executable = luaValue1.checkfunction();
        Blua.instance.addScriptEventListener(eventName, EventPriority.NORMAL, true, executable);
        return LuaValue.NIL;
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue1, LuaValue luaValue2) {
        String eventName = luaValue.checkstring().toString();
        EventPriority priority;
        boolean ignoreCancelled;
        if(luaValue1.isboolean()) {
            priority = EventPriority.NORMAL;
            ignoreCancelled = luaValue1.toboolean();
        } else {
            priority = (EventPriority) CoerceLuaToJava.coerce(luaValue1, EventPriority.class);
            ignoreCancelled = true;
        }
        LuaFunction executable = luaValue2.checkfunction();
        Blua.instance.addScriptEventListener(eventName, priority, ignoreCancelled, executable);
        return LuaValue.NIL;
    }
}
