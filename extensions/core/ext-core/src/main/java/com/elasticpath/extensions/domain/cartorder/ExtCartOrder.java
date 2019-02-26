/*
 * Copyright © 2018 Elastic Path Software Inc. All rights reserved.
 */

package com.elasticpath.extensions.domain.cartorder;

import com.elasticpath.domain.cartorder.CartOrder;
import com.elasticpath.extensions.domain.charity.CartOrderCharityFlag;

import java.util.Map;

/**
 * Interface ExtCartOrder.
 */
public interface ExtCartOrder extends CartOrder {
	/**
	 * @return map of string and CartOrderCharityFlag
	 */
	Map<String, CartOrderCharityFlag> getCartOrderCharityFlags();

	/**
	 * @param cartOrderCharityFlags map of string and CartOrderCharityFlag
	 */
	void setCartOrderCharityFlags(Map<String, CartOrderCharityFlag> cartOrderCharityFlags);

	/**
	 * @param cartOrderCharityFlag cartOrderCharityFlag
	 */
	void addCartOrderCharityFlag(CartOrderCharityFlag cartOrderCharityFlag);

	/**
	 * @param charity charity
	 */
	void removeCartOrderCharityFlag(String charity);
}
