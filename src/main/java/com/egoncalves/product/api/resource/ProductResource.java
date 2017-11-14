/**
 * 
 */
package com.egoncalves.product.api.resource;

import java.net.URI;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.egoncalves.product.api.exception.ProductNotFoundException;
import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.ImageRepository;
import com.egoncalves.product.api.repository.ProductRepository;
import com.egoncalves.product.api.repository.filter.ProductFilter;
import com.egoncalves.product.api.service.ImageService;
import com.egoncalves.product.api.service.ProductService;

/**
 * @author Esmael
 *
 */
@Component
@Path("/products")
@Produces("application/json")
public class ProductResource {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ImageService imageService;

	@GET
	public Response listAllProducts(@QueryParam("images") boolean images, @QueryParam("children") boolean children) {
		ProductFilter filter = new ProductFilter(null, images, children);
		List<Product> products = productService.filterProducts(filter);
		return Response.ok(products).build();
	}
	
	@Path("/{id}")
	@GET
	public Response getProductById(@QueryParam("images") boolean images, @QueryParam("children") boolean children,
			@PathParam("id") Long id) {
		
		ProductFilter filter = new ProductFilter(id, images, children);
		List<Product> products = productService.filterProducts(filter);
		
		return products.size() > 0 ? Response.ok(products).build() : Response.status(Response.Status.NOT_FOUND).entity(products).build();
	}
	
	@Path("/{id}/children")
	@GET
	public Response getProductChildren(@PathParam("id") Long id) {
		List<Product> products = productRepository.findByParentProduct(id);

		return Response.ok(products).build();
	}

	@POST
	public Response newProduct(Product product) {
		Product savedProduct = productService.save(product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(savedProduct.getId())
				.toUri();
		return Response.status(Status.CREATED).entity(savedProduct).contentLocation(uri).build();
	}
	
	@Path("/{id}")
	@POST
	public Response newProductChild(@PathParam("id") Long id, Product product) {
		try {
			Product savedProduct = productService.saveProductChild(id, product);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(savedProduct.getId())
					.toUri();
			return Response.status(Status.CREATED).entity(product).contentLocation(uri).build();
		} catch (ProductNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}

	@Path("/{id}")
	@PUT
	public Response updateProduct(@PathParam("id") Long id, Product product) {
		try {
			productService.update(id, product);
			return Response.ok(product).build();
		} catch (ProductNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteProduct(@PathParam("id") Long id) {
		try {
			productService.delete(id);
			return Response.noContent().build();
		} catch (ProductNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}	
	}
	
	@Path("/{id}/images")
	public ImageResource getImageSubResource(@PathParam("id")Long id) {
		Product p = productRepository.findOne(id);
		return new ImageResource(p, imageRepository, imageService);
	}
}
