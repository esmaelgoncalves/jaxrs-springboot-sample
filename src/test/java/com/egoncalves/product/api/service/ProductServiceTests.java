/**
 * 
 */
package com.egoncalves.product.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.egoncalves.product.api.exception.ProductNotFoundException;
import com.egoncalves.product.api.model.Image;
import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.ProductRepository;
import com.egoncalves.product.api.repository.filter.ProductFilter;

/**
 * @author Esmael
 *
 */
@RunWith(SpringRunner.class)
public class ProductServiceTests {

	@TestConfiguration
	static class ProductServiceTestContextConfiguration {

		@Bean
		public ProductService productService() {
			return new ProductService();
		}
	}

	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	@Before
	public void setUp() {

	}

	@Test
	public void testSaveProduct() {
		Product product = new Product(2L, "Sample Child", "Sample Description Child", null);
		Product productToSave = new Product(null, "Sample Child", "Sample Description Child", null);

		Mockito.when(productRepository.save(productToSave)).thenReturn(product);

		Product savedProduct = productService.save(productToSave);

		assertNotNull(savedProduct.getId());
	}

	@Test
	public void testSaveProductChild() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample", "Sample Child", null);
		Product productToSave = new Product(null, "Sample Child", "Sample Description Child", product);
		Product productReturn = new Product(2L, "Sample Child", "Sample Description Child", product);

		Mockito.when(productRepository.findOne(1L)).thenReturn(product);
		Mockito.when(productRepository.save(productToSave)).thenReturn(productReturn);

		Product savedProduct = productService.saveProductChild(1L, productToSave);

		assertNotNull(savedProduct.getId());
		assertEquals(savedProduct.getParent(), product);
	}

	@Test(expected = ProductNotFoundException.class)
	public void testSaveProductChildParentNotFount() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample", "Sample Child", null);
		Product productToSave = new Product(null, "Sample Child", "Sample Description Child", product);

		Mockito.when(productRepository.findOne(1L)).thenReturn(product);

		productService.saveProductChild(2L, productToSave);
	}

	@Test
	public void testUpdateProduct() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample", "Sample Child", null);
		Product productToSave = new Product(1L, "Sample Child Update", "Sample Description Child Update", null);

		Mockito.when(productRepository.findOne(1L)).thenReturn(product);
		Mockito.when(productRepository.save(productToSave)).thenReturn(productToSave);

		Product savedProduct = productService.update(1L, productToSave);

		assertEquals(savedProduct.getName(), product.getName());
	}

	@Test(expected = ProductNotFoundException.class)
	public void testUpdateProductNotFount() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample", "Sample Child", null);
		Product productToSave = new Product(1L, "Sample Child Update", "Sample Description Child Update", null);

		Mockito.when(productRepository.findOne(1L)).thenReturn(product);

		productService.update(2L, productToSave);
	}

	@Test
	public void testDeleteProduct() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample", "Sample Child", null);

		Mockito.when(productRepository.findOne(1L)).thenReturn(product);
		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				System.out.println("called with arguments: " + Arrays.toString(args));
				return null;
			}
		}).when(productRepository).delete(1L);

		productService.delete(1L);
	}

	@Test(expected = ProductNotFoundException.class)
	public void testDeleteProductNotFound() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample", "Sample Child", null);

		Mockito.when(productRepository.findOne(1L)).thenReturn(product);

		productService.delete(2L);
	}

	@Test
	public void testFilterAllProductsWithoutImagesAndChildren() {
		Product productOne = new Product(1L, "Sample Product", "Sample Description", null);
		Product productTwo = new Product(2L, "Sample Child", "Sample Description Child", productOne);

		List<Product> listProducts = new ArrayList<>();
		listProducts.add(productOne);
		listProducts.add(productTwo);

		ProductFilter productFilter = new ProductFilter(null, false, false);
		Mockito.when(productRepository.filter(productFilter)).thenReturn(listProducts);

		List<Product> allProducts = productService.filterProducts(productFilter);

		assertEquals(2, allProducts.size());
		assertNull(allProducts.get(0).getChildren());
		assertNull(allProducts.get(0).getImages());
	}

	@Test
	public void testFilterOneProductWithoutImagesAndChildren() {
		Product productOne = new Product(1L, "Sample Product", "Sample Description", null);

		List<Product> listProducts = new ArrayList<>();
		listProducts.add(productOne);

		ProductFilter productFilter = new ProductFilter(1L, false, false);
		Mockito.when(productRepository.filter(productFilter)).thenReturn(listProducts);

		List<Product> products = productService.filterProducts(productFilter);

		assertEquals(1, products.size());
		assertNull(products.get(0).getChildren());
		assertNull(products.get(0).getImages());
	}

	@Test
	public void testFilterOneProductWithImagesAndChildren() {
		Product productOne = new Product(1L, "Sample Product", "Sample Description", null);
		productOne.setChildren(prepareChildren(productOne));
		productOne.setImages(prepareImages(productOne));

		List<Product> listProducts = new ArrayList<>();
		listProducts.add(productOne);

		ProductFilter productFilter = new ProductFilter(1L, false, false);
		Mockito.when(productRepository.filter(productFilter)).thenReturn(listProducts);

		List<Product> products = productService.filterProducts(productFilter);

		assertEquals(1, products.size());
		assertNotNull(products.get(0).getChildren());
		assertNotNull(products.get(0).getImages());
		assertEquals(1L, products.get(0).getChildren().size());
		assertEquals(1L, products.get(0).getImages().size());
	}

	private Set<Product> prepareChildren(Product product) {
		Set<Product> products = new HashSet<>();
		products.add(new Product(2L, "Sample Child", "Sample Description Child", product));
		return products;
	}

	private Set<Image> prepareImages(Product product) {
		Set<Image> images = new HashSet<>();
		images.add(new Image(1L, "location/usr/img001", product));
		return images;
	}

}
