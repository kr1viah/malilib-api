package kr1v.malilibApi.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.lib.mappingio.MappingReader;
import net.fabricmc.loader.impl.lib.mappingio.tree.MappingTree;
import net.fabricmc.loader.impl.lib.mappingio.tree.MemoryMappingTree;
import net.minecraft.MinecraftVersion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class MappingUtils {
    private static final Map<String, String> cachedClassesReverse = new HashMap<>();

    private static final MemoryMappingTree tree = new MemoryMappingTree();
    private static final Path mappingsPath = FabricLoader.getInstance().getGameDir().resolve(".tiny").resolve("yarn-" + MinecraftVersion.CURRENT.getName() + "+build.1-tiny");

    public static String yarnToIntermediary(String yarnName) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) return yarnName; // already intermediary in dev

        String cached = cachedClassesReverse.get(yarnName);
        if (cached != null) return cached;

        String result = yarnName;
        for (MappingTree.ClassMapping c : tree.getClasses()) {
            String yarn = c.getDstName(1) != null ? c.getDstName(1).replace("/", ".") : null;
            if (Objects.equals(yarn, yarnName)) {
                result = c.getDstName(0).replace("/", ".");
                break;
            }
        }

        cachedClassesReverse.put(yarnName, result);
        return result;
    }


    static {
        try {
            if (!mappingsPath.toFile().exists()) {
                String version = MinecraftVersion.CURRENT.getName();
                String url = "https://maven.fabricmc.net/net/fabricmc/yarn/" + version + "%2Bbuild.1/yarn-" + version + "%2Bbuild.1-tiny.gz";

                Path gzPath = mappingsPath.resolveSibling("temp.gz");
                InputStream in = URI.create(url).toURL().openStream();

                if (!Files.exists(gzPath)) {
                    Files.createDirectories(gzPath.getParent());
                    Files.createFile(gzPath);
                }

                Files.copy(in, gzPath, StandardCopyOption.REPLACE_EXISTING);
                GZIPInputStream gis = new GZIPInputStream(new FileInputStream(gzPath.toFile()));
                try (FileOutputStream fos = new FileOutputStream(mappingsPath.toFile())) {

                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = gis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                }

                Files.delete(gzPath);
            }

            MappingReader.read(mappingsPath, tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}