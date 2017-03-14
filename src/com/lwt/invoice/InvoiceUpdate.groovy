package com.lwt.invoice

import com.lwt.base.OracleLink
import com.navis.argo.business.api.GroovyApi
import com.navis.billing.business.model.Invoice
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

    String billID
    String sqlStr

    InvoiceItem invoiceItem
    Invoice invoice

    //返回信息
    String retMsg

    //入口
    public String execute(Map inParameters) {
        api = new GroovyApi()
        n4View = new OracleLink("192.168.3.200", "orcl", "nbinvoice", "nbinvoice").sql

        try {
            billID = inParameters.get("InvoiceID")
        } catch (Exception e) {
            api.log("解析参数出现异常:" + e.toString())
        }

        //查询invoiceItem详情
        sqlStr = """
select * from M_INVOICEDETAIL where BILLCODE='${billID}'
"""
        n4View.eachRow(sqlStr) { row ->
            updateInvoiceItem(row)
        }

        //查询是否涉及到多票invoice
        sqlStr = """
select INVOICE_GKEY from V_BILL_INVOICE where BILLCODE='${billID}'
"""
        n4View.eachRow(sqlStr) { row ->
            updateInvoice(row)
        }

        retMsg = "success"
        return retMsg
    }

    //根据传入行，更新invoice
    void updateInvoice(def row) {
        //invoice中发票单号列表，需要检查是否已经存在要插入的发票单号
        String billList

        invoice = Invoice.findInvoiceByGkey(row["INVOICE_GKEY"]);

        //TODO: 获取单号列表自定义发票号字段中的值
        billList = invoice.getField("")

        if (!billList.contains(row["BILLCODE"])) {
            billList += "," + row["BILLCODE"]
            invoice.setFieldValue("", billList)
        }
    }

    //根据传入行，更新invoiceItem
    void updateInvoiceItem(def row) {
        invoiceItem = InvoiceItem.findInvoiceItemGkeys(row["ITEMNO"])

        //TODO: 直接将单号填入费收明细的自定义发票号字段、实际收费字段
        invoiceItem.setFieldValue("", row["BILLCODE"])
    }

}
