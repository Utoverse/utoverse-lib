package cn.utoverse.utoverselib.util.locale;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import cn.utoverse.utoverselib.util.Util;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class EntityLocale {
    @Getter
    private static YamlConfiguration localeList;

    public static void loadLocale() {
        UtoverseLibPlugin plugin = UtoverseLibPlugin.getInstance();

        File itemi18nFile = new File(plugin.getDataFolder(), Localeable.getLocaleDir("entity"));
        YamlConfiguration defaultYaml = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Objects.requireNonNull(Localeable.getLocaleFileStream("entity")),
                        StandardCharsets.UTF_8)
        );
        if (!itemi18nFile.exists()) {
            plugin.getLogger().info("Creating entity.yml");
            plugin.saveResource(Localeable.getLocaleDir("entity"), true);
        }
        localeList = YamlConfiguration.loadConfiguration(itemi18nFile);
        localeList.options().copyDefaults(false);
        localeList.setDefaults(defaultYaml);
    }

    public static String getI18n(@NotNull EntityType entityType) {
        return getI18n(entityType.name());
    }

    public static String getI18n(@NotNull String typeKey) {
        if (typeKey.isEmpty()) {
            return "EntityKey is empty";
        }
        String itemnameI18n = localeList.getString("entities." + typeKey);
        if (itemnameI18n != null && !itemnameI18n.isEmpty()) {
            return itemnameI18n;
        }
        EntityType entity = EntityType.valueOf(typeKey);
        if (entity == null) {
            return "Entity not exist";
        }
        return Util.prettifyText(entity.name());

    }
}
