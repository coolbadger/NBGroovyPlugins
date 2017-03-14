package com.lwt.test

import com.lwt.base.OracleLinkTest
import groovy.sql.GroovyResultSetProxy
import groovy.sql.Sql

/**
 * Created by badger on 2017/3/14.
 */
class TestRun {

    public static void main(String[] args) {
        Sql n4View = new OracleLinkTest("192.168.3.200", "orcl", "nbinvoice", "nbinvoice").sql
        String outStr

        String sqlStr = """
select INVOICE_GKEY from V_BILL_INVOICE where BILLCODE='1'
"""
        n4View.eachRow(sqlStr) { row ->
            process(row)
        }

    }

    static void process(def row){
        println row["INVOICE_GKEY"]
    }
}
