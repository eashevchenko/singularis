package org.shevalab.singularis.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureAtlasPacker {

    private static final String INPUT_DIR = "in";
    private static final String OUTPUT_DIR = "gfx/hero";
    private static final String ATLAS_NAME = "singularis";

    public static void main(String... args) {
        TexturePacker.process(INPUT_DIR, OUTPUT_DIR, ATLAS_NAME);
    }
}
