package net.deechael.hoyoi.tips;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.*;

public class TipsManager {

    private final static String DEFAULT = "en_us";

    private final Map<String, List<Tip>> tips = new HashMap<>();

    public TipsManager() {
    }

    public Tip randomTip() {
        String current = Minecraft.getInstance().getLanguageManager().getSelected();
        return randomTip0(this.tips.containsKey(current) ? this.tips.get(current) : this.tips.get(DEFAULT));
    }

    private Tip randomTip0(List<Tip> tips) {
        return tips.get(new Random().nextInt(tips.size()));
    }

    public void load() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        for (Map.Entry<ResourceLocation, Resource> entry : resourceManager.listResources("tips", location -> true).entrySet()) {
            String languageCode = entry.getKey().getPath().split("/")[1].split("\\.")[0];
            if (!this.tips.containsKey(languageCode))
                this.tips.put(languageCode, new ArrayList<>());
            this.tips.get(languageCode).addAll(loadTips(entry.getValue()));
        }
    }

    private List<Tip> loadTips(Resource resource) {
        try (JsonReader reader = new JsonReader(resource.openAsReader())) {
            List<Tip> tips = new ArrayList<>();
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                tips.add(new Tip(object.get("title").getAsString(), object.getAsJsonArray("desc").asList().stream().map(JsonElement::getAsString).toList()));
            }
            return tips;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
