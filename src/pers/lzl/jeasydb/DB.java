package pers.lzl.jeasydb;

import java.sql.*;
import java.util.HashMap;

/**
 * 数据库操作
 */
public class DB {
    private static Connection conn = null;
    public static String db_url = null;
    public static String db_user = null;
    public static String db_password = null;
    public static Exception last_err = null;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // 快速生成一个HashMap
    public static HashMap<String, Object> HM(Object... args) {
        HashMap<String, Object> hm = new HashMap<>();
        putHM(hm, args);
        return hm;
    }
    public static void putHM(HashMap<String, Object> hm, Object... args) {
        String key = null;
        Object val = null;
        for (int i = 0; i < args.length; i+=2) {
            key = (String) args[i];
            val = args[i+1];
            if (val != null) hm.put(key, val);
        }
    }
    // 获取数据库连接
    public static Connection connection() throws SQLException {
        if (conn == null || conn.isClosed())
            conn = DriverManager.getConnection(db_url, db_user, db_password);
        return conn;
    }
    public static String QSql(String table, String field) {
        return "SELECT "+field+" FROM "+table;
    }
    public static String QSql(String table, String field, String condition) {
        return QSql(table, field)+" WHERE "+condition;
    }
    public static String ISql(String table, String field, String values) {
        return "INSERT INTO "+table+" ("+field+") VALUES ("+values+")";
    }
    public static String USql(String table, String sets, String condition) {
        return "UPDATE "+table+" SET "+sets+" WHERE "+condition;
    }
    // 插入数据
    public static boolean insert(String table, String fields, Object... vals) {
        return PS.Get(ISql(table, fields, join("?", vals.length))).exec(vals);
    }
    public static boolean insert(String table, HashMap<String, Object> kvs) {
        if (kvs.size() == 0) return false;
        PS ps = PS.Get(ISql(table, join(kvs.keySet().toArray()), join("?", kvs.size())));
        return ps.exec(kvs.values().toArray());
    }
    // 查询数据
    public static RS query(String table, String fields,
                                  String condformat, Object... vals) {
        return PS.Get(QSql(table, fields, condformat)).query(vals);
    }
    public static RS query(String table, String fields) {
        return PS.Get(QSql(table, fields)).query();
    }
    // 删除数据
    static boolean delete(String table, String condformat, Object ...vals) {
        return PS.Get("DELETE FROM "+table+" WHERE "+condformat).exec(vals);
    }
    // string utils
    public static String join(Object[] objs, String deli, int begin, int end) {
        StringBuffer sb = new StringBuffer();
        int size = end - begin;
        if(size < 0) return null;
        if(size == 0) return objs[begin].toString();
        int i = begin;
        for(; i < end; i++)
            sb.append(objs[i].toString()).append(deli);
        sb.append(objs[i].toString());
        return sb.toString();
    }
    public static String join(Object[] objs, String deli) { return join(objs, deli, 0, objs.length - 1); }
    public static String join(Object[] objs) { return join(objs, ","); }
    public static String join(String cell, int num) { return join(cell, ",", num); }
    public static String join(String cell, String deli, int num) {
        StringBuffer sb = new StringBuffer();
        if(num < 1) return null;
        if(num == 1) return cell;
        int i = 0;
        for(int n = num - 1; i < n; i++)
            sb.append(cell).append(deli);
        sb.append(cell);
        return sb.toString();
    }
}