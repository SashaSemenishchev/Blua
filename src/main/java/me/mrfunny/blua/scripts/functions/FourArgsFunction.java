package me.mrfunny.blua.scripts.functions;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class FourArgsFunction extends LibFunction {
    public abstract LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3, LuaValue var4);

    public Varargs invoke(Varargs var1) {
        return this.call(var1.arg1(), var1.arg(2), var1.arg(3), var1.arg(4));
    }
}
