package pl.botprzemek.bpBungeeUtils.Codes;

import pl.botprzemek.bpBungeeUtils.Utils.Database;

import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class CodesManager {

    private HashMap<String, UUID> codes;

    private final Database database;

    public CodesManager(Database database) {

        this.database = database;

        this.codes = new HashMap<>();

        loadCodes();

    }

    public void loadCodes() {

        String SELECT_MULTIPLE = "SELECT code FROM user_codes WHERE used_by=null";

        PreparedStatement preparedStatement = database.prepareStatement(SELECT_MULTIPLE);

        CachedRowSet cachedRowSet = database.sendQuery(preparedStatement);

        if (cachedRowSet == null) codes = new HashMap<>();

        try {

            while (cachedRowSet.next()) {

                codes.put(cachedRowSet.getString("code"), null);

            }

        }

        catch (SQLException error) {

            error.printStackTrace();

        }

    }

    public void addCode(String code) {

        codes.put(code, null);

        String INSERT = "INSERT INTO user_codes(code) VALUES(?)";

        PreparedStatement preparedStatement = database.prepareStatement(INSERT, code);

        database.executeQuery(preparedStatement);

    }

    public boolean validateCode(String code) {

        return codes.containsKey(code);

    }

    public void updateCode(String code, UUID usedBy) {

        String UPDATE = "UPDATE user_codes SET used_by=? WHERE code=?";

        PreparedStatement preparedStatement = database.prepareStatement(UPDATE, String.valueOf(usedBy), code);

        database.executeQuery(preparedStatement);

        codes.put(code, usedBy);

    }

    public void deleteCode(String code) {

        String DELETE = "DELETE FROM user_codes WHERE code=?";

        PreparedStatement preparedStatement = database.prepareStatement(DELETE, code);

        database.executeQuery(preparedStatement);

        codes.remove(code);

    }

}
