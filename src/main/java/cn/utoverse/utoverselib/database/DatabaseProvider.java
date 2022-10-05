package cn.utoverse.utoverselib.database;

import javax.annotation.Nonnull;

public interface DatabaseProvider {

    /**
     * Gets the global datasource.
     *
     * @return the global datasource.
     */
    @Nonnull
    IDatabaseCore getCore();

}
