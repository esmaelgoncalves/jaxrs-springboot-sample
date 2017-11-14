/**
 * 
 */
package com.egoncalves.product.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoncalves.product.api.exception.ImageNotFoundException;
import com.egoncalves.product.api.exception.ProductNotFoundException;
import com.egoncalves.product.api.model.Image;
import com.egoncalves.product.api.model.Product;
import com.egoncalves.product.api.repository.ImageRepository;
import com.egoncalves.product.api.repository.ProductRepository;

/**
 * @author esmael.pains
 *
 */
@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ProductRepository productRepository;

	public Image saveProductImage(Long id, Image image) throws ProductNotFoundException {
		Product savedProduct = productRepository.findOne(id);
		
		if (savedProduct == null) {
			throw new ProductNotFoundException();
		}
		
		image.setProduct(savedProduct);
		return imageRepository.save(image);
	}

	public Image update(Long id,  Image image) throws ImageNotFoundException {
		Image savedImage = findOneImage(id);

		BeanUtils.copyProperties(image, savedImage, "id", "product");
		return imageRepository.save(savedImage);

	}

	public void delete(Long id) throws ImageNotFoundException {
		Image savedImage = findOneImage(id);
		imageRepository.delete(savedImage);
	}

	private Image findOneImage(Long id) throws ImageNotFoundException {
		Image savedImage = imageRepository.findOne(id);

		if (savedImage == null) {
			throw new ImageNotFoundException();
		}
		return savedImage;
	}
}
