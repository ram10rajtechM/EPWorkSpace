/*
 * Copyright © 2018 Elastic Path Software Inc. All rights reserved.
 */

package com.elasticpath.extensions.domain.charity.impl;

import com.elasticpath.extensions.domain.charity.CartOrderCharityFlag;
import com.elasticpath.persistence.api.AbstractPersistableImpl;
import org.apache.openjpa.persistence.DataCache;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * CartOrderCharityFlagImpl by extending AbstractPersistableImpl.
 */
@Entity
@Table(name = CartOrderCharityFlagImpl.TABLE_NAME)
@DataCache(enabled = false)
public final class CartOrderCharityFlagImpl extends AbstractPersistableImpl implements CartOrderCharityFlag {
	private static final long serialVersionUID = 5000000001L;

	/**
	 * The name of the DB table used to persist this object.
	 */
	static final String TABLE_NAME = "TCARTORDERCHARITY";

	private long uidPk;
	private String amount;
	private boolean accepted;
	private String charity;

	@Basic
	@Column(name = "AMOUNT")
	@Override
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount amount
	 */
	@Override
	public void setAmount(final String amount) {
		this.amount = amount;
	}

	@Basic
	@Column(name = "CHARITY")
	@Override
	public String getCharity() {
		return charity;
	}

	/**
	 * @param charity charity
	 */
	@Override
	public void setCharity(final String charity) {
		this.charity = charity;
	}

	@Basic
	@Column(name = "ACCEPTED")
	@Override
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted accepted
	 */
	@Override
	public void setAccepted(final boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * Gets the unique identifier for this domain object. This unique identifier is system-dependent. That means on different systems(like staging
	 * and production environments), different identifiers might be assigned to the same(from business perspective) domain object.
	 * <p>
	 * Notice: not all persistent domain objects has unique identifier. Some value objects don't have unique identifier. They are cascade loaded and
	 * updated through their parents.
	 *
	 * @return the unique identifier.
	 */
	@Id
	@Column(name = "UIDPK")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = TABLE_NAME)
	@TableGenerator(name = TABLE_NAME, table = "JPA_GENERATED_KEYS", pkColumnName = "ID", valueColumnName = "LAST_VALUE", pkColumnValue =
			TABLE_NAME)
	@Override
	public long getUidPk() {
		return uidPk;
	}

	/**
	 * Sets the unique identifier for this domain model object.
	 *
	 * @param uidPk the new unique identifier.
	 */
	@Override
	public void setUidPk(final long uidPk) {
		this.uidPk = uidPk;
	}
}
