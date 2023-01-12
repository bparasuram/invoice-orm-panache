package org.acme.entity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;


/**
 * The persistent class for the invoice_payment database table.
 * 
 */
@Entity
@Table(name="invoice_payment")
@IdClass(InvoicePK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@NamedQuery(name="InvoicePayment.findAll", query="SELECT i FROM InvoicePayment i")
public class InvoicePayment extends PanacheEntityBase implements Serializable {

	@Id
	@Column(name="invoice_number", insertable=false, updatable=false)
	public String invoiceNumber;

	@Id
	@Column(name="scan_id", insertable=false, updatable=false)
	public String scanId;

	public String description;

	@Column(name="due_date")
	@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	public ZonedDateTime dueDate;

	@Column(name="payment_method")
	public String paymentMethod;

	@Column(name="payment_reference")
	public String paymentReference;

	@Column(name="payment_terms")
	public String paymentTerms;

	//bi-directional one-to-one association to Invoice
	@OneToOne
	@MapsId
	@JoinColumns({
		@JoinColumn(name="invoice_number", referencedColumnName="invoice_number", insertable =  false, updatable = false),
		@JoinColumn(name="scan_id", referencedColumnName="scan_id", insertable =  false, updatable = false)
		})
	@JsonbTransient
	public Invoice invoice;

	

}