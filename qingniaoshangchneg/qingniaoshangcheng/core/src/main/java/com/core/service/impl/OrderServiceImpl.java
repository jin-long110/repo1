package com.core.service.impl;

import java.util.Date;
import java.util.List;

import com.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.dao.order.DetailMapper;
import com.core.dao.order.OrderMapper;
import com.core.pojo.cart.UserCart;
import com.core.pojo.cart.UserItem;
import com.core.pojo.order.Detail;
import com.core.pojo.order.Order;
import com.core.service.OrderService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	JedisPool jedisPool;
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	DetailMapper detailMapper;
	
	/**
	 * 保存订单和订单详情
	 */
	@Override
	public void insertOrder(Order order, UserCart userCart) {
		Jedis jedis = jedisPool.getResource();
		Long oid = jedis.incr("orderId");
		order.setId(oid);
		
		//运费
		order.setDeliverFee(userCart.getExtra());
		//金额
		order.setOrderPrice(Float.parseFloat(userCart.getPrice().toString()));
		//总金额
		order.setTotalFee(Float.parseFloat(userCart.getAllPrice().toString()));
		
		//判断付款方式
		if(order.getPaymentWay() ==0) {
			order.setIsPaiy(0); 	//0 到付
		}else {
			order.setIsPaiy(1); 	//1 等待付款
		}
		
		//订单状态
		order.setOrderState(0); //提交订单
		
		//订单生成时间
		order.setCreateDate(new Date());
		
		//保存订单
		orderMapper.insertSelective(order);
		
		//保存订单详情
		List<UserItem> userItems = userCart.getItems();
		for (UserItem userItem : userItems) {
			Detail detail = new Detail();
			//订单id
			detail.setOrderId(oid);
			//商品id
			detail.setProductId(userItem.getSku().getProductId());
			//商品名称
			detail.setProductName(userItem.getSku().getProduct().getName());
			//颜色
			detail.setColor(userItem.getSku().getColor().getName());
			//尺码
			detail.setSize(userItem.getSku().getSize());
			//价格
			detail.setPrice(userItem.getSku().getPrice());
			//购买数量
			detail.setAmount(userItem.getAmount());
			//保存订单详情
			detailMapper.insertSelective(detail);
		}
		
	}

}
