package cn.utoverse.utoverselib.util.locale;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.util.Util;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ItemLocale {
    @Getter
    private static YamlConfiguration localeList;

    public static void loadLocale() {
        AbstractUtoverseLibPlugin plugin = AbstractUtoverseLibPlugin.getInstance();

        File itemi18nFile = new File(plugin.getDataFolder(), Localeable.getLocaleDir("item"));
        YamlConfiguration defaultYaml = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Objects.requireNonNull(Localeable.getLocaleFileStream("item")),
                        StandardCharsets.UTF_8)
        );
        if (!itemi18nFile.exists()) {
            plugin.getLogger().info("Creating item.yml");
            plugin.saveResource(Localeable.getLocaleDir("item"), true);
        }
        localeList = YamlConfiguration.loadConfiguration(itemi18nFile);
        localeList.options().copyDefaults(false);
        localeList.setDefaults(defaultYaml);
    }

    public static String getI18n(@NotNull Material material) {
        return getI18n(material.getKey().toString());
    }

    public static String getI18n(@NotNull String typeKey) {
        if (typeKey.isEmpty()) {
            return "Item is empty";
        }
        String itemnameI18n = localeList.getString("items." + typeKey);
        if (itemnameI18n != null && !itemnameI18n.isEmpty()) {
            return itemnameI18n;
        }
        Material material = Material.matchMaterial(typeKey);
        if (material == null) {
            return "Material not exist";
        }
        return Util.prettifyText(material.name());

    }
}
