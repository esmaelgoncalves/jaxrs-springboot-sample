/**
 * 
 */
package com.egoncalves.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoncalves.product.api.model.Image;

/**
 * @author Esmael
 *
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

}
