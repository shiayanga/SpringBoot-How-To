package com.github.shiayanga.loader;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * @author LiYang
 */
public class LuaTest {
    public static void main(String[] args) throws Exception {
        // 执行10000次, 具体对比耗时值
        System.out.println("执行10000次, 具体对比耗时值:");
        // java 模式运行
        long beg = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            int a = 0;
            for (int i = 0; i <= 100000; i++) {
                a = a + i;
            }
        }
        long end = System.currentTimeMillis();
        System.err.printf("Java 执行时长: %dms%n", end - beg);
        // ---------------------- lua ---------------------
        long begin = System.currentTimeMillis();
        // 加载lua脚本
        String path = LuaTest.class.getClassLoader().getResource("script/luaTest.lua").toURI().getPath();
        Globals globals = JsePlatform.standardGlobals();
        for (int j = 0; j < 10000; j++) {
            LuaValue call = globals.loadfile(path).call();
        }
        end = System.currentTimeMillis();
        System.err.printf("Lua 加载文件 --- 执行时长: %dms%n", end - begin);

        begin = System.currentTimeMillis();
        // 脚本写在代码里
        String luaStr = "a = 0; for i = 0, 10000, 1 do a = a + i; end";
        for (int j = 0; j < 10000; j++) {
            globals.load(luaStr).call();
        }
        end = System.currentTimeMillis();
        System.err.printf("Lua 不加载文件 --- 执行时长: %dms%n", end - begin);


    }
}
