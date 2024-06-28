package net.deechael.hoyoi.tips;

import java.util.List;

public record Tip(
        String title,
        List<String> desc
) {
}
