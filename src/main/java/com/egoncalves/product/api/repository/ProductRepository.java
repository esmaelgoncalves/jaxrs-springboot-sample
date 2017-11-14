/**
 * 
 */
package com.egoncalves.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.product.ProductRepositoryQuery;

/**
 * @author Esmael
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQuery{
	
	@Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.parent parent WHERE parent.id = ?1")
	List<Product> findByParentProduct(Long id);
	
}
