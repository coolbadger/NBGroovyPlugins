package com.lwt.invoice

import com.navis.argo.business.api.GroovyApi
import paypal.payflow.Invoice

/**
 * 根据接口传入的发票开票信息，更新Billing中相关InvoiceItem
 * Created by badger on 2017/3/7.
 */
class InvoiceUpdate {

    //GroovyAPI 数据库链接
    GroovyApi api = new GroovyApi();
    //返回信息
    String retMsg;

    //入口
    public String execute(Map inParameters) {

        //todo:查找数据库，匹配相关InvoiceItem

        //todo:更新InvoiceItem与Invoice实体相关字段

        retMsg = Invoice.findAll().size()
        return retMsg
    }

}
