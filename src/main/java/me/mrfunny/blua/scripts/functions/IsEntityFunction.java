package me.mrfunny.blua.scripts.functions;

import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.util.Objects;

public class IsEntityFunction extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
        Entity entity = (Entity) CoerceLuaToJava.coerce(luaValue, Entity.class);
        return LuaValue.valueOf(entity.getType().name().equals(luaValue.checkstring().toString()));
    }
}
