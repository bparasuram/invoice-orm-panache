package org.acme.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the invoice_extensions database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InvoiceExtensionPK implements Serializable {

	public String invoiceNumber;
	public String scanId;

	
}