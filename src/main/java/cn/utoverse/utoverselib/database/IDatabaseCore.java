package cn.utoverse.utoverselib.database;

import be.bendem.sqlstreams.SqlStream;
import be.bendem.sqlstreams.util.SqlConsumer;
import be.bendem.sqlstreams.util.SqlFunction;
import com.zaxxer.hikari.HikariDataSource;
import me.lucko.helper.Schedulers;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.terminable.Terminable;
import org.intellij.lang.annotations.Language;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Represents an individual SQL datasource, created by the library.
 */
public interface IDatabaseCore extends Terminable {

    /**
     * Gets the Hikari instance backing the datasource
     *
     * @return the hikari instance
     */
    @Nonnull
    HikariDataSource getHikari();

    /**
     * Gets a connection from the datasource.
     *
     * <p>The connection should be returned once it has been used.</p>
     *
     * @return a connection
     */
    @Nonnull
    Connection getConnection() throws SQLException;

    /**
     * Gets a {@link SqlStream} instance for this {@link Sql}.
     *
     * @return a instance of the stream library for this connection.
     */
    @Nonnull
    SqlStream stream();

    /**
     * Executes a database statement with no preparation.
     *
     * <p>This will be executed on an asynchronous thread.</p>
     *
     * @param statement the statement to be executed
     * @return a Promise of an asynchronous database execution
     * @see #execute(String) to perform this action synchronously
     */
    @Nonnull
    default Promise<Void> executeAsync(@Language("SQLite") @Nonnull String statement) {
        return Schedulers.async().run(() -> this.execute(statement));
    }

    /**
     * Executes a database statement with no preparation.
     *
     * <p>This will be executed on whichever thread it's called from.</p>
     *
     * @param statement the statement to be executed
     * @see #executeAsync(String) to perform the same action asynchronously
     */
    default void execute(@Language("SQLite") @Nonnull String statement) {
        this.execute(statement, stmt -> {});
    }

    /**
     * Executes a database statement with preparation.
     *
     * <p>This will be executed on an asynchronous thread.</p>
     *
     * @param statement the statement to be executed
     * @param preparer the preparation used for this statement
     * @return a Promise of an asynchronous database execution
     * @see #executeAsync(String, SqlConsumer) to perform this action synchronously
     */
    @Nonnull
    default Promise<Void> executeAsync(@Language("SQLite") @Nonnull String statement, @Nonnull SqlConsumer<PreparedStatement> preparer) {
        return Schedulers.async().run(() -> this.execute(statement, preparer));
    }

    /**
     * Executes a database statement with preparation.
     *
     * <p>This will be executed on whichever thread it's called from.</p>
     *
     * @param statement the statement to be executed
     * @param preparer the preparation used for this statement
     * @see #executeAsync(String, SqlConsumer) to perform this action asynchronously
     */
    void execute(@Language("SQLite") @Nonnull String statement, @Nonnull SqlConsumer<PreparedStatement> preparer);

    /**
     * Executes a database query with no preparation.
     *
     * <p>This will be executed on an asynchronous thread.</p>
     *
     * <p>In the case of a {@link SQLException} or in the case of
     * no data being returned, or the handler evaluating to null,
     * this method will return an {@link Optional#empty()} object.</p>
     *
     * @param query the query to be executed
     * @param handler the handler for the data returned by the query
     * @param <R> the returned type
     * @return a Promise of an asynchronous database query
     * @see #query(String, SqlFunction) to perform this query synchronously
     */
    default <R> Promise<Optional<R>> queryAsync(@Language("SQLite") @Nonnull String query, @Nonnull SqlFunction<ResultSet, R> handler) {
        return Schedulers.async().supply(() -> this.query(query, handler));
    }

    /**
     * Executes a database query with no preparation.
     *
     * <p>This will be executed on whichever thread it's called from.</p>
     *
     * <p>In the case of a {@link SQLException} or in the case of
     * no data being returned, or the handler evaluating to null,
     * this method will return an {@link Optional#empty()} object.</p>
     *
     * @param query the query to be executed
     * @param handler the handler for the data returned by the query
     * @param <R> the returned type
     * @return the results of the database query
     * @see #queryAsync(String, SqlFunction) to perform this query asynchronously
     */
    default <R> Optional<R> query(@Nonnull String query, @Nonnull SqlFunction<ResultSet, R> handler) {
        return this.query(query, stmt -> {}, handler);
    }

    /**
     * Executes a database query with preparation.
     *
     * <p>This will be executed on an asynchronous thread.</p>
     *
     * <p>In the case of a {@link SQLException} or in the case of
     * no data being returned, or the handler evaluating to null,
     * this method will return an {@link Optional#empty()} object.</p>
     *
     * @param query the query to be executed
     * @param preparer the preparation used for this statement
     * @param handler the handler for the data returned by the query
     * @param <R> the returned type
     * @return a Promise of an asynchronous database query
     * @see #query(String, SqlFunction) to perform this query synchronously
     */
    default <R> Promise<Optional<R>> queryAsync(@Nonnull String query, @Nonnull SqlConsumer<PreparedStatement> preparer, @Nonnull SqlFunction<ResultSet, R> handler) {
        return Schedulers.async().supply(() -> this.query(query, preparer, handler));
    }
    /**
     * Executes a database query with preparation.
     *
     * <p>This will be executed on whichever thread it's called from.</p>
     *
     * <p>In the case of a {@link SQLException} or in the case of
     * no data being returned, or the handler evaluating to null,
     * this method will return an {@link Optional#empty()} object.</p>
     *
     * @param query the query to be executed
     * @param preparer the preparation used for this statement
     * @param handler the handler for the data returned by the query
     * @param <R> the returned type
     * @return the results of the database query
     * @see #queryAsync(String, SqlFunction) to perform this query asynchronously
     */
    <R> Optional<R> query(@Language("SQLite") @Nonnull String query, @Nonnull SqlConsumer<PreparedStatement> preparer, @Nonnull SqlFunction<ResultSet, R> handler);

}
