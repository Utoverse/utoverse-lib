package cn.utoverse.utoverselib.database;

import be.bendem.sqlstreams.SqlStream;
import be.bendem.sqlstreams.util.SqlConsumer;
import be.bendem.sqlstreams.util.SqlFunction;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.intellij.lang.annotations.Language;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseCoreImpl implements IDatabaseCore {
    private static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);
    // https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing
    private static final int MAXIMUM_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2) + 1;
    private static final int MINIMUM_IDLE = Math.min(MAXIMUM_POOL_SIZE, 10);

    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30);
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(10);
    private static final long LEAK_DETECTION_THRESHOLD = TimeUnit.SECONDS.toMillis(10);

    private final HikariDataSource source;
    private final SqlStream stream;

    private final String databaseFileName = "UtoverseLib";

    public DatabaseCoreImpl() {
        final HikariConfig config = new HikariConfig();

        config.setPoolName("utoverse-lib-" + POOL_COUNTER.getAndIncrement());
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:plugins/UtoverseLib/" + databaseFileName + ".db");
        config.setConnectionTestQuery("SELECT 1");

        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.setMinimumIdle(MINIMUM_IDLE);

        config.setMaxLifetime(MAX_LIFETIME);
        config.setConnectionTimeout(CONNECTION_TIMEOUT);
        config.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);

        config.setMaxLifetime(60000); // 60 Sec
        config.setIdleTimeout(45000); // 45 Sec
        config.setMaximumPoolSize(50); // 50 Connections (including idle connections)

        this.source = new HikariDataSource(config);
        this.stream = SqlStream.connect(this.source);
    }

    @Nonnull
    @Override
    public HikariDataSource getHikari() {
        return this.source;
    }

    @Nonnull
    @Override
    public Connection getConnection() throws SQLException {
        return Objects.requireNonNull(this.source.getConnection(), "connection is null");
    }

    @Nonnull
    @Override
    public SqlStream stream() {
        return this.stream;
    }

    @Override
    public void execute(@Language("SQLite") @Nonnull String statement, @Nonnull SqlConsumer<PreparedStatement> preparer) {
        try (Connection c = this.getConnection(); PreparedStatement s = c.prepareStatement(statement)) {
            preparer.accept(s);
            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <R> Optional<R> query(@Language("SQLite") @Nonnull String query, @Nonnull SqlConsumer<PreparedStatement> preparer, @Nonnull SqlFunction<ResultSet, R> handler) {
        try (Connection c = this.getConnection(); PreparedStatement s = c.prepareStatement(query)) {
            preparer.accept(s);
            try (ResultSet r = s.executeQuery()) {
                return Optional.ofNullable(handler.apply(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void close() {
        this.source.close();
    }
}
