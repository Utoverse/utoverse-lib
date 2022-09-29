package cn.utoverse.utoverselib.util.locale;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public class Localeable {
    @Getter
    protected static String localeDir = "locale";

    protected static InputStream getLocaleFileStream(@NotNull String ymlFileName) {
        return UtoverseLibPlugin.getInstance().getResource(getLocaleDir(ymlFileName));
    }

    protected static String getLocaleDir(@NotNull String ymlFileName) {
        return String.format("%s/%s.yml", localeDir, ymlFileName);
    }
}
