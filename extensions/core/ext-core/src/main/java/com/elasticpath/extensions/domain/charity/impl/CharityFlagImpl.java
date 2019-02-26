package com.elasticpath.extensions.domain.charity.impl;

import com.elasticpath.extensions.domain.charity.CharityFlag;
import com.elasticpath.persistence.api.AbstractEntityImpl;
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
 * TnC implementation.
 */
@Entity
@Table(name = CharityFlagImpl.TABLE_NAME)
@DataCache(enabled = true)
public class CharityFlagImpl extends AbstractEntityImpl implements CharityFlag {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 5000000001L;

    /**
     * Database Table.
     */
    public static final String TABLE_NAME = "TCHARITY";

    private String guid;
    private long uidpk;
    private String charity;

    @Basic
    @Column(name = "CHARITY")
    @Override
    public String getCharity() {
        return charity;
    }

    @Override
    public void setCharity(final String charity) {
        this.charity = charity;
    }



    /**
     * Return the guid.
     *
     * @return the guid.
     */
    @Basic
    @Column(name = "GUID")
    @Override
    public String getGuid() {
        return guid;
    }

    /**
     * Set the guid.
     *
     * @param guid the guid to set.
     */
    @Override
    public void setGuid(final String guid) {
        this.guid = guid;
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
        return uidpk;
    }

    /**
     * Sets the unique identifier for this domain model object.
     *
     * @param uidPk the new unique identifier.
     */
    @Override
    public void setUidPk(final long uidPk) {
        this.uidpk = uidPk;
    }
}
