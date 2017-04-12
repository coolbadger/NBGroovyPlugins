package com.lwt.invoice

import com.navis.argo.FlexStringField
import com.navis.argo.business.api.GroovyApi
import com.navis.billing.BillingBizMetafield
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
        retMsg = "no item found!"
        String orclClassName = "OracleLink"
        def oracleInstance = api.getGroovyClassInstance(orclClassName)

        n4View = oracleInstance.getLink("192.168.37.110", "nb", "nbinvoice", "nbinvoice")

        try {
            billID = inParameters.get("InvoiceID")
        } catch (Exception e) {
            api.log("解析参数出现异常:" + e.toString())
        }

        api.log("start invoice update")

        //查询invoiceItem详情，invoiceItem目前无详细数据
//        sqlStr = """
//select * from M_INVOIC where BILLCODE='${billID}'
//"""
//        n4View.eachRow(sqlStr) { row ->
//            updateInvoiceItem(row)
//        }

        //查询是否涉及到多票invoice
        sqlStr = """
select * from M_INVOICE where BILLCODE='${billID}'
"""
        n4View.eachRow(sqlStr) { row ->
            updateInvoice(row)
            retMsg = "success"
        }

        retMsg = "success"
        return retMsg
    }

    //根据传入行，更新invoice
    void updateInvoice(def row) {
        //invoice中发票单号列表，需要检查是否已经存在要插入的发票单号
        String billList
        String billDT
        String invoiceType

        invoice = Invoice.findInvoiceByGkey(row["BILLCODE"]);

        //TODO: 获取单号列表自定义发票号字段中的值
        billList = invoice.getField(BillingBizMetafield.INVOICE_FLEX_STRING01)
        billList = billList == null ? "" : billList

        if (!billList.contains(row["INVOICENO"])) {
            if (billList.length() > 1)
                billList += ","
            billList += row["INVOICENO"]
            billDT = row["BILLDT"]
            invoiceType = row["INVOICETYPE"]
            invoice.setFieldValue(BillingBizMetafield.INVOICE_FLEX_STRING01, billList) //发票号
            invoice.setFieldValue(BillingBizMetafield.INVOICE_FLEX_STRING02, billDT) //发票金额
            invoice.setFieldValue(BillingBizMetafield.INVOICE_FLEX_STRING10, invoiceType) //发票金额
        } else {
            billDT = row["BILLDT"]
            invoiceType = row["INVOICETYPE"]
            invoice.setFieldValue(BillingBizMetafield.INVOICE_FLEX_STRING02, billDT) //发票金额
            invoice.setFieldValue(BillingBizMetafield.INVOICE_FLEX_STRING10, invoiceType) //发票金额
        }
    }

    //根据传入行，更新invoiceItem
    void updateInvoiceItem(def row) {
        invoiceItem = InvoiceItem.findInvoiceItemGkeys(row["ITEMNO"])

        //TODO: 直接将单号填入费收明细的自定义发票号字段、实际收费字段
        invoiceItem.setFieldValue(BillingBizMetafield.INVOICE_PARM_BEXU_FLEX_STRING09, "ok")  //开票状态
        invoiceItem.setFieldValue(BillingBizMetafield.INVOICE_PARM_BEXU_FLEX_STRING10, row["BILLCODE"])   //发票号
//        invoiceItem.setFieldValue("invoiceParmBexuFlexDate05", row["BILLCODE"])   //开票日期
    }

}
