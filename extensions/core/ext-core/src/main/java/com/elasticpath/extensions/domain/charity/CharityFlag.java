package com.elasticpath.extensions.domain.charity;

import com.elasticpath.persistence.api.Entity;
/*Interface for charity flag **/
public interface CharityFlag extends Entity {
    /* return code */
    String getCharity();

    /*
    @param charity
     */
    void setCharity(String charity);
}
