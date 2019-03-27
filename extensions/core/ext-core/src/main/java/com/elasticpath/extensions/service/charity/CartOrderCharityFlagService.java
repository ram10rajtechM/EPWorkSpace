package com.elasticpath.extensions.service.charity;

import com.elasticpath.extensions.domain.charity.CartOrderCharityFlag;
import com.elasticpath.extensions.domain.charity.CharityFlag;

import java.util.List;

public interface CartOrderCharityFlagService {
    /**
     * @param cartOrderCharityFlag cartOrderCharityFlag
     * @return CartOrderCharityFlag
     */
    CartOrderCharityFlag add(CartOrderCharityFlag cartOrderCharityFlag);

    /**
     * @param cartOrderCharityFlag cartOrderCharityFlag
     */
    void delete(CartOrderCharityFlag cartOrderCharityFlag);

    /**
     * @param guid guid
     * @return CartOrderCharityFlag
     */
    CartOrderCharityFlag findByCartOrderGuid(String guid);

    /**
     * @return list of CartOrderCharityFlag
     */
    List<CartOrderCharityFlag> findAllCharityCartOrderFlags();
}
