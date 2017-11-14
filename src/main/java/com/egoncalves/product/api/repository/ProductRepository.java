/**
 * 
 */
package com.egoncalves.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoncalves.product.api.model.Product;

/**
 * @author Esmael
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long>{

}
