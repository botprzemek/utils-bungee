package pl.botprzemek.bpBungeeUtils.Codes;

import pl.botprzemek.bpBungeeUtils.BpBungeeUtils;
import pl.botprzemek.bpBungeeUtils.Utils.MySQLDatabase;

import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CodesManager {

    private BpBungeeUtils instance;

    private HashMap<String, UUID> codes;

    private final MySQLDatabase mySQLDatabase;

    public CodesManager(MySQLDatabase mySQLDatabase, BpBungeeUtils instance) {

        this.instance = instance;

        this.mySQLDatabase = mySQLDatabase;

        this.codes = new HashMap<>();

        loadCodes();

    }

    public void loadCodes() {

        String SELECT_MULTIPLE = "SELECT code, used_by FROM user_codes";

        PreparedStatement preparedStatement = mySQLDatabase.prepareStatement(SELECT_MULTIPLE);

        CachedRowSet cachedRowSet = mySQLDatabase.sendQuery(preparedStatement);

        try {

            while (cachedRowSet.next()) {

                String code = cachedRowSet.getString("code");

                UUID playerUUID = (cachedRowSet.getString("used_by") != null) ? UUID.fromString(cachedRowSet.getString("used_by")) : null;

                codes.put(code, playerUUID);

            }

        }

        catch (SQLException error) {

            error.printStackTrace();

        }

    }

    public void addCode(String code) {

        this.codes.put(code, null);

        String INSERT = "INSERT INTO user_codes (code) VALUES (?)";

        PreparedStatement preparedStatement = mySQLDatabase.prepareStatement(INSERT, code);

        mySQLDatabase.executeQuery(preparedStatement);

    }

    public void addCodes(List<String> codes) {

        for (String code : codes) this.codes.put(code, null);

        String INSERT = "INSERT INTO user_codes (code) VALUES (?)";

        PreparedStatement preparedStatement = mySQLDatabase.prepareStatements(INSERT, codes);

        mySQLDatabase.executeBatch(preparedStatement);

    }

    public boolean validateCode(String providedCode) {

        String SELECT = "SELECT code, used_by FROM user_codes WHERE code=?";

        PreparedStatement preparedStatement = mySQLDatabase.prepareStatement(SELECT, providedCode);

        CachedRowSet cachedRowSet = mySQLDatabase.sendQuery(preparedStatement);

        try {

            if (cachedRowSet.next()) {

                String code = cachedRowSet.getString("code");

                if (code == null) return false;

                UUID playerUUID = (cachedRowSet.getString("used_by") != null) ? UUID.fromString(cachedRowSet.getString("used_by")) : null;

                codes.put(code, playerUUID);

                return codes.get(code) == null;

            }

        }

        catch (SQLException error) {

            error.printStackTrace();

            return false;

        }

        return false;

    }

    public void updateCode(String code, UUID usedBy) {

        String UPDATE = "UPDATE user_codes SET used_by=? WHERE code=?";

        PreparedStatement preparedStatement = mySQLDatabase.prepareStatement(UPDATE, String.valueOf(usedBy), code);

        mySQLDatabase.executeQuery(preparedStatement);

        codes.put(code, usedBy);

    }

    public void deleteCode(String code) {

        String DELETE = "DELETE FROM user_codes WHERE code=?";

        PreparedStatement preparedStatement = mySQLDatabase.prepareStatement(DELETE, code);

        mySQLDatabase.executeQuery(preparedStatement);

        codes.remove(code);

    }

}
