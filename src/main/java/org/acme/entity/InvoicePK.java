package org.acme.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class InvoicePK implements Serializable {
    
    public  String invoiceNumber;
    public  String scanId;
    
}
