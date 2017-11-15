/**
 * 
 */
package com.egoncalves.product.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.egoncalves.product.api.model.Image;
import com.egoncalves.product.api.repository.ImageRepository;

/**
 * @author Esmael
 *
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class ImageRepositoryTests {
	
	@Autowired
	private ImageRepository repository;
	
	@Test
	public void testNewImage() {
		Image image = repository.save(new Image(null, "location/usr/img_001", null));
		
		assertNotNull(image);
		assertNotNull(image.getId());
	}
	
	@Test
	public void testFindByParentProduct() {
		List<Image> images = repository.findByProductParentId(3L);
		
		assertEquals(2, images.size());
	}
	
}
