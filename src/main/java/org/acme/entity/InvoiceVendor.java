package org.acme.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The persistent class for the invoice_vendor database table.
 * 
 */
@Entity
@Table(name="invoice_vendor")
@IdClass(InvoicePK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@ToString
@NamedQuery(name="InvoiceVendor.findAll", query="SELECT i FROM InvoiceVendor i")
public class InvoiceVendor extends PanacheEntityBase implements Serializable {

	@Id
	@Column(name="invoice_number")
	public String invoiceNumber;

	@Id
	@Column(name="scan_id")
	public String scanId;

	public String address;

	@Column(name="bank_account")
	public String bankAccount;

	public String country;

	@Column(name="email_address")
	public String emailAddress;

	public String iban;

	public String name;

	@Column(name="po_vendor")
	public String poVendor;

	@Column(name="reporting_country_code")
	public String reportingCountryCode;

	@Column(name="vat_number")
	public String vatNumber;

	@Column(name="vendor_id")
	public String vendorId;

	//bi-directional one-to-one association to Invoice
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="invoice_number", referencedColumnName="invoice_number", insertable =  false, updatable = false),
		@JoinColumn(name="scan_id", referencedColumnName="scan_id", insertable =  false, updatable = false)
		})
	@JsonbTransient
	public Invoice invoice;

}