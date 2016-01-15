package org.shevalab.singularis.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;

public class AssetFactory {

    private static volatile AssetFactory instance;

    private AssetManager assets;

    private AssetFactory() {
        assets = new AssetManager();
    }

    public <T> T get(String fileName, Class<T> type) {
        return assets.get(fileName, type);
    }

    public boolean update() {
        return assets.update();
    }

    public boolean isLoaded(String fileName, Class<?> type) {
        return assets.isLoaded(fileName, type);
    }

    public float getProgress() {
        return assets.getProgress();
    }

    public void dispose() {
        assets.dispose();
        instance = null;
    }

    public void load() {
        // loading assets from AssetManager implementation here...
    }

    public Array<String> getLoaded() {
        return assets.getAssetNames();
    }

    public void update(float millis) {
        assets.update((int) millis);
    }

    public int getLoadedAssets() {
        return assets.getLoadedAssets();
    }

    public void finishLoading() {
        assets.finishLoading();
    }

    public static AssetFactory getInstance() {
        AssetFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (AssetFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AssetFactory();
                }
            }
        }
        return localInstance;
    }
}
