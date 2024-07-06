package net.deechael.hoyoi.fabric;

import net.deechael.hoyoi.HoYoI;
import net.fabricmc.api.ClientModInitializer;

public class HoYoIFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HoYoI.init();
/*

        KeyMapping keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping("test", InputConstants.KEY_B, "hoyoi"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.isDown()) {
                Minecraft.getInstance().setScreen(new AnimatedScreen(
                        Component.literal("test"),
                        Animation.moving()
                                .from(new Point(0, 0))
                                .to(new Point(200, 200))
                                .renderer((context, x, y) -> {
                                    context.getGraphics().fill(x, y, x + 40, y + 40, FastColor.ARGB32.color(255, 0, 0));
                                })
                                .duration(2000L)
                                .build()
                ));
            }
        });
*/

    }

}
