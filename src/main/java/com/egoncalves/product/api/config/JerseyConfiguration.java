/**
 * 
 */
package com.egoncalves.product.api.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.egoncalves.product.api.resource.ProductResource;

/**
 * @author Esmael
 *
 */
@Component
public class JerseyConfiguration extends ResourceConfig{

	public JerseyConfiguration() {
		register(ProductResource.class);
	}
}
