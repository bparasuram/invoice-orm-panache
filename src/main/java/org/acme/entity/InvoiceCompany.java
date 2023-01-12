package org.acme.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The persistent class for the invoice_company database table.
 * 
 */
@Entity
@Table(name="invoice_company")
@IdClass(InvoicePK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@ToString
@NamedQuery(name="InvoiceCompany.findAll", query="SELECT i FROM InvoiceCompany i")
public class InvoiceCompany extends PanacheEntityBase implements Serializable {

	@Id
	@Column(name="invoice_number")
	public String invoiceNumber;

	@Id
	@Column(name="scan_id")
	public String scanId;

	public String address;

	public String city;

	@Column(name="company_code")
	public String companyCode;

	@Column(name="company_name")
	public String companyName;

	@Column(name="company_vat")
	public String companyVat;

	public String country;

	public String currency;

	@Column(name="zip_code")
	public String zipCode;

	//bi-directional one-to-one association to Invoice
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="invoice_number", referencedColumnName="invoice_number"),
		@JoinColumn(name="scan_id", referencedColumnName="scan_id")
		})
	@JsonbTransient
	public Invoice invoice;

}