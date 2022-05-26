package me.mrfunny.blua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class SomeFunction extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return LuaValue.valueOf("Hello World!");
    }
}
