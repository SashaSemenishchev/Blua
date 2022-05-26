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
    public Varargs onInvoke(Varargs args) {
        String name = args.arg(1).checkstring().toString();;
        List<String> aliases;
        String permission;
        String description;
        String usage;
        LuaFunction executable;
        if (args.narg() == 2) {
            aliases = new ArrayList<>();
            permission = "*";
            description = "A blua command: " + name;
            usage = "/" + name;
            executable = args.arg(2).checkfunction();
        } else if(args.narg() == 3) {
            if(args.arg(1).isstring()) {
                description = args.arg(2).checkstring().toString();
                aliases = new ArrayList<>();
            } else {
                aliases = Arrays.stream(args.arg(2).checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
                description = "A blua command: " + name;
            }
            permission = "*";
            usage = "/" + name;
            executable = args.arg(3).checkfunction();
        } else if(args.narg() == 4) {
            aliases = Arrays.stream(args.arg(2).checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
            description = args.arg(3).checkstring().toString();
            executable = args.arg(4).checkfunction();
            permission = "*";
            usage = "/" + name;
        } else if(args.narg() == 5) {
            aliases = Arrays.stream(args.arg(2).checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
            description = args.arg(3).checkstring().toString();
            usage = args.arg(4).checkstring().toString();
            executable = args.arg(5).checkfunction();
            permission = "*";
        } else {
            aliases = Arrays.stream(args.arg(2).checktable().keys()).map(LuaValue::toString).collect(Collectors.toList());
            description = args.arg(3).checkstring().toString();
            usage = args.arg(4).checkstring().toString();
            permission = args.arg(5).checkstring().toString();
            executable = args.arg(6).checkfunction();
        }
        new BluaCommand(permission, name, description, usage, aliases, executable);
        return LuaValue.NIL;
    }

    @Override
    public LuaValue call(LuaValue luaValue) {
        throw new LuaError("CommandExecutor must be called at least with 2 arguments, 1st is the command name, 2nd is the command executor");
    }
}
