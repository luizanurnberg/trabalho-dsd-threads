package constants;

import java.nio.file.Paths;

public enum RoadTranslationConst {

    icon0("grass_background.png"),
    icon1("up-arrow_60708.png"),
    icon2("right-straight-arrow_60554.png"),
    icon3("down-arrow_60928.png"),
    icon4("left-arrow_60586.png"),
    icon5("two-arrows-pointing-up_60640.png"),
    icon6("fast-forward-double-right-arrows_60678.png"),
    icon7("two-down-arrows_60920.png"),
    icon8("rewind-double-arrows-angles_60769.png"),
    icon9("arrows-right-angle_60919.png"),
    icon10("two-united-arrows-straight-angle-pointing-left-up_60705.png"),
    icon11("right-down-arrows-straight-angle_60622.png"),
    icon12("two-united-arrows-straight-angle-pointing-left-down_60599.png");

    private final String fileName;
    private static final String ICONS_DIRECTORY = Paths.get("").toAbsolutePath() + "/src/icons/";

    RoadTranslationConst(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return ICONS_DIRECTORY + fileName;
    }
}
