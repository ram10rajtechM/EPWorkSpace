/*
 * Copyright © 2018 Elastic Path Software Inc. All rights reserved.
 */

package com.elasticpath.extensions.service.shoppingcart.impl;

import com.elasticpath.domain.customer.Customer;
import com.elasticpath.domain.customer.CustomerSession;
import com.elasticpath.domain.order.Order;
import com.elasticpath.domain.shoppingcart.ShoppingCart;
import com.elasticpath.domain.shoppingcart.ShoppingCartTaxSnapshot;
import com.elasticpath.extensions.domain.cartorder.ExtCartOrder;
import com.elasticpath.extensions.domain.charity.CartOrderCharityFlag;
import com.elasticpath.service.shoppingcart.impl.OrderFactoryImpl;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * ExtOrderFactoryImpl extends OrderFactoryImpl.
 */
public class ExtOrderFactoryImpl extends OrderFactoryImpl {

	private static  final Logger LOG = Logger.getLogger(ExtOrderFactoryImpl.class);
	/**
	 * Populates all the fields of the order. Creates order shipments and sets the corresponding order skus.
	 *
	 * @param order                   order to populate fields on
	 * @param shoppingCart            shopping cart to get fields from
	 * @param pricingSnapshot         the shopping cart pricing snapshot
	 * @param customerSession         used to populate the order with details from the customer's current shopping session
	 * @param customer                customer
	 * @param isExchangeOrder         true if order is an exchange. false otherwise
	 * @param awaitExchangeCompletion true if order is awaiting exchange completion. false otherwise
	 */
	@Override
	protected void fillInOrderDetails(final Order order, final ShoppingCart shoppingCart, final ShoppingCartTaxSnapshot pricingSnapshot,
			final CustomerSession customerSession, final Customer customer, final boolean isExchangeOrder, final boolean awaitExchangeCompletion) {
		super.fillInOrderDetails(order, shoppingCart, pricingSnapshot, customerSession, customer, isExchangeOrder, awaitExchangeCompletion);
		ExtCartOrder extCartOrder = (ExtCartOrder) getCartOrderService().findByShoppingCartGuid(shoppingCart.getGuid());
		LOG.info("At the time order placed it will be called");
		if (extCartOrder != null) {
			Map<String, CartOrderCharityFlag> cartOrderCharityFlags = extCartOrder.getCartOrderCharityFlags();

			for (Map.Entry<String, CartOrderCharityFlag> entry : cartOrderCharityFlags.entrySet()) {
				order.setFieldValue(entry.getValue().getCharity(), Boolean.toString(entry.getValue().isAccepted()).toUpperCase());
			}
			LOG.info("End of the fuction");
		}
	}
}
