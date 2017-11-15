/**
 * 
 */
package com.egoncalves.product.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

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

import com.egoncalves.product.api.exception.ImageNotFoundException;
import com.egoncalves.product.api.exception.ProductNotFoundException;
import com.egoncalves.product.api.model.Image;
import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.ImageRepository;
import com.egoncalves.product.api.repository.ProductRepository;

/**
 * @author Esmael
 *
 */
@RunWith(SpringRunner.class)
public class ImageServiceTests {

	@TestConfiguration
	static class ImageServiceTestContextConfiguration {

		@Bean
		public ImageService imageService() {
			return new ImageService();
		}
	}

	@Autowired
	private ImageService imageService;

	@MockBean
	private ProductRepository productRepository;
	@MockBean
	private ImageRepository imageRepository;

	@Before
	public void setUp() {

	}

	@Test
	public void testSaveProductImage() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample Child", "Sample Description Child", null);
		Image image = new Image(1L, "location/img001", product);
		Image imageToSave = new Image(null, "location/img001", null);
		
		Mockito.when(productRepository.findOne(1L)).thenReturn(product);
		Mockito.when(imageRepository.save(imageToSave)).thenReturn(image);

		Image savedImage = imageService.saveProductImage(1L, imageToSave);

		assertNotNull(savedImage.getId());
		assertNotNull(savedImage.getProduct());
		assertSame(product, savedImage.getProduct());
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void testSaveProductImageProductNotFound() throws ProductNotFoundException {
		Product product = new Product(1L, "Sample Child", "Sample Description Child", null);
		Image image = new Image(1L, "location/img001", product);
		Image imageToSave = new Image(null, "location/img001", null);
		
		Mockito.when(productRepository.findOne(1L)).thenReturn(product);
		Mockito.when(imageRepository.save(imageToSave)).thenReturn(image);

		imageService.saveProductImage(2L, imageToSave);
	}



	@Test
	public void testUpdateImage() throws ImageNotFoundException {
		Image image = new Image(1L, "location/img001", null);
		Image imageToSave = new Image(1L, "location/img001/alterado", null);

		Mockito.when(imageRepository.findOne(1L)).thenReturn(image);
		Mockito.when(imageRepository.save(imageToSave)).thenReturn(imageToSave);

		Image savedImage = imageService.update(1L, imageToSave);

		assertEquals(savedImage.getPath(), image.getPath());
	}

	@Test(expected = ImageNotFoundException.class)
	public void testImageProductNotFount() throws ImageNotFoundException {
		Image image = new Image(1L, "location/img001", null);
		Image imageToSave = new Image(1L, "location/img001/alterado", null);

		Mockito.when(imageRepository.findOne(1L)).thenReturn(image);

		imageService.update(2L, imageToSave);
	}

	@Test
	public void testDeleteImage() throws ImageNotFoundException {
		Image image = new Image(1L, "location/img001", null);

		Mockito.when(imageRepository.findOne(1L)).thenReturn(image);
		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				System.out.println("called with arguments: " + Arrays.toString(args));
				return null;
			}
		}).when(imageRepository).delete(1L);

		imageService.delete(1L);
	}

	@Test(expected = ImageNotFoundException.class)
	public void testDeleteImageNotFound() throws ImageNotFoundException {
		Image image = new Image(1L, "location/img001", null);

		Mockito.when(imageRepository.findOne(1L)).thenReturn(image);

		imageService.delete(2L);
	}

}
