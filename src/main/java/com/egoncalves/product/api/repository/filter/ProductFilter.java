/**
 * 
 */
package com.egoncalves.product.api.repository.filter;

/**
 * @author esmael.pains
 *
 */
public class ProductFilter {

	private Long id;
	private boolean images;
	private boolean children;

	public ProductFilter() {
		super();
	}

	public ProductFilter(Long id, boolean images, boolean children) {
		super();
		this.id = id;
		this.images = images;
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isImages() {
		return images;
	}

	public void setImages(boolean images) {
		this.images = images;
	}

	public boolean isChildren() {
		return children;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}

}
