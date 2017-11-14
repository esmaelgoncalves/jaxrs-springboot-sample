/**
 * 
 */
package com.egoncalves.product.api.resource;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.egoncalves.product.api.model.Product;

/**
 * @author Esmael
 *
 */
@Component
@Path("/products")
@Produces("application/json")
public class ProductResource {

	@GET
	public Response listAllProducts(@QueryParam("images") boolean images, @QueryParam("children") boolean children) {
		List<Product> products = Arrays.asList(new Product(1L, "Panela", null), new Product(2L, "Colher", null));

		System.out.println("images: " + images);
		System.out.println("children: " + children);

		return Response.ok(products).build();
	}

	@Path("/{id}/childs")
	@GET
	public Response getProductChildren(@PathParam("id") Long id) {

		return Response.ok(new Product(1L, "qUALQUER COISA", "descrição")).build();
	}
	
	@Path("/{id}")
	@GET
	public Response getProductById(@QueryParam("images") boolean images, @QueryParam("children") boolean children,
			@PathParam("id") Long id) {

		return Response.ok(new Product(1L, "qUALQUER COISA", "descrição")).build();
	}

	@POST
	public Response newProduct(Product product) {
		product.setId(1L);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(product.getId())
				.toUri();
		return Response.status(Status.CREATED).entity(product).contentLocation(uri).build();
	}
	
	@Path("/{id}")
	@POST
	public Response newProductChild(@PathParam("id") Long id, Product product) {
		product.setId(1L);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(product.getId())
				.toUri();
		return Response.status(Status.CREATED).entity(product).contentLocation(uri).build();
	}

	@Path("/{id}")
	@PUT
	public Response updateProduct(@PathParam("id") Long id, Product product) {

		return Response.ok(product).build();
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteProduct(@PathParam("id") Long id) {
		return Response.noContent().build();
	}
	
	@Path("/{id}/images")
	public ImageResource getImageSubResource(@PathParam("id")Long id) {
		Product p = new Product(id, "Produto1", "Descrição");
		return new ImageResource(p);
	}
}
