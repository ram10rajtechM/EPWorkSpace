package com.elasticpath.extensions.domain.shoppingcart.impl;

import com.elasticpath.domain.shoppingcart.impl.ShoppingCartImpl;
import com.elasticpath.money.Money;
import org.apache.log4j.Logger;

public class ExtShoppingCartImpl extends ShoppingCartImpl {
    private static final long serialVersionUID = 6000000001L;
    private static final Logger LOG = Logger.getLogger(ExtShoppingCartImpl.class.getName());

    @Override
    public Money getSubtotalMoney() {
        LOG.warn("Warning getSubtotalMoney caluculated");
        LOG.info("Before custom amount "+super.getSubtotalMoney());
        LOG.info("After custom amount "+super.getSubtotalMoney().add( Money.valueOf(100, getCustomerSession().getCurrency())));
        return super.getSubtotalMoney().add( Money.valueOf(100, getCustomerSession().getCurrency()));
    }

}
