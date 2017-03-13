package com.lwt.invoice

import com.lwt.base.N4ViewLink
import com.navis.argo.business.api.GroovyApi
import com.navis.billing.business.model.InvoiceItem
import groovy.sql.Sql

/**
 * 根据接口传入的发票开票信息，更新Billing中相关InvoiceItem
 * Created by badger on 2017/3/7.
 */
class InvoiceUpdate {

    //GroovyAPI 数据库链接
    GroovyApi api
    Sql n4View

    String invoiceID
    String sqlStr
    ArrayList<InvoiceItem> invoiceItems

    //返回信息
    String retMsg

    //入口
    public String execute(Map inParameters) {
        api = new GroovyApi()
        n4View = new N4ViewLink().sql
        invoiceItems = new ArrayList<InvoiceItem>()

        try {
            invoiceID = inParameters.get("InvoiceID")
        } catch (Exception e) {
            api.log("解析参数出现异常:" + e.toString())
        }

        //todo:查找数据库，匹配相关InvoiceItem
        sqlStr = """
select gkey from invoiceItem where 1=1
"""
        n4View.eachRow(sqlStr) { row ->
            invoiceItems.push(InvoiceItem.findInvoiceItemGkeys(row["gkey"]))
        }

        //todo:更新InvoiceItem与Invoice实体相关字段

        return retMsg
    }

}
