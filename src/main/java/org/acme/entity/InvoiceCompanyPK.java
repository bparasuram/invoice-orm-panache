package org.acme.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the invoice_company database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class InvoiceCompanyPK implements Serializable {
	
	public String invoiceNumber;
	public String scanId;
}