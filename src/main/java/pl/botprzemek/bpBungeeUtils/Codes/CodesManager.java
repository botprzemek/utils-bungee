package pl.botprzemek.bpBungeeUtils.Codes;

import java.util.HashMap;
import java.util.UUID;

public class CodesManager {

    private final String INSERT = "INSERT INTO user_codes VALUES(?,?)";

    private final String SELECT = "SELECT code FROM user_codes WHERE used_by=?";

    private final String UPDATE = "UPDATE user_codes SET used_by=? WHERE code=?";

    private final String DELETE = "DELETE FROM user_codes WHERE code=?";

    private final HashMap<String, UUID> codes;

    public CodesManager() {

        this.codes = loadCodes();

    }

    public HashMap<String, UUID> loadCodes() {

        return codes;

    }

    public void addCode(String code) {

        codes.put(code, null);

    }

    public void updateCode(String code, UUID usedBy) {

        codes.put(code, usedBy);

    }

    public void deleteCode(String code) {

        codes.remove(code);

    }

}
