package me.mrfunny.blua;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BluaTest {

    @org.junit.jupiter.api.Test
    void test() {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile(new File("main.lua").getAbsolutePath());
        LuaTable listeners = chunk.call().checktable();

        SomeClass someClass = new SomeClass();
        System.out.println();
        for(LuaValue listenerKey : listeners.get(1).checktable().keys()) {
            System.out.println(listenerKey);
            LuaValue possibleListener = listeners.get(listenerKey);
            if(possibleListener.isfunction()) {
                possibleListener.call(CoerceJavaToLua.coerce(someClass));
            }

        }
        LuaValue possibleCommands = listeners.get(2);
        if(!possibleCommands.isnil() && possibleCommands.istable()) {
            System.out.println("Found commands in the script");
            for(LuaValue listenerKey : possibleCommands.checktable().keys()) {
                System.out.println(listenerKey);
            }
        }
        assertEquals(1, someClass.getHello());
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