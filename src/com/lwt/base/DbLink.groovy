package com.lwt.base

import com.navis.argo.business.api.GroovyApi
import groovy.sql.Sql

/**
 * Created by badger on 2017/3/13.
 */
class DbLink {
    GroovyApi api = new GroovyApi();
    Sql sql;

    void initDataLink() {
        try {
            String DB = "jdbc:oracle:thin:@" + "192.168.50.32" + ":" + "1521" + ":" + "n4"
            String USER = "n4view"
            String PASSWORD = "n4view"
            String DRIVER = 'oracle.jdbc.driver.OracleDriver'
            sql = Sql.newInstance(DB, USER, PASSWORD, DRIVER)
        } catch (Exception e) {
            api.logWarn(e.toString())
        }
    }
}
