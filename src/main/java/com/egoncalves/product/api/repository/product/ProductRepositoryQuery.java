/**
 * 
 */
package com.egoncalves.product.api.repository.product;

import java.util.List;

import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.filter.ProductFilter;

/**
 * @author esmael.pains
 *
 */
public interface ProductRepositoryQuery {
	List<Product> filter(ProductFilter productFilter);
}
