package pl.botprzemek.bpBungeeUtils.Utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

public class MySQLDatabase {

    private final HikariDataSource hikari;

    public MySQLDatabase(Configuration configuration) {

        ProxyServer.getInstance().getLogger().info("Loading MySQL database...");

        Configuration config = configuration.getSection("database");

        String jdbcUrl = "jdbc:mysql://" + config.getString("host") + ":" + config.getInt("port") + "/" + config.getString("database");

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(config.getString("user"));
        hikariConfig.setPassword(config.getString("password"));
        hikariConfig.setMaxLifetime(870000000);
        hikariConfig.setConnectionTimeout(870000000);
        hikariConfig.setRegisterMbeans(true);
        hikariConfig.setMaximumPoolSize(10);

        this.hikari = new HikariDataSource(hikariConfig);

        createTable();

    }

    public void disconnectDatabase() {

        hikari.close();

    }

    public void createTable() {

        try {

            Connection connection = hikari.getConnection();

            connection.createStatement().executeUpdate("create table if not exists user_codes(code_id int(6) not null auto_increment, code varchar(24) not null, used_by varchar(36) default null null, constraint user_codes_pk primary key (code_id))");

        }

        catch (SQLException error) {

            error.printStackTrace();

        }

    }


    public CachedRowSet sendQuery(PreparedStatement preparedStatement) {

        CachedRowSet rowSet = null;

        try {

            ExecutorService executor = Executors.newCachedThreadPool();

            Future<CachedRowSet> future = executor.submit(new Callable<CachedRowSet>() {

                public CachedRowSet call() {

                    try {

                        if (preparedStatement.isClosed()) return null;

                        ResultSet resultSet = preparedStatement.executeQuery();

                        CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();

                        cachedRowSet.populate(resultSet);

                        resultSet.close();

                        preparedStatement.close();

                        return  cachedRowSet;

                    }

                    catch (SQLException error) {

                        error.printStackTrace();

                    }

                    return null;

                }

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

            PreparedStatement preparedStatement = hikari.getConnection().prepareStatement(query);

            int x = 0;

            if (!query.contains("?") && args.length == 0) return preparedStatement;

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


    public PreparedStatement prepareStatements(String query, List<String> arg) {

        try {

            if (!hikari.isRunning() || hikari.getConnection() == null) return null;

            PreparedStatement preparedStatement = hikari.getConnection().prepareStatement(query);

            for (String var : arg) {

                preparedStatement.setString(1, var);

                preparedStatement.addBatch();

            }

            return preparedStatement;

        }

        catch (SQLException error) {

            error.printStackTrace();

        }

        return null;

    }

    public void executeQuery(PreparedStatement preparedStatement) {

        if (preparedStatement == null) return;

        try {

            if (preparedStatement.isClosed()) return;

            preparedStatement.executeQuery();

            preparedStatement.close();

        }

        catch (SQLException error) {

            error.printStackTrace();

        }
    }

    public void executeBatch(PreparedStatement preparedStatement) {

        if (preparedStatement == null) return;

        try {

            ProxyServer.getInstance().getLogger().info("[MYSQL] Trying to send data...");

            if (preparedStatement.isClosed()) return;

            preparedStatement.executeBatch();

            preparedStatement.close();

            ProxyServer.getInstance().getLogger().info("[MYSQL] Data has been sent! Closing statement...");

        }

        catch (SQLException error) {

            error.printStackTrace();

        }

    }

}
