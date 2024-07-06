package net.deechael.hoyoi.style;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.OptionEnum;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public enum HoYoIStyle implements OptionEnum, StringRepresentable {

    VANILLA(null, 0, "vanilla", "hoyoi.option.style.vanilla"),
    GENSHIN(GenshinStyle.INSTANCE, 1, "genshin", "hoyoi.option.style.genshin"),
    // STAR_RAIL(StarRailStyle.INSTANCE, 2, "star_rail", "hoyoi.option.style.star_rail")
    // temporarily hide Star Rail style preventing user use unsupported style and the pause screen and loading screen is deleted
    ;

    public static final Codec<HoYoIStyle> CODEC = StringRepresentable.fromEnum(HoYoIStyle::values);
    public static final IntFunction<HoYoIStyle> BY_ID = ByIdMap.continuous(HoYoIStyle::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

    private final StyleInstance instance;
    private final int id;
    private final String serializedName;
    private final String translationKey;

    HoYoIStyle(StyleInstance instance, int id, String serializedName, String translatableKey) {
        this.instance = instance;
        this.id = id;
        this.serializedName = serializedName;
        this.translationKey = translatableKey;
    }

    public StyleInstance getInstance() {
        return instance;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public @NotNull String getKey() {
        return this.translationKey;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.serializedName;
    }

}
