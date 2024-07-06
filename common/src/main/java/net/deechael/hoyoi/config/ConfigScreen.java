package net.deechael.hoyoi.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class ConfigScreen extends Screen {

    private final Screen parent;
    private ConfigListWidget entries;

    protected ConfigScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected final void init() {
        entries = new ConfigListWidget(this.minecraft, width, height - 64, 32, 25);
        addElements();
        addRenderableWidget(entries);
        addRenderableWidget(
                Button.builder(Component.translatable("hoyoi.option.apply"), (button) -> save())
                        .pos(this.width / 2 - 175, this.height - 27)
                        .size(150, 20)
                        .build()
        );
        addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, (button) -> {
                            save();
                            Minecraft.getInstance().setScreen(parent);
                        })
                        .pos(this.width / 2 + 25, this.height - 27)
                        .size(150, 20)
                        .build()
        );
    }

    private static class ConfigListWidget extends ContainerObjectSelectionList<ConfigListEntry> {

        public ConfigListWidget(Minecraft minecraftClient, int width, int height, int y, int itemHeight) {
            super(minecraftClient, width, height, y, itemHeight);
        }

        @Override
        public int addEntry(@NotNull ConfigListEntry entry) {
            return super.addEntry(entry);
        }

        public int getRowWidth() {
            return 400;
        }

        protected int getScrollbarPosition() {
            return super.getScrollbarPosition() + 32;
        }

        public Style getHoveredStyle(int mouseX, int mouseY) {
            Optional<GuiEventListener> hovered = getChildAt(mouseX, mouseY);
            return hovered.map(guiEventListener -> ((ConfigListEntry) guiEventListener).getHoveredStyle(mouseX, mouseY)).orElse(null);
        }
    }

    public static class ConfigListEntry extends ContainerObjectSelectionList.Entry<ConfigListEntry> {

        private final List<AbstractWidget> buttons;

        public ConfigListEntry(List<AbstractWidget> buttons) {
            this.buttons = buttons;
        }

        @Override
        public void render(@NotNull GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            for (AbstractWidget widget : buttons) {
                widget.setY(y);
                widget.render(context, mouseX, mouseY, tickDelta);
            }
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return buttons;
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return buttons;
        }

        public Style getHoveredStyle(int mouseX, int mouseY) {
            return null;
        }
    }

    @Override
    public final void removed() {
        save();
    }

    public abstract void save();

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        Style hoveredStyle = entries.getHoveredStyle(mouseX, mouseY);
        if (hoveredStyle != null) {
            context.renderComponentHoverEffect(this.font, hoveredStyle, mouseX, mouseY);
        }
        context.drawCenteredString(this.font, this.title, this.width / 2, 10, 16777215);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        // this.renderTransparentBackground(context);
        super.renderBackground(context, mouseX, mouseY, delta);
    }

    public abstract void addElements();

    public void addOption(OptionInstance<?> opt) {
        entries.addEntry(new ConfigListEntry(Collections.singletonList(opt.createButton(Minecraft.getInstance().options, width / 2 - 155, 0, 310))));
    }

    public void addOptionsRow(OptionInstance<?> opt, OptionInstance<?> opt2) {
        entries.addEntry(new ConfigListEntry(Arrays.asList(
                opt.createButton(Minecraft.getInstance().options, width / 2 - 155, 0, 150),
                opt2.createButton(Minecraft.getInstance().options, width / 2 - 155 + 160, 0, 150))));
    }

    public static class ConfigListHeader extends ConfigListEntry {
        private final Component headerText;
        private final Font textRenderer;
        private final int width;
        private final int textWidth;
        private final Screen screen;

        public ConfigListHeader(Component headerText, Font textRenderer, int width, Screen screen) {
            super(Collections.emptyList());
            this.headerText = headerText;
            this.textRenderer = textRenderer;
            this.width = width;
            this.textWidth = textRenderer.width(headerText);
            this.screen = screen;
        }

        @Override
        public void render(@NotNull GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            context.drawCenteredString(textRenderer, headerText, width / 2, y + 5, 16777215);
        }

        private Style getStyleAt(int mouseX) {
            int min = (width / 2) - (textWidth / 2);
            int max = (width / 2) + (textWidth / 2);
            if (mouseX >= min && mouseX <= max) {
                return textRenderer.getSplitter().componentStyleAtWidth(headerText, mouseX - min);
            }
            return null;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return screen.handleComponentClicked(getStyleAt((int) mouseX));
        }

        @Override
        public Style getHoveredStyle(int mouseX, int mouseY) {
            Style style = getStyleAt(mouseX);
            if (style != null && style.getHoverEvent() != null) {
                return style;
            }
            return null;
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return Collections.singletonList(new NarratableEntry() {
                @Override
                public @NotNull NarrationPriority narrationPriority() {
                    return NarrationPriority.HOVERED;
                }

                @Override
                public void updateNarration(@NotNull NarrationElementOutput builder) {
                    builder.add(NarratedElementType.TITLE, headerText);
                }
            });
        }
    }

    public void addHeading(Component text) {
        entries.addEntry(new ConfigListHeader(text, font, width, this));
    }

    public static class ConfigListTextField extends ConfigListEntry {
        private final EditBox textField;
        private final int textWidth;
        private final Font textRenderer;
        private final int x;

        public ConfigListTextField(Font textRenderer, int x, int y, int width, int height, Component description, Supplier<String> getter, Consumer<String> setter, Predicate<String> validator) {
            super(Collections.singletonList(makeField(textRenderer, x, y, width, height, description)));
            this.textField = (EditBox) children().getFirst();
            this.textWidth = textRenderer.width(description);
            this.textRenderer = textRenderer;
            this.x = x;
            textField.setValue(getter.get());
            textField.setResponder(value -> {
                if (validator.test(value)) {
                    setter.accept(value);
                    textField.setTextColor(14737632);
                } else {
                    textField.setTextColor(16711680);
                }
            });
        }

        private static EditBox makeField(Font textRenderer, int x, int y, int width, int height, Component description) {
            return new EditBox(textRenderer, x + (width / 2) + 7, y, (width / 2) - 8, height, description) {
                @Override
                public void updateWidgetNarration(@NotNull NarrationElementOutput builder) {
                    builder.add(NarratedElementType.TITLE, createNarrationMessage()); // Use the narration message which includes the description
                }
            };
        }

        @Override
        public Style getHoveredStyle(int mouseX, int mouseY) {
            int max = this.x + textWidth;
            if (mouseX >= this.x && mouseX <= max) {
                Style style = textRenderer.getSplitter().componentStyleAtWidth(textField.getMessage(), mouseX - this.x);
                if (style != null && style.getHoverEvent() != null) {
                    return style;
                }
            }
            return null;
        }

        @Override
        public void render(@NotNull GuiGraphics drawContext, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            drawContext.drawString(textRenderer, textField.getMessage(), this.x, y + 5, 16777215);
            super.render(drawContext, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
        }
    }

    public void addTextField(Component description, Supplier<String> getter, Consumer<String> setter) {
        addTextField(description, getter, setter, Objects::nonNull);
    }

    public void addTextField(Component description, Supplier<String> getter, Consumer<String> setter, Predicate<String> validator) {
        entries.addEntry(new ConfigListTextField(font, width / 2 - 154, 0, 308, 18, description, getter, setter, validator));
    }

    public void addIntField(Component description, Supplier<Integer> getter, Consumer<Integer> setter) {
        addTextField(description, () -> getter.get().toString(), value -> setter.accept(Integer.parseInt(value)), value -> {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public void addFloatField(Component description, Supplier<Float> getter, Consumer<Float> setter) {
        addTextField(description, () -> getter.get().toString(), value -> setter.accept(Float.parseFloat(value)), value -> {
            try {
                Float.parseFloat(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

}
