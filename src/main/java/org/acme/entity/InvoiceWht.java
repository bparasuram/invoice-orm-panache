package org.acme.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the invoice_wht database table.
 * 
 */
@Entity
@Table(name="invoice_wht")
@IdClass(InvoiceWhtPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@ToString
//@NamedQuery(name="InvoiceWht.findAll", query="SELECT i FROM InvoiceWht i")
@NamedQueries(value = {
    @NamedQuery(name = "InvoiceWht.getById", 
    query = "SELECT i FROM InvoiceWht i  where i.invoiceNumber = ?1 and i.scanId = ?2")
})
public class InvoiceWht extends PanacheEntityBase implements Serializable {
	
	@Id
	@Column(name="invoice_number")
	public String invoiceNumber;

	@Id
	@Column(name="scan_id")
	public String scanId;

	@Id
	@Column(name="tax_code")
	public String taxCode;

	@Column(name="amount_calculated")
	public BigDecimal amountCalculated;

	@Column(name="base_amount")
	public BigDecimal baseAmount;

	@Column(name="base_amount_type")
	public String baseAmountType;

	public String description;

	@Column(name="tax_percent")
	public BigDecimal taxPercent;

	//bi-directional many-to-one association to Invoice
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="invoice_number", referencedColumnName="invoice_number", insertable =  false, updatable = false),
		@JoinColumn(name="scan_id", referencedColumnName="scan_id", insertable =  false, updatable = false)
		})
	@JsonbTransient
	public Invoice invoice;

	
}