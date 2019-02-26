/*
 * Copyright © 2018 Elastic Path Software Inc. All rights reserved.
 */

package com.elasticpath.extensions.domain.charity;

import com.elasticpath.persistence.api.Persistable;

/**
 * Interface CartOrderTermsAndConditionsFlag.
 */
public interface CartOrderCharityFlag extends Persistable {
	/**
	 * @return amount
	 */
	String getAmount();

	/**
	 * @param amount amount
	 */
	void setAmount(String amount);

	/**
	 * @return charity
	 */
	String getCharity();

	/**
	 * @param charity charity
	 */
	void setCharity(String charity);

	/**
	 * @return boolean value
	 */
	boolean isAccepted();

	/**
	 * @param accepted accepted
	 */
	void setAccepted(boolean accepted);
}
