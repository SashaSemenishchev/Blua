package me.mrfunny.blua.scripts;

import org.luaj.vm2.Globals;

import java.io.File;

public class Script {
    public Script(Globals globals, File file) {
        globals.loadfile(file.getAbsolutePath()).call();
    }
}
