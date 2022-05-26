package me.mrfunny.blua;

import org.bukkit.event.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class BluaRegisteredListener extends RegisteredListener {
    private LuaFunction function;
    public BluaRegisteredListener(Listener listener, LuaFunction function, EventPriority priority, Plugin plugin, boolean ignoreCancelled) {
        super(listener, null, priority, plugin, ignoreCancelled);
        this.function = function;
    }

    @Override
    public void callEvent(Event event) throws EventException {
        if (event instanceof Cancellable) {
            if (((Cancellable) event).isCancelled() && isIgnoringCancelled()) {
                return;
            }
        }
        function.call(CoerceJavaToLua.coerce(event));
    }
}
