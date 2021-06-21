package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    BONES("bones"),
    BONES2("bones2"),
    BARS("bars"),
    OPENBARS("openbars"),
    WEB("web"),
    CANDLE("candle"),
    CANDELABRE("candelabre"),
    REDWALL1("redwall1"),
    REDWALL2("redwall2"),
    EXIT("exit"),
    DIRT("dirt"),
    TREE1("tree1"),
    TREE2("tree2"),
    GRAVE("grave"),
    BRIDGE1("bridge1"),
    BRIDGE2("bridge2"),
    BRIDGE3("bridge3"),
    TOWER("tower"),
    CASTLE("castle"),
    WATER("water"),
    BLUEDOOR("bluedoor"),
    OPENBLUEDOOR("openbluedoor"),
    REDDOOR("reddoor"),
    TOILET("toilet"),
    SPECTREE("spectree"),
    DEADTREE("deadtree"),
    GRASS1("grass1"),
    GRASS2("grass2"),
    ROCKS("rocks"),
    CITYPOST("citypost"),
    SIGNPOST("signpost"),
    HOUSE1("house1"),
    HOUSE2("house2"),
    HOUSE3("house3"),
    HOUSE4("house4"),
    HOUSE5("house5");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
