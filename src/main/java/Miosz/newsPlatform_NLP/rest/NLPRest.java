package Miosz.newsPlatform_NLP.rest;

import Miosz.newsPlatform_NLP.BLL.BasicNER;

import java.io.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/NLP")
public class NLPRest{

    @GET
    @Produces(MediaType.TEXT_HTML)
    public void get() {
        System.out.println("NLP up");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearch(@PathParam("id") String newsId){
        System.out.println("Starting Finder for: " + newsId);
        new BasicNER().entityFinder(newsId);

        return Response.ok(MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

}