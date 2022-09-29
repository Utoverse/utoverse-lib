package cn.utoverse.utoverselib.api;

import ink.tuanzi.utoverselib.IUtoverseLib;
import org.checkerframework.checker.nullness.qual.NonNull;

import static org.jetbrains.annotations.ApiStatus.Internal;

public final class UtoverseLibProvider {

    private static IUtoverseLib instance = null;

    /**
     * Gets an instance of the {@link IUtoverseLib} API,
     * throwing {@link IllegalStateException} if the API is not loaded yet.
     *
     * <p>This method will never return null.</p>
     *
     * @return an instance of the LuckPerms API
     * @throws IllegalStateException if the API is not loaded yet
     */
    public static @NonNull IUtoverseLib get() {
        IUtoverseLib instance = UtoverseLibProvider.instance;
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    @Internal
    static void register(IUtoverseLib instance) {
        UtoverseLibProvider.instance = instance;
    }

    @Internal
    static void unregister() {
        UtoverseLibProvider.instance = null;
    }

    @Internal
    private UtoverseLibProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Exception thrown when the API is requested before it has been loaded.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = "The UtoverseLib API isn't loaded yet!\n" +
                "This could be because:\n" +
                "  a) the UtoverseLib plugin is not installed or it failed to enable\n" +
                "  b) the plugin in the stacktrace does not declare a dependency on LuckPerms\n" +
                "  c) the plugin in the stacktrace is retrieving the API before the plugin 'enable' phase\n" +
                "     (call the #get method in onEnable, not the constructor!)\n";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}