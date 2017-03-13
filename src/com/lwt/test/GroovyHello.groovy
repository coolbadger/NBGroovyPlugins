package com.lwt.test

import com.navis.billing.business.model.Invoice
import com.navis.billing.business.model.InvoiceItem

/**
 * Created by badger on 2017/3/13.
 */
class GroovyHello {
    public String execute() {
        Invoice invoice = Invoice.findInvoiceByDraftNbr("498")
        Set invoiceItems = invoice.getInvoiceInvoiceItems()

        return invoiceItems.iterator().next().getClass()
    }
}
