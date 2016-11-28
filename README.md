# JEasyDB

JEasyDB是一个既安全又便捷的jdbc框架，使用JEasyDB就像使用脚本一样方便。

- 内部全都使用的PreparedStatement，尽可能的减少SQL注入的发生
- 尽可能的使用不定参方式传递参数
- 屏蔽了大部分异常，尽可能的使用null和布尔值来检测错误(但是仍可获取异常)

## Examples

```java
String password = ...;
String username = ...;
if (DB.query("user", "id", "username=? AND password=?", username, password)) {
   // ...
} else {
   // ...
}
```
也可以这样提高执行效率：
```java
// 预先获取一个PS对象
PS ps = PS.Get(QSql("user", "id", "username=? AND password=?"));
// ...
// 数据库查询
if (ps.query(username, password)) {
   // ...
} else {
   // ...
}
```
还可以使用HashMap指定不定个数的字段：
```java
HashMap<String, Object> kvs = DB.HM(
        "email", udata.getString("email"),
        "nick", udata.getString("nick"),
        "head", udata.getString("head"));
kvs.put("phone", phone);
kvs.put("password", password);
DB.insert("user", kvs);
```
DB.HM是一个快速构造HashMap<String, Object>的方法，对于Object为null的对象，将不会插入到HashMap里。

更多的文档请查阅[JEasyDB wiki](https://github.com/luzhlon/JEasyDB/wiki)

## LISENSE

此程序遵循MIT开源协议

