package me.mrfunny.blua.scripts;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

import java.io.File;

public class Script {
    private final LuaValue chunk;
    public Script(Globals globals, File file) {
        this.chunk = globals.loadfile(file.getAbsolutePath());
    }

    public void run() {
        chunk.call();
    }
}
