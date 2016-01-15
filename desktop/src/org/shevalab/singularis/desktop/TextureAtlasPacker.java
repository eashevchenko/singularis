package org.shevalab.singularis.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureAtlasPacker {

    private static final String INPUT_DIR = "out";
    private static final String OUTPUT_DIR = "gfx/hero";
    private static final String ATLAS_NAME = "singularis";

    public static void main(String... args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 512;
        settings.fast =true;
        TexturePacker.process(settings,INPUT_DIR, OUTPUT_DIR, ATLAS_NAME);
    }
}
