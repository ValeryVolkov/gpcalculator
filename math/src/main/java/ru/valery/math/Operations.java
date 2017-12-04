package ru.valery.math;

import ru.valery.plugins.Plugin;

public enum Operations {
    OpenBrace(() -> "("),
    CloseBrace(() -> ")"),
    ;
    private final Plugin plugin;
    Operations(Plugin plugin){
        this.plugin = plugin;
    }
    public Plugin getPlugin(){
        return plugin;
    }
}
