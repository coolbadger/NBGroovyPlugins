package com.lwt.base

import groovy.sql.Sql

/**
 * 通用Oracle数据库连接类
 * Created by badger on 2017/3/13.
 */
class OracleLinkTest {

    Sql sql
    String host
    String sid
    String userName
    String password

    OracleLinkTest(String host, String sid, String userName, String password) {
        this.host = host
        this.sid = sid
        this.userName = userName
        this.password = password
        initDataLink()
    }

    void initDataLink() {
        try {
            String DB = "jdbc:oracle:thin:@" + host + ":" + "1521" + ":" + sid
            String DRIVER = 'oracle.jdbc.driver.OracleDriver'
            sql = Sql.newInstance(DB, userName, password, DRIVER)
        } catch (Exception e) {
        }
    }
}
