/**
 * 
 */
package com.egoncalves.product.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoncalves.product.api.exception.ProductNotFoundException;
import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.ProductRepository;
import com.egoncalves.product.api.repository.filter.ProductFilter;

/**
 * @author esmael.pains
 *
 */
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public Product saveProductChild(Long id, Product product) throws ProductNotFoundException {
		Product savedProduct = findOneProduct(id);
		product.setParent(savedProduct);
		
		return productRepository.save(product);
	}
	
	public Product update(Long id, Product product) throws ProductNotFoundException {
		Product savedProduct = findOneProduct(id);
		
		BeanUtils.copyProperties(product, savedProduct, "id", "children", "images");
		return productRepository.save(savedProduct);
	}
	
	public void delete(Long id) throws ProductNotFoundException {
		Product savedProduct = findOneProduct(id);
		
		productRepository.delete(savedProduct);
	}
	
	public List<Product> filterProducts(ProductFilter productFilter){
		List<Product> list = productRepository.filter(productFilter);
		
		return list;
	}
	
	public Product findOneProduct(Long id) throws ProductNotFoundException {
		Product savedProduct = productRepository.findOne(id);
		
		if(savedProduct == null) {
			throw new ProductNotFoundException();
		}
		return savedProduct;
	}
}
