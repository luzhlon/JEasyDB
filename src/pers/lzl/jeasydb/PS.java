package pers.lzl.jeasydb;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by tom on 2016/11/23.
 */
public class PS {
    private PreparedStatement state = null;

    public static PS Get(String sql) {
        try {
            return new PS(DB.connection().prepareStatement(sql));
        } catch (Exception e) {
            DB.last_err = e;
            return null;
        }
    }
    private PS() {}
    public PS(PreparedStatement s) {
        state = s;
    }

    public boolean setObjs(Object... args) {
        try {
            for (int i = 0; i < args.length; i++)
                state.setObject(i+1, args[i]);
            return true;
        } catch (SQLException e) {
            DB.last_err = e;
            return false;
        }
    }
    public boolean exec(Object... args) {
        if (!setObjs(args))
            return false;
        try {
            state.execute();
            return true;
        } catch (SQLException e) {
            DB.last_err = e;
            return false;
        }
    }
    public RS query(Object... args) {
        if (!setObjs(args))
            return null;
        try {
            return new RS(state.executeQuery());
        } catch (SQLException e) {
            DB.last_err = e;
            return null;
        }
    }
    public int count() {
        try {
            return state.getUpdateCount();
        } catch (SQLException e) {
            DB.last_err = e;
            return -1;
        }
    }
    public RS result() {
        try {
            return new RS(state.getResultSet());
        } catch (SQLException e) {
            DB.last_err = e;
            return null;
        }
    }
}

