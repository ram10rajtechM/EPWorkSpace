/*
 * Copyright © 2018 Elastic Path Software Inc. All rights reserved.
 */

package com.elasticpath.extensions.domain.cartorder.impl;

import com.elasticpath.domain.cartorder.impl.CartOrderImpl;
import com.elasticpath.extensions.domain.cartorder.ExtCartOrder;
import com.elasticpath.extensions.domain.charity.CartOrderCharityFlag;
import com.elasticpath.extensions.domain.charity.impl.CartOrderCharityFlagImpl;
import org.apache.openjpa.persistence.DataCache;
import org.apache.openjpa.persistence.ElementDependent;
import org.apache.openjpa.persistence.jdbc.ElementForeignKey;
import org.apache.openjpa.persistence.jdbc.ElementJoinColumn;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.Map;

/**
 * ExtCartOrderImpl extending CartOrderImpl.
 */
@Entity
@DiscriminatorValue("CHARITY")
@DataCache(enabled = false)
public final class ExtCartOrderImpl extends CartOrderImpl implements ExtCartOrder {
	private static final long serialVersionUID = 5000000001L;

	private Map<String, CartOrderCharityFlag> cartOrderCharityFlags =
			new HashMap<String, CartOrderCharityFlag>();

	/**
	 * @return map of string and CartOrderCharityFlag
	 */
	@OneToMany(targetEntity = CartOrderCharityFlagImpl.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@MapKey(name = "charity")
	@ElementJoinColumn(name = "CARTORDER_UID", nullable = false)
	@ElementForeignKey(name = "FK_TCARTORDERCHARITY_CARTORDER")
	@ElementDependent
	@Override
	public Map<String, CartOrderCharityFlag> getCartOrderCharityFlags() {
		return cartOrderCharityFlags;
	}

	/**
	 * @param cartOrderCharityFlags map of string and CartOrderCharityFlag
	 */
	@Override
	public void setCartOrderCharityFlags(final Map<String, CartOrderCharityFlag> cartOrderCharityFlags) {
		this.cartOrderCharityFlags = cartOrderCharityFlags;
	}

	@Override
	public void addCartOrderCharityFlag(final CartOrderCharityFlag cartOrderCharityFlag) {
		this.getCartOrderCharityFlags().put(cartOrderCharityFlag.getCharity(), cartOrderCharityFlag);
	}

	@Override
	public void removeCartOrderCharityFlag(final String charity) {
		this.getCartOrderCharityFlags().remove(charity);
	}
}
