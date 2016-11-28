package pers.lzl.jeasydb;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tom on 2016/11/23.
 */
public class RS {
    ResultSet result = null;

    public RS(ResultSet rs) {
        result = rs;
    }

    public boolean next() {
        try {
            return result.next();
        } catch (SQLException e) {
            return false;
        }
    }
    public String str(int index) {
        try {
            return result.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }
    public Integer Int(int index) {
        try {
            return result.getInt(1);
        } catch (SQLException e) {
            return null;
        }
    }
}
