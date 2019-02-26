package com.elasticpath.extensions.service.charity;

import com.elasticpath.extensions.domain.charity.CharityFlag;

import java.util.List;

public interface CharityFlagService {
    /**
     * @param charityFlag charityFlag
     * @return CharityFlag
     */
    CharityFlag add(CharityFlag charityFlag);

    /**
     * @param charityFlag CharityFlag
     */
    void delete(CharityFlag charityFlag);

    /**
     * @param charity charity
     * @return CharityFlag
     */
    CharityFlag findByCode(String charity);

    /**
     * @return list of CharityFlag
     */
    List<CharityFlag> findAllCharityFlags();
}
