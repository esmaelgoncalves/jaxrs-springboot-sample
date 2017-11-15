/**
 * 
 */
package com.egoncalves.product.api.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
public class ProductResourceTests {

	@TestConfiguration
	static class ProductResourceTestContextConfiguration {

		@Bean
		public ProductResource productResource() {
			return new ProductResource();
		}
	}

	@Autowired
	private ProductResource productResource;
	@MockBean
	private ProductRepository productRepository;
	@MockBean
	private ImageRepository imageRepository;
	@MockBean
	private ProductService productService;
	@MockBean
	private ImageService imageService;

	@Test
	public void testListAllProducts() {
		Product productOne = new Product(1L, "Sample Product", "Sample Description", null);
		Product productTwo = new Product(2L, "Sample Child", "Sample Description Child", productOne);

		List<Product> listProducts = new ArrayList<>();
		listProducts.add(productOne);
		listProducts.add(productTwo);

		Mockito.when(productService.filterProducts(any(ProductFilter.class))).thenReturn(listProducts);

		Response response = productResource.listAllProducts(false, false);

		List<Product> returnedEntities = (List<Product>) response.getEntity();

		assertEquals(200, response.getStatus());
		assertNotNull(returnedEntities);
		assertEquals(2, returnedEntities.size());
	}

	@Test
	public void testGetProductById() {
		Product productOne = new Product(1L, "Sample Product", "Sample Description", null);
		List<Product> listProducts = new ArrayList<>();
		listProducts.add(productOne);

		Mockito.when(productService.filterProducts(any(ProductFilter.class))).thenReturn(listProducts);

		Response response = productResource.getProductById(false, false, 1L);

		List<Product> returnedEntities = (List<Product>) response.getEntity();

		assertEquals(200, response.getStatus());
		assertNotNull(returnedEntities);
		assertEquals(1, returnedEntities.size());
	}

	@Test
	public void testGetProductByIdNotFound() {
		List<Product> listProducts = new ArrayList<>();

		Mockito.when(productService.filterProducts(any(ProductFilter.class))).thenReturn(listProducts);

		Response response = productResource.getProductById(false, false, 2L);

		List<Product> returnedEntities = (List<Product>) response.getEntity();

		assertEquals(404, response.getStatus());
		assertNotNull(returnedEntities);
		assertEquals(0, returnedEntities.size());
	}

	@Test
	public void testGetProductChildren() {
		List<Product> listProducts = new ArrayList<>();
		Product productTwo = new Product(2L, "Sample Child", "Sample Description Child", null);
		listProducts.add(productTwo);

		Mockito.when(productRepository.findByParentProduct(1L)).thenReturn(listProducts);

		Response response = productResource.getProductChildren(1L);

		List<Product> returnedEntities = (List<Product>) response.getEntity();

		assertEquals(200, response.getStatus());
		assertNotNull(returnedEntities);
		assertEquals(1, returnedEntities.size());
	}

	
	public void testNewProduct() {}
	
	public void testNewProductChild() {}
	
	@Test
	public void testUpdateProduct() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample", "Sample Child", null);
		Product productToSave = new Product(1L, "Sample Child Update", "Sample Description Child Update", null);
		
		Mockito.when(productService.findOneProduct(1L)).thenReturn(product);
		Mockito.when(productService.update(1L, productToSave)).thenReturn(productToSave);
		
		Response response = productResource.updateProduct(1L, productToSave);

		Product returnedEntitie = (Product) response.getEntity();

		assertEquals(200, response.getStatus());
		assertNotNull(returnedEntitie);
	}

}
