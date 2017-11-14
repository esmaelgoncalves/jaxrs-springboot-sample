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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.egoncalves.product.api.exception.ImageNotFoundException;
import com.egoncalves.product.api.exception.ProductNotFoundException;
import com.egoncalves.product.api.model.Image;
import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.ImageRepository;
import com.egoncalves.product.api.service.ImageService;

@Produces("application/json")
public class ImageResource {
	
	private ImageRepository imageRepository;
	private ImageService imageService;
	private Product product;
	
	public ImageResource(Product product, ImageRepository imageRepository, ImageService imageService) {
		this.product = product;
		this.imageRepository = imageRepository;
		this.imageService = imageService;
	}
	
	@POST
	public Response newImage(Image image) {
		try {
			Image savedImage = imageService.saveProductImage(this.product.getId(), image);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(savedImage.getId())
					.toUri();
			return Response.status(Status.CREATED).entity(image).contentLocation(uri).build();
		} catch (ProductNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@Path("/{id}")
	@PUT
	public Response updateImage(@PathParam("id") Long id, Image image) {
		try {
			Image savedImage = imageService.update(id, image);
			return Response.ok().entity(savedImage).build();
		} catch (ImageNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteImage(@PathParam("id") Long id) {
		try {
			imageService.delete(id);
			return Response.noContent().build();
		} catch (ImageNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	public Response getProductImages() {
		List<Image> images = imageRepository.findByProductParentId(this.product.getId());
		return Response.ok(images).build();
	}
	

}
