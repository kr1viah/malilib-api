package kr1v.malilibApi.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.lib.mappingio.MappingReader;
import net.fabricmc.loader.impl.lib.mappingio.tree.MappingTree;
import net.fabricmc.loader.impl.lib.mappingio.tree.MemoryMappingTree;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public final class MappingUtils {
    private MappingUtils() {}

    private static final Map<String, String> cachedClassesReverse = new HashMap<>();

    private static final MemoryMappingTree tree = new MemoryMappingTree();
    private static final String currentVersion = FabricLoader.getInstance().getRawGameVersion();
    private static final Path mappingsPath = FabricLoader.getInstance().getGameDir().resolve(".tiny").resolve("yarn-" + currentVersion + "+build.1-tiny");

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
        new Thread(() -> {
            if (!mappingsPath.toFile().exists()) {
                try (InputStream versionStream = URI.create("https://meta.fabricmc.net/v2/versions/yarn/" + currentVersion).toURL().openStream()) {
                    String versionJson = new String(versionStream.readAllBytes(), StandardCharsets.UTF_8);
                    JsonArray versionObject = JsonParser.parseString(versionJson).getAsJsonArray();
                    String versionString = versionObject.get(0).getAsJsonObject().get("version").getAsString();

                    String url = "https://maven.fabricmc.net/net/fabricmc/yarn/" + versionString + "/yarn-" + versionString + "-tiny.gz";

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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                MappingReader.read(mappingsPath, tree);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}