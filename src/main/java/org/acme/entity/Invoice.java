package org.acme.entity;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;


@Entity
@Table(name = "invoice")
@IdClass(InvoicePK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@ToString
@NamedQueries(value = {
    @NamedQuery(name = "Invoice.getById", 
    query = "SELECT i FROM Invoice i LEFT JOIN FETCH i.lineItems where i.invoiceNumber = ?1 and i.scanId = ?2"),
    @NamedQuery(name = "Invoice.getByCompanyCode", 
    query = "SELECT i FROM Invoice i where i.companyCode = ?1")
})
public class Invoice extends PanacheEntityBase implements Serializable {

    @Id
    @Column(name="invoice_number")
    public String invoiceNumber;

    @Id
    @Column(name = "scan_id")
    public String scanId;

    @Column(name="received_date")
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    public ZonedDateTime receivedDate;

    @Column(name="invoice_date")
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    public ZonedDateTime invoiceDate;

    @Column(name="baseline_date")
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    public ZonedDateTime baselineDate;

    @Column(name="type")
    public String type;

    @Column(name="currency")
    public String currency;

    @Column(name="po_number")
    public String poNumber;

    @Column(name="net_amount")
    public Double netAmount;

    @Column(name="total_tax_amount")
    public Double totalTaxAmount;

    @Column(name="total_amount")
    public Double totalAmount;

    @Column(name="company_code")
    public String companyCode;

    @Column(name="payment_id")
    public String paymentId;

    @Column(name="vendor_id")
    public String vendorId;

    @Column(name="wht_id")
    public String whtId;

    @Column(name="status")
    public String status;

    @Column(name="related_invoice_number")
    public String relatedInvoiceNumber;

    @OneToMany(mappedBy="invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<InvoiceItem> lineItems;

    //bi-directional one-to-one association to InvoiceCompany
	@OneToOne(mappedBy="invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public InvoiceCompany entity;

	//bi-directional one-to-one association to InvoicePayment
	@OneToOne(mappedBy="invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public InvoicePayment payment;

	//bi-directional one-to-one association to InvoiceVendor
	@OneToOne(mappedBy="invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public InvoiceVendor vendor;

	//bi-directional many-to-one association to InvoiceWht
	// @OneToMany(mappedBy="invoice", cascade = CascadeType.ALL)
	// public List<InvoiceWht> wht;

    // public String toString() {
    //     return this.getClass().getSimpleName() + "<" + this.invoiceNumber + ">";
    // }

    @ReactiveTransactional
    public static Uni<Invoice> createInvoice(Invoice invoice) {
        // Uni<Invoice> uni = Panache
        //         .withTransaction(() -> persist(invoice))
        //         .replaceWith(invoice)
        //         .ifNoItem()
        //         .after(Duration.ofMillis(10000))
        //         .fail()
        //         .onFailure()
        //         .transform(t -> new IllegalStateException(t));
        return Panache.getSession().chain(session -> session.persist(invoice)).replaceWith(invoice);
        //return uni;        

    }

    public static Uni<Invoice> saveInvoice(Invoice invoice) {
        InvoicePK id = new InvoicePK(invoice.invoiceNumber,invoice.scanId);
        System.out.println("**** SAVING  ***** "+id.invoiceNumber);

        // Uni<Invoice> uni = Panache
        // .withTransaction(() -> {return InvoiceItem.deleteInvoiceItem(invoice)
        // .chain(() -> Invoice.deleteInvoice(invoice))
        // .chain(() -> persist(invoice).replaceWith(invoice));})
        // .ifNoItem()
        // .after(Duration.ofMillis(10000))
        // .fail()
        // .onFailure()
        // .transform(t -> new IllegalStateException(t));   
        
        Uni<Invoice> uni = Panache
        .withTransaction(() -> updateInvoice(invoice))
        .ifNoItem()
        .after(Duration.ofMillis(10000))
        .fail()
        .onFailure()
        .transform(t -> new IllegalStateException(t));         
        
        return uni;        

    }
    
    @ReactiveTransactional
    public static Uni<Invoice> findByInvoiceId(String invoiceId, String scanId) {
        return find("#Invoice.getById", invoiceId, scanId).firstResult();
        //return Panache.getSession().chain(session -> session.createNamedQuer("#Invoice.getById").
    }

    @ReactiveTransactional
    public static Uni<List<Invoice>> findByCompanyCode(String companyCode) {
        Log.info("*** FETCHING  INVOICE BY CC *** "+companyCode); 
        return find("#Invoice.getByCompanyCode", companyCode).list();
        // Uni<List<Invoice>> uni = find("#Invoice.getByCompanyCode", companyCode).list();
        // uni.onItem().invoke(item -> Log.info(" ** ITEM *** "+item));
        // return uni; //find("#Invoice.getByCompanyCode", companyCode).list();
    }

    @ReactiveTransactional
    public static Uni<Long> deleteInvoice(Invoice invoice) {
        return Panache.withTransaction(() -> Invoice.delete("delete from Invoice i where i.invoiceNumber = ?1 and i.scanId = ?2", invoice.invoiceNumber,invoice.scanId));
        //return Panache.executeUpdate("what ever it is ?1, ?2", invoiceId,scanId).log();
        //return Panache.withTransaction(() -> deleteById(id));
    }

    @ReactiveTransactional
    public static Uni<Invoice> updateInvoice(Invoice invoice) {
        //Uni<Invoice> uniInv = Invoice.findByInvoiceId(invoice.invoiceNumber,invoice.scanId);
        Log.info("*** UPDATING INVOICE *** ");
        return Panache.getSession().chain(session -> session.merge(invoice));
    }

}

