package me.abel.security.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * @Description
 * @Author Abel.li
 * @Date 2020/12/29 下午16:10
 */
public class MySQL5InnoDBDialectUtf8mb4 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci";
    }
}