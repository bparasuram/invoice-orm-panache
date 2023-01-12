package org.acme.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class InvoiceItemPK implements Serializable {

    public String scanId;
    public String invoiceNumber;
    public Integer lineNumber;
    
}

