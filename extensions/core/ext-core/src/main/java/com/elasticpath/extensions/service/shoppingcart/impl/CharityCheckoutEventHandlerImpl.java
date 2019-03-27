/**
 * Copyright (c) Elastic Path Software Inc., 2011
 */
package com.elasticpath.extensions.service.shoppingcart.impl;

import com.elasticpath.domain.order.Order;
import com.elasticpath.domain.order.OrderPayment;
import com.elasticpath.domain.shoppingcart.ShoppingCart;
import com.elasticpath.service.shoppingcart.CheckoutEventHandler;
import com.elasticpath.service.shoppingcart.impl.AbstractCheckoutEventHandlerImpl;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Implementation of {@link CheckoutEventHandler} that utilizes the composite design
 * pattern to allow multiple {@link CheckoutEventHandler} instances to be invoked during
 * the Checkout process.
 */
public class CharityCheckoutEventHandlerImpl extends AbstractCheckoutEventHandlerImpl {



	private static final Logger LOG = Logger.getLogger(CharityCheckoutEventHandlerImpl.class);

	/**
	 * <p>
	 * This event occurs after a checkout has been processed but before the order has been persisted, between preCheckout and postCheckout.
	 * Iterates through the list of {@link CheckoutEventHandler}s and invokes preCheckoutOrderPersist() on each instance.
	 * </p>
	 * <p>
	 * Note that the first exception encountered is immediately thrown upwards to the
	 * caller. No subsequent {@link CheckoutEventHandler} instances will be invoked from
	 * that point onward.
	 * </p>
	 * 
	 * @param shoppingCart the {@link ShoppingCart} being checked out
	 * @param orderPayment information about the method of payment
	 * @param completedOrder the order object resulting from the checkout
	 */
	@Override
	public void preCheckoutOrderPersist(final ShoppingCart shoppingCart,
										final Collection<OrderPayment> orderPayment, final Order completedOrder) {
	   // LOG.info("Cart Order Service :: "+getCartOrderService());
		//LOG.info("Charity Object: "+getCartOrderCharityFlag());
	    //LOG.info("Amount of Charity : "+cartOrderCharityFlag.getAmount());
	   // if(cartOrderService != null){
	    	//LOG.info("Cart Order Guid : "+ completedOrder.getCartOrderGuid());

	    	//LOG.info("Cart Order uidpk : "+getCartOrderService().findByShoppingCartGuid(completedOrder.getCartOrderGuid()).getUidPk());

        //}
		//LOG.info("PreCheckoutOrderPersist Checkout Object "+shoppingCart);
		//LOG.info("Object cartOrderCharityFlag : "+cartOrderCharityFlag);
		//LOG.info("Shopping cart guid : "+completedOrder.getCartOrderGuid());
		//LOG.info("cartOrder Object : "+cartOrderService.getCartOrderGuidByShoppingCartGuid(completedOrder.getCartOrderGuid()));
		//CartOrderCharityFlag cartOrderCharityFlag = cartOrderCharityFlagService.findByCartOrderGuid("350000");
		//LOG.info("CartOrder Object "+cartOrderCharityFlag);
	    // LOG.info("Shopping cart id : "+ shoppingCart.getGuid());
		 //LOG.info("cart uidpk :  "+cartOrder.getUidPk());
		 //CartOrderCharityFlag cartOrderCharityFlag = cartOrderCharityFlagService.findByCartOrderGuid(shoppingCart.get());
	}

}