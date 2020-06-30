package com.core.service;

import com.core.pojo.cart.UserCart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.pojo.cart.UserCart;
import com.core.pojo.order.Order;

/**
 * 订单模块
 *
 */


public interface OrderService {
	public void insertOrder(Order order, UserCart userCart);
}
