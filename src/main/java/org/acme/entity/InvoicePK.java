package org.acme.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class InvoicePK implements Serializable {
    
    public  String invoiceNumber;
    public  String scanId;
    
}
