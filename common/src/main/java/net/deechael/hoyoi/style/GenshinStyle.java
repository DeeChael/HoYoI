package net.deechael.hoyoi.style;

import com.mojang.realmsclient.RealmsMainScreen;
import net.deechael.hoyoi.HoYoI;
import net.deechael.hoyoi.HoYoICaching;
import net.deechael.hoyoi.mixin.accessor.PauseScreenAccessor;
import net.deechael.hoyoi.mixin.accessor.ScreenInvoker;
import net.deechael.hoyoi.platform.Services;
import net.deechael.hoyoi.tips.Tip;
import net.deechael.hoyoi.ui.IconButton;
import net.deechael.hoyoi.ui.WrappedIconButton;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonLinks;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenshinStyle implements StyleInstance {

    // Center Region Icon color: 134, 153, 169

    private final static SimpleDateFormat HOUR = new SimpleDateFormat("HH");
    public final static GenshinStyle INSTANCE = new GenshinStyle();

    public static Tip TIP = null;
    public static long LAST_TIP_TIME = 0;

    public static ResourceLocation ICON;

    private GenshinStyle() {
    }

    private boolean isNight() {
        int current = Integer.parseInt(HOUR.format(new Date()));
        return current >= 19 || current < 7;
    }

    @Override
    public int getBackgroundColorR() {
        return this.isNight() ? 28 : 255;
    }

    @Override
    public int getBackgroundColorG() {
        return this.isNight() ? 28 : 255;
    }

    @Override
    public int getBackgroundColorB() {
        return this.isNight() ? 34 : 255;
    }

    @Override
    public void renderProgress(float progress, GuiGraphics graphics) {
        int size = HoYoICaching.FIRST_LOADING ? 32 : 16;
        int gap = size / 4;
        int halfSize = size / 2;

        int yOffset = HoYoICaching.FIRST_LOADING ? graphics.guiHeight() / 2 - halfSize : graphics.guiHeight() - size - halfSize;

        this.renderProgressPyro(progress, graphics, size, graphics.guiWidth() / 2 - halfSize - gap - size - gap - size - gap - size, yOffset);
        this.renderProgressHydro(progress, graphics, size, graphics.guiWidth() / 2 - halfSize - gap - size - gap - size, yOffset);
        this.renderProgressAnemo(progress, graphics, size, graphics.guiWidth() / 2 - halfSize - gap - size, yOffset);
        this.renderProgressElectro(progress, graphics, size, graphics.guiWidth() / 2 - halfSize, yOffset);
        this.renderProgressDendro(progress, graphics, size, graphics.guiWidth() / 2 + halfSize + gap, yOffset);
        this.renderProgressCryo(progress, graphics, size, graphics.guiWidth() / 2 + halfSize + gap + size + gap, yOffset);
        this.renderProgressGeo(progress, graphics, size, graphics.guiWidth() / 2 + halfSize + gap + size + gap + size + gap, yOffset);

        if (!HoYoICaching.FIRST_LOADING) {
            if (System.currentTimeMillis() - LAST_TIP_TIME > 5 * 1000L) {
                TIP = HoYoI.getTipsManager().randomTip();
                LAST_TIP_TIME = System.currentTimeMillis();
            }

            Font font = Minecraft.getInstance().font;

            int lineColor = this.isNight() ? FastColor.ARGB32.color(59, 59, 64) : FastColor.ARGB32.color(227, 227, 227);
            int dimensionColor = this.isNight() ? FastColor.ARGB32.color(211, 188, 142) : FastColor.ARGB32.color(134, 153, 169);

            graphics.blit(
                    ICON,
                    graphics.guiWidth() / 2 - 48,
                    graphics.guiHeight() / 2 - 72,
                    96,
                    96,
                    0f,
                    0f,
                    512,
                    512,
                    512,
                    512
            );

            String title = TIP.title();
            List<String> desc = TIP.desc();

            int titleOffset = graphics.guiHeight() - 35 - (18 * desc.size());

            graphics.drawString(font, title, graphics.guiWidth() / 2 - font.width(title) / 2, titleOffset, dimensionColor, false);

            /*graphics.pose().pushPose();
            graphics.pose().scale(1.2f, 1.2f, 1.2f);
            graphics.pose().popPose();*/

            for (int i = 0; i < desc.size(); i++) {
                String line = desc.get(i);
                graphics.drawString(font, line, graphics.guiWidth() / 2 - font.width(line) / 2, titleOffset + 15 + (6 * i) + (i - 1) * 2, dimensionColor, false);
            }

            graphics.fill(0, yOffset + halfSize - 2, graphics.guiWidth() / 2 - halfSize - gap - size - gap - size - gap - size - size, yOffset + halfSize - 1, lineColor);
            graphics.fill(graphics.guiWidth() / 2 + halfSize + gap + size + gap + size + gap + size + size, yOffset + halfSize - 2, graphics.guiWidth(), yOffset + halfSize - 1, lineColor);
        }
    }

    @Override
    public void renderPauseScreenBackground(GuiGraphics graphics) {
        graphics.fill(0, 0, 1, graphics.guiHeight(), FastColor.ARGB32.color(211, 188, 142));
        graphics.fill(1, 0, 39, graphics.guiHeight(), FastColor.ARGB32.color(72, 82, 102)); // actually in genshin is not exactly this color
        graphics.fill(39, 0, 40, graphics.guiHeight(), FastColor.ARGB32.color(211, 188, 142));

        int width = Math.max(graphics.guiWidth() / 3, 160);

        int cardHeight = graphics.guiHeight() / 7 * 2;

        graphics.fill(40, 0, 40 + width, cardHeight + 20 + (cardHeight / 2), FastColor.ARGB32.color(236, 229, 216));
        graphics.fill(40, 20, 40 + width, cardHeight + 20, FastColor.ARGB32.color(91, 139, 174));

        // transparency transition

        final int restPixels = graphics.guiHeight() - (cardHeight + 20 + (cardHeight / 2));

        final int alphaStart = 255;
        final int alphaEnd = 10;

        final int alphaDelta = alphaStart - alphaEnd;

        int pixelDelta = restPixels / (alphaDelta);
        if (restPixels - (pixelDelta * alphaDelta) > 0)
            pixelDelta += 1;

        for (int i = 0; i < alphaDelta; i++) {
            graphics.fill(40, cardHeight + 20 + (cardHeight / 2) + ((i - 1) * pixelDelta), 40 + width, cardHeight + 20 + (cardHeight / 2) + (i * pixelDelta), FastColor.ARGB32.color(alphaStart - i, 236, 229, 216));
        }

        // Render player's skin head as avatar

        int avatarSize = cardHeight / 7 * 3;

        graphics.blit(
                Minecraft.getInstance().player.getSkin().texture(),
                avatarSize / 3 + 40,
                avatarSize / 3 + 20,
                avatarSize,
                avatarSize,
                8f,
                8f,
                8,
                8,
                64,
                64
        );

        Font font = Minecraft.getInstance().font;
        String playerName = Minecraft.getInstance().player.getScoreboardName();

        int nameLength = font.width(playerName);

        int nameX = (avatarSize / 3) * 3 + 80;
        int namePreferredWidth = (40 + width) - nameX - avatarSize;

        int nameY = avatarSize / 3 + 20 + (avatarSize / 4);

        float scale = (float) namePreferredWidth / (float) nameLength;

        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        graphics.drawString(font, playerName, (int) (nameX / scale), (int) ((float) nameY / scale), FastColor.ARGB32.color(255, 255, 255), false);
        graphics.pose().popPose();
    }

    @Override
    public List<AbstractWidget> renderPauseScreenWidgets(PauseScreen pauseScreen) {
        Minecraft minecraft = Minecraft.getInstance();
        List<AbstractWidget> DEFAULT_BUTTONS = List.of(
                IconButton.builder()
                        .resourceLocation(ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/back.png"))
                        .onPress((button) -> {
                            minecraft.setScreen(null);
                            minecraft.mouseHandler.grabMouse();
                        })
                        .x(40 / 2 - 12)
                        .y(40 / 2 - 12)
                        .width(24)
                        .height(24)
                        .tooltip(Tooltip.create(((PauseScreenAccessor) pauseScreen).getReturnToGame()))
                        .build(),
                IconButton.builder()
                        .resourceLocation(ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/exit.png"))
                        .onPress((button) -> {
                            button.active = false;
                            minecraft.getReportingContext().draftReportHandled(minecraft, pauseScreen, () -> {
                                boolean flag = minecraft.isLocalServer();
                                ServerData serverdata = minecraft.getCurrentServer();
                                minecraft.level.disconnect();
                                if (flag) {
                                    minecraft.disconnect(new GenericMessageScreen(((PauseScreenAccessor) pauseScreen).getSavingLevel()));
                                } else {
                                    minecraft.disconnect();
                                }

                                TitleScreen titlescreen = new TitleScreen();
                                if (flag) {
                                    minecraft.setScreen(titlescreen);
                                } else if (serverdata != null && serverdata.isRealm()) {
                                    minecraft.setScreen(new RealmsMainScreen(titlescreen));
                                } else {
                                    minecraft.setScreen(new JoinMultiplayerScreen(titlescreen));
                                }
                            }, true);
                        })
                        .x(40 / 2 - 12)
                        .y(minecraft.getWindow().getGuiScaledHeight() - (40 / 2 - 12) - 24)
                        .width(24)
                        .height(24)
                        .tooltip(Tooltip.create(
                                Minecraft.getInstance().isLocalServer() ?
                                        ((PauseScreenAccessor) pauseScreen).getReturnToMenu() :
                                        CommonComponents.GUI_DISCONNECT)
                        )
                        .build()
        );

        // Mod developers, mixin this class and modify here to add your custom buttons!
        List<Triple<ResourceLocation, IconButton.OnPress, Component>> SIDE_BUTTONS = new ArrayList<>();
        SIDE_BUTTONS.add(
                Triple.of(
                        ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/mods.png"),
                        button -> Services.PLATFORM.openModMenu(pauseScreen),
                        Component.translatable("hoyoi.pause.button.modlist")
                )
        );
        SIDE_BUTTONS.add(
                Triple.of(
                        ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/settings.png"),
                        button -> minecraft.setScreen(new OptionsScreen(pauseScreen, minecraft.options)),
                        ((PauseScreenAccessor) pauseScreen).getOptions()
                )
        );

        int y = (minecraft.getWindow().getGuiScaledHeight() - (24 * SIDE_BUTTONS.size()) - (8 * (SIDE_BUTTONS.size() - 1))) / 2;

        List<AbstractWidget> BUILT_SIDE_BUTTONS = new ArrayList<>();
        for (Triple<ResourceLocation, IconButton.OnPress, Component> pair : SIDE_BUTTONS) {
            BUILT_SIDE_BUTTONS.add(
                    IconButton.builder()
                            .resourceLocation(pair.getLeft())
                            .onPress(pair.getMiddle())
                            .x(40 / 2 - 12)
                            .y(y)
                            .width(24)
                            .height(24)
                            .tooltip(Tooltip.create(pair.getRight()))
                            .build()
            );
            y += 8 + 24;
        }

        if (pauseScreen.showsPauseMenu()) {
            List<Triple<ResourceLocation, WrappedIconButton.OnPress, Component>> MENU_BUTTONS = new ArrayList<>();

            MENU_BUTTONS.add(
                    Triple.of(
                            ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/achievement.png"),
                            button -> minecraft.setScreen(new AdvancementsScreen(minecraft.player.connection.getAdvancements(), pauseScreen)),
                            Component.translatable("hoyoi.pause.button.achievement")
                    )
            );
            MENU_BUTTONS.add(
                    Triple.of(
                            ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/statistics.png"),
                            button -> minecraft.setScreen(new StatsScreen(pauseScreen, minecraft.player.getStats())),
                            Component.translatable("hoyoi.pause.button.statistics")
                    )
            );
            MENU_BUTTONS.add(
                    Triple.of(
                            ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/feedback.png"),
                            button -> ConfirmLinkScreen.confirmLinkNow(pauseScreen, SharedConstants.getCurrentVersion().isStable() ? CommonLinks.RELEASE_FEEDBACK : CommonLinks.SNAPSHOT_FEEDBACK),
                            Component.translatable("hoyoi.pause.button.feedback")
                    )
            );
            MENU_BUTTONS.add(
                    Triple.of(
                            ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/bug.png"),
                            button -> ConfirmLinkScreen.confirmLinkNow(pauseScreen, CommonLinks.SNAPSHOT_BUGS_FEEDBACK),
                            Component.translatable("hoyoi.pause.button.bug")
                    )
            );
            if (minecraft.hasSingleplayerServer() && !minecraft.getSingleplayerServer().isPublished()) {
                MENU_BUTTONS.add(
                        Triple.of(
                                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/menu/lan.png"),
                                button -> minecraft.setScreen(new ShareToLanScreen(pauseScreen)),
                                Component.translatable("hoyoi.pause.button.lan")
                        )
                );
            }

            int width = Math.max(minecraft.getWindow().getGuiScaledWidth() / 3, 160);

            int cardHeight = minecraft.getWindow().getGuiScaledHeight() / 7 * 2;

            int buttonWidth = (width - (4 * 5)) / 4;

            GridLayout gridlayout = new GridLayout();
            gridlayout.defaultCellSetting().padding(4, 4, 0, 0);
            GridLayout.RowHelper rowHelper = gridlayout.createRowHelper(4);

            for (Triple<ResourceLocation, WrappedIconButton.OnPress, Component> triple : MENU_BUTTONS) {
                rowHelper.addChild(
                        WrappedIconButton.builder()
                                .resourceLocation(triple.getLeft())
                                .onPress(triple.getMiddle())
                                .name(triple.getRight())
                                .x(0)
                                .y(0)
                                .width(buttonWidth)
                                .height(buttonWidth)
                                .build()
                );
            }

            gridlayout.arrangeElements();
            FrameLayout.alignInRectangle(gridlayout, 40, cardHeight + 20, 40 + width, minecraft.getWindow().getGuiScaledHeight(), 0F, 0F);
            gridlayout.visitWidgets(((ScreenInvoker) pauseScreen)::invokeAddRenderableWidget);
        }

        List<AbstractWidget> RESULT = new ArrayList<>(List.copyOf(DEFAULT_BUTTONS));
        RESULT.addAll(BUILT_SIDE_BUTTONS);
        return RESULT;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    ////                            INTERNAL FUNCTIONS                                ////
    //////////////////////////////////////////////////////////////////////////////////////

    private void renderProgressPyro(float progress, GuiGraphics graphics, int size, int x, int y) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/pyro" + (this.isNight() ? "" : "_empty") + ".png"),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/pyro" + (this.isNight() ? "_empty" : "") + ".png"),
                x,
                y,
                0,
                0,
                (int) (size * Mth.clamp(progress / 0.14f, 0.0f, 1.0f)),
                size,
                size,
                size
        );
    }

    private void renderProgressHydro(float progress, GuiGraphics graphics, int size, int x, int y) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/hydro" + (this.isNight() ? "" : "_empty") + ".png"),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/hydro" + (this.isNight() ? "_empty" : "") + ".png"),
                x,
                y,
                0,
                0,
                (int) (size * Mth.clamp((progress - 0.14f) / 0.14f, 0.0f, 1.0f)),
                size,
                size,
                size
        );
    }

    private void renderProgressAnemo(float progress, GuiGraphics graphics, int size, int x, int y) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/anemo" + (this.isNight() ? "" : "_empty") + ".png"),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/anemo" + (this.isNight() ? "_empty" : "") + ".png"),
                x,
                y,
                0,
                0,
                (int) (size * Mth.clamp((progress - 0.24f) / 0.14f, 0.0f, 1.0f)),
                size,
                size,
                size
        );
    }

    private void renderProgressElectro(float progress, GuiGraphics graphics, int size, int x, int y) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/electro" + (this.isNight() ? "" : "_empty") + ".png"),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/electro" + (this.isNight() ? "_empty" : "") + ".png"),
                x,
                y,
                0,
                0,
                (int) (size * Mth.clamp((progress - 0.42f) / 0.14f, 0.0f, 1.0f)),
                size,
                size,
                size
        );
    }

    private void renderProgressDendro(float progress, GuiGraphics graphics, int size, int x, int y) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/dendro" + (this.isNight() ? "" : "_empty") + ".png"),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/dendro" + (this.isNight() ? "_empty" : "") + ".png"),
                x,
                y,
                0,
                0,
                (int) (size * Mth.clamp((progress - 0.56f) / 0.14f, 0.0f, 1.0f)),
                size,
                size,
                size
        );
    }

    private void renderProgressCryo(float progress, GuiGraphics graphics, int size, int x, int y) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/cryo" + (this.isNight() ? "" : "_empty") + ".png"),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/cryo" + (this.isNight() ? "_empty" : "") + ".png"),
                x,
                y,
                0,
                0,
                (int) (size * Mth.clamp((progress - 0.70f) / 0.14f, 0.0f, 1.0f)),
                size,
                size,
                size
        );
    }

    private void renderProgressGeo(float progress, GuiGraphics graphics, int size, int x, int y) {
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/geo" + (this.isNight() ? "" : "_empty") + ".png"),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
        graphics.blit(
                ResourceLocation.fromNamespaceAndPath("hoyoi", "textures/ui/progress/geo" + (this.isNight() ? "_empty" : "") + ".png"),
                x,
                y,
                0,
                0,
                (int) (size * Mth.clamp((progress - 0.84f) / 0.14f, 0.0f, 1.0f)),
                size,
                size,
                size
        );
    }

}
