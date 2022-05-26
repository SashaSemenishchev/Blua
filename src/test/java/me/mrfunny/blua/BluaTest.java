package me.mrfunny.blua;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BluaTest {

    @org.junit.jupiter.api.Test
    void test() {
        Globals globals = JsePlatform.standardGlobals();
        globals.set("myf", new SomeFunction());
        LuaValue chunk = globals.loadfile(new File("main.lua").getAbsolutePath());
        chunk.call();
        assertEquals(1, 1);
    }

    public static class SomeClass {
        private int hello = 0;
        public void setHello(int hello) {
            this.hello = hello;
        }
        public int getHello() {
            return hello;
        }
        public void someMethod() {
            System.out.println("Hello World! " + this.hello);
        }
    }
}