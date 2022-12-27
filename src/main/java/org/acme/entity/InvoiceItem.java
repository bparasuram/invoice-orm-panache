package org.acme.entity;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_lines")
@IdClass(InvoiceItemPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@NamedQueries(value = {
    @NamedQuery(name = "InvoiceItem.getById", 
    query = "SELECT i FROM InvoiceItem i  where i.invoiceNumber = ?1 and i.scanId = ?2")
})
public class InvoiceItem extends PanacheEntityBase {

    @Id
    @Column(name="invoice_number")
    public String invoiceNumber;

    @Id
    @Column(name = "scan_id")
    public String scanId;

    @Id
    @Column(name = "line_number")
    public Integer lineNumber;

    @Column(name="po_number")
    public String poNumber;

    @Column(name="description")
    public String description;

    @Column(name="uom")
    public String uom;

    @Column(name="quantity")
    public String quantity;

    @Column(name="unit_price")
    public String uniPrice;

    @Column(name="net_amount")
    private String netAmount;

    @Column(name="tax_code")
    public String taxCode;

    
    @Column(name="tax_percent")
    public String taxPercent;
    
    @Column(name="tax_amount")
    public String taxAmount;

    @Column(name="total_amount")
    public String totalAmount;

    @Column(name="material_number")
    public String materialNumber;

    @Column(name="status")
    public String status;

    @Column(name="po_line_number")
    public String poLineNumber;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="invoice_number", referencedColumnName="invoice_number", insertable =  false, updatable = false),
        @JoinColumn(name="scan_id", referencedColumnName="scan_id", insertable =  false, updatable = false)
    })
    @JsonbTransient
    public Invoice invoice;

    public String toString() {
        return this.getClass().getSimpleName() + "<" + this.invoiceNumber + ">";
    }

    public static Uni<Long> deleteInvoiceItem(Invoice invoice) {
        //return Panache.withTransaction(() -> delete("delete from InvoiceItem ii where ii.invoiceNumber = ?1 and ii.scanId = ?2", invoiceNumber, scanId));
        Log.info("Deleting Invoice Item "+invoice.invoiceNumber);
        return Panache.withTransaction(() -> delete("delete from InvoiceItem where invoiceNumber = ?1 and scanId = ?2", invoice.invoiceNumber, invoice.scanId));
    }

}

