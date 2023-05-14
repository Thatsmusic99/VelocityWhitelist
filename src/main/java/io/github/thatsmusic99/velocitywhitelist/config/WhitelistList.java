package io.github.thatsmusic99.velocitywhitelist.config;

import io.github.thatsmusic99.configurationmaster.annotations.Option;
import io.github.thatsmusic99.configurationmaster.api.ConfigFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhitelistList extends ConfigFile {

    private static WhitelistList instance;
    public @Option List<String> WHITELISTED_NAMES = new ArrayList<>();

    public WhitelistList(@NotNull File file) throws IOException, IllegalAccessException {
        super(file);

        instance = this;
    }

    public static WhitelistList get() {
        return instance;
    }
}
