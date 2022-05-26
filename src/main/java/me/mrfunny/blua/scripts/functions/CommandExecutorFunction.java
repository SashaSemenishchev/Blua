package me.mrfunny.blua.scripts.functions;

import me.mrfunny.blua.bukkitwrapper.BluaCommand;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandExecutorFunction extends VarArgFunction {

    @Override
    public Varargs invoke(LuaValue[] values) {
        String name = values[0].checkstring().toString();;
        List<String> aliases;
        String permission;
        String description;
        String usage;
        LuaFunction executable;
        if (values.length == 2) {
            aliases = new ArrayList<>();
            permission = "*";
            description = "A blua command: " + name;
            usage = "/" + name;
            executable = values[1].checkfunction();
        } else if(values.length == 3) {
            if(values[1].isstring()) {
                description = values[1].checkstring().toString();
                aliases = new ArrayList<>();
            } else {
                aliases = Arrays.stream(values[1].checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
                description = "A blua command: " + name;
            }
            permission = "*";
            usage = "/" + name;
            executable = values[2].checkfunction();
        } else if(values.length == 4) {
            aliases = Arrays.stream(values[1].checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
            description = values[2].checkstring().toString();
            executable = values[3].checkfunction();
            permission = "*";
            usage = "/" + name;
        } else if(values.length == 5) {
            aliases = Arrays.stream(values[1].checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
            description = values[2].checkstring().toString();
            usage = values[3].checkstring().toString();
            executable = values[4].checkfunction();
            permission = "*";
        } else {
            aliases = Arrays.stream(values[1].checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
            description = values[2].checkstring().toString();
            usage = values[3].checkstring().toString();
            permission = values[4].checkstring().toString();
            executable = values[5].checkfunction();
        }
        new BluaCommand(name, description, usage, permission, aliases, executable);

        return LuaValue.NIL;
    }

    @Override
    public LuaValue call(LuaValue luaValue) {
        throw new LuaError("CommandExecutor must be called at least with 2 arguments, 1st is the command name, 2nd is the command executor");
    }
}
