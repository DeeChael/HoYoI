package net.deechael.hoyoi.style;

public enum HoYoIStyle {

    GENSHIN(GenshinStyle.INSTANCE),
    STAR_RAIL(StarRailStyle.INSTANCE);

    private final StyleInstance instance;

    HoYoIStyle(StyleInstance instance) {
        this.instance = instance;
    }

    public StyleInstance getInstance() {
        return instance;
    }

}
