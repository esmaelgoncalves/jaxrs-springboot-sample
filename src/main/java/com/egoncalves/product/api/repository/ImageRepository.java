/**
 * 
 */
package com.egoncalves.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoncalves.product.api.model.Image;

/**
 * @author Esmael
 *
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
	
	@Query("SELECT DISTINCT i FROM Image i LEFT JOIN FETCH i.product parent WHERE parent.id = ?1")
	List<Image> findByProductParentId(Long id);

}
