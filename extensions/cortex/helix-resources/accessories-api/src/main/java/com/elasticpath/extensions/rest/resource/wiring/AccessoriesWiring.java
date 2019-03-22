package com.elasticpath.extensions.rest.resource.wiring;

import com.elasticpath.rest.definition.accessories.ItemRecommendationAccessoriesResource;
import com.elasticpath.rest.helix.api.AbstractHelixModule;

import javax.inject.Named;

@Named
public class AccessoriesWiring  extends AbstractHelixModule {

    protected String resourceName() {
        return ItemRecommendationAccessoriesResource.FAMILY;
    }
}
