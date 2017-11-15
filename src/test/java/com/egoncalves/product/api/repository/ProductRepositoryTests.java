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

import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.ProductRepository;
import com.egoncalves.product.api.repository.filter.ProductFilter;

/**
 * @author Esmael
 *
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void testNewProduct() {
		Product product = repository.save(new Product(null, "Sample product", "Sample description product", null));
		
		assertNotNull(product);
		assertNotNull(product.getId());
	}
	
	@Test
	public void testFindByParentProduct() {
		List<Product> products = repository.findByParentProduct(1L);
		
		assertEquals(1L, products.size());
	}
	
	
	@Test
	public void testFilterProductWithImagesAndChildren() {
		ProductFilter productFilter = new ProductFilter(1L, true, true);
		List<Product> products = repository.filter(productFilter);
		
		assertNotNull(products.get(0).getChildren());
		assertNotNull(products.get(0).getImages());
	}
	
	@Test
	public void testFilterProductJustWithImages() {
		ProductFilter productFilter = new ProductFilter(1L, true, false);
		List<Product> products = repository.filter(productFilter);
		
		assertNotNull(products.get(0).getImages());
	}
	
	@Test
	public void testFilterProductJustWithChildren() {
		ProductFilter productFilter = new ProductFilter(1L, false, true);
		List<Product> products = repository.filter(productFilter);
		
		assertNotNull(products.get(0).getChildren());
	}


}
