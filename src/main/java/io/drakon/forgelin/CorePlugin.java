package io.drakon.forgelin;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * This exists for the sole purpose of getting the language adapter onto the classpath before any actual mods
 * are loaded. The actual classloading is done via {@link SetupClass}
 */
public class CorePlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return "io.drakon.forgelin.SetupClass";
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
