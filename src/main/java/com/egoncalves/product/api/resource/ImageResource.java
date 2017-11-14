package com.egoncalves.product.api.resource;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.egoncalves.product.api.model.Image;
import com.egoncalves.product.api.model.Product;

@Produces("application/json")
public class ImageResource {
	
	private Product product;
	
	public ImageResource(Product product) {
		this.product = product;
	}
	
	@POST
	public Response newImage(Image image) {
		image.setId(1L);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(image.getId())
				.toUri();
		return Response.status(Status.CREATED).entity(image).contentLocation(uri).build();
	}
	
	@Path("/{id}")
	@PUT
	public Response updateImage(@PathParam("id") Long id, Image image) {

		return Response.ok(image).build();
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteProduct(@PathParam("id") Long id) {
		return Response.noContent().build();
	}
	
	@GET
	public Response getProductImages() {
		
		return Response.ok().build();
	}
	

}
