/**
 * 
 */
package com.egoncalves.product.api.repository.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.filter.ProductFilter;

/**
 * @author esmael.pains
 *
 */
public class ProductRepositoryImpl implements ProductRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Product> filter(ProductFilter productFilter) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT DISTINCT p FROM Product p ");

		createFetchJoins(productFilter, queryBuilder);

		createRestriction(productFilter, queryBuilder);

		TypedQuery<Product> query = (TypedQuery<Product>) manager.createQuery(queryBuilder.toString());

		if (productFilter.getId() != null) {
			query.setParameter("id", productFilter.getId());
		}

		return query.getResultList();
	}

	private void createFetchJoins(ProductFilter productFilter, StringBuilder queryBuilder) {

		if (productFilter.isChildren()) {
			queryBuilder.append(" LEFT JOIN FETCH p.children ");
		}

		if (productFilter.isImages()) {
			queryBuilder.append(" LEFT JOIN FETCH p.images ");
		}
	}

	private void createRestriction(ProductFilter productFilter, StringBuilder queryBuilder) {
		if (productFilter.getId() != null) {
			queryBuilder.append(" WHERE p.id = :id ");
		}

		queryBuilder.append(" ORDER BY p.id ");
	}

}
