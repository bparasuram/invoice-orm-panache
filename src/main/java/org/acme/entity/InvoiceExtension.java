package org.acme.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the invoice_extensions database table.
 * 
 */
@Entity
@Table(name="invoice_extensions")
@IdClass(InvoiceExtensionPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@NamedQuery(name="InvoiceExtension.findAll", query="SELECT i FROM InvoiceExtension i")
public class InvoiceExtension extends PanacheEntityBase implements Serializable {

	@Id
	@Column(name="invoice_number", insertable=false, updatable=false)
	public String invoiceNumber;

	@Id
	@Column(name="scan_id", insertable=false, updatable=false)
	public String scanId;

	public String action;

	@Column(name="assigned_to")
	public String assignedTo;

	@Column(name="assigned_to_team")
	public String assignedToTeam;

	@Column(name="baw_case_id")
	public String bawCaseId;

	@Column(name="business_process_id")
	public BigDecimal businessProcessId;

	@Column(name="client_id")
	public String clientId;

	@Column(name="duplicate_status")
	public String duplicateStatus;

	@Column(name="erp_posting_number")
	public String erpPostingNumber;

	@Column(name="erp_system")
	public String erpSystem;

	@Column(name="exception_type")
	public String exceptionType;

	@Column(name="filenet_docid")
	public String filenetDocid;

	@Column(name="foreign_supplier_status")
	public String foreignSupplierStatus;

	@Column(name="posted_idoc_id")
	public String postedIdocId;

	public String reason;

	@Column(name="validation_messages")
	public String validationMessages;

	@Column(name="validation_messages_lines")
	public String validationMessagesLines;

	@OneToOne
	@MapsId
	@JoinColumns({
        @JoinColumn(name="invoice_number", referencedColumnName="invoice_number", insertable =  false, updatable = false),
        @JoinColumn(name="scan_id", referencedColumnName="scan_id", insertable =  false, updatable = false)
    })
    @JsonbTransient
    public Invoice invoice;
	
}