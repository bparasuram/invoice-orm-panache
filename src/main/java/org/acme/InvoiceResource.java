package org.acme;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.entity.Invoice;
import org.acme.entity.InvoiceList;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;


@Path("/v1/adapt")
public class InvoiceResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> createInvoice(Invoice invoice) {
        if(invoice == null || invoice.invoiceNumber == null || invoice.scanId == null) {
            throw new WebApplicationException("Invoice data insufficient", 422);
        }
        return Invoice.createInvoice(invoice)
                .onItem().transform(id -> URI.create("/v1/adapt/"+id.invoiceNumber+"/"+id.scanId))
                .onItem().transform(uri -> Response.created(uri))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    // @GET
    // @Path("{invoiceId}/{scanId}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Uni<Response> getInvoice(@PathParam("invoiceId") String invoiceId, @PathParam("scanId") String scanId) {
    //     return Invoice.findByInvoiceId(invoiceId, scanId)
    //             .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
    //             .onItem().ifNull().continueWith(Response.ok().status(Status.NOT_FOUND)::build);

    // }   
    
    @GET
    @Path("{companyCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getInvoiceByCompanyCode(@PathParam("companyCode") String companyCode) {
        InvoiceList iList = new InvoiceList();
        Log.info("*** FETCHING INVOICE *** ");
        return Invoice.findByCompanyCode(companyCode).onItem()
        .invoke(list -> iList.instances = list).onItem()
        .transform(list -> iList).onItem()
        .ifNotNull().transform(entity -> Response.ok(entity).build())
        .onItem().ifNull().continueWith(Response.ok().status(Status.NOT_FOUND)::build);
        //invoke(list -> iList.instances = list)
        // return Uni.createFrom().item(iList)
        // .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
        // .onItem().ifNull().continueWith(Response.ok().status(Status.NOT_FOUND)::build);

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ReactiveTransactional
    public Uni<Response> saveInvoice(Invoice invoice) {
        if(invoice == null || invoice.invoiceNumber == null || invoice.scanId == null) {
            throw new WebApplicationException("Invoice data insufficient", 422);
        }
        return Invoice.saveInvoice(invoice).replaceWith(invoice)    
                .onItem().transform(id -> URI.create("/v1/adapt/"+id.invoiceNumber+"/"+id.scanId))
                .onItem().transform(uri -> Response.accepted(uri))
                .onItem().transform(Response.ResponseBuilder::build);
    }


}