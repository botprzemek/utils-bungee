package pl.botprzemek.bpBungeeUtils.Utils;

import com.zaxxer.hikari.HikariDataSource;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Database {

    private final HikariDataSource hikari;

    private final BpBungeeUtils instance;

    public Database(Configuration configuration, BpBungeeUtils instance) {

        this.hikari = new HikariDataSource();

        this.instance = instance;

        Configuration config = configuration.getSection("database");

        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", config.getString("host"));
        hikari.addDataSourceProperty("port", config.getString("port"));
        hikari.addDataSourceProperty("databaseName", config.getString("database"));
        hikari.addDataSourceProperty("user", config.getString("user"));
        hikari.addDataSourceProperty("password", config.getString("password"));

        createTable();

    }

    public void disconnectDatabase() {

        hikari.shutdown();

    }

    public void createTable() {

        try {

            Connection connection = hikari.getConnection();

            connection.createStatement().execute("create table if not exists user_codes(code_id int(6) not null, code varchar(24) not null, used_by varchar(36) default null null, constraint user_codes_pk primary key (code_id)); create index user_codes_code_index on user_codes (code); create index user_codes_used_by_index on user_codes (used_by);");

        }

        catch (SQLException error) {

            error.printStackTrace();

        }
    }


    public CachedRowSet sendQuery(PreparedStatement preparedStatement) {

        CachedRowSet rowSet = null;

        if (!isConnected()) return null;

        try {
            ExecutorService executor = Executors.newCachedThreadPool();

            Future<CachedRowSet> future = executor.submit(() -> {

                try {

                    ResultSet resultSet = preparedStatement.executeQuery();

                    CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();

                    cachedRowSet.populate(resultSet);

                    resultSet.close();

                    preparedStatement.getConnection().close();

                    if (!cachedRowSet.next()) return cachedRowSet;

                }

                catch (SQLException error) {

                    error.printStackTrace();

                }

                return null;

            });

            if (future.get() == null) return null;

            rowSet = future.get();

        }

        catch (InterruptedException | ExecutionException error) {

            error.printStackTrace();

        }

        return rowSet;

    }

    public PreparedStatement prepareStatement(String query, String... args) {
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(query);

            int x = 0;

            if (!query.contains("?") && args.length == 0) return null;

            for (String var : args) {
                x++;
                preparedStatement.setString(x, var);
            }

            return preparedStatement;

        }

        catch (SQLException error) {

            error.printStackTrace();

        }

        return null;

    }

    public void executeQuery(PreparedStatement preparedStatement) {

        if (!isConnected()) return;

        ProxyServer.getInstance().getScheduler().runAsync(instance, ()-> {

            try {

                preparedStatement.execute();

                preparedStatement.getConnection().close();

            }

            catch (SQLException error) {

                error.printStackTrace();

            }
        });
    }

    public Connection getConnection() {

        try {

            return hikari.getConnection();

        }

        catch (SQLException error) {

            error.printStackTrace();

        }

        return null;

    }

    public boolean isConnected() {

        try {

            hikari.getConnection();

        }

        catch (SQLException error) {

            return false;

        }

        return true;

    }

}
