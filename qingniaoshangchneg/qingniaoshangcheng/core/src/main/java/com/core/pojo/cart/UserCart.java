package com.core.pojo.cart;

import com.core.pojo.product.Sku;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


//购物车
public class UserCart {
    private List<UserItem> items=new ArrayList<>();

    public List<UserItem> getItems() {
        return items;
    }

    public void setItems(List<UserItem> items) {
        this.items = items;
    }

    //添加购物项的购物车  如果是同款商品那么就和并
    public void  addUserItem(UserItem userItem){
//有同款的商品数量追加
        if (items.contains(userItem)){
            for (UserItem item:items) {
                if (item.equals(userItem)){
                    item.setAmount(userItem.getAmount()+item.getAmount());
                }
            }
        }else {
            items.add(userItem);
        }

    }


    //因为购物车会转换成json，小计，金额，运费，总价格是不需要被转换的，所以需要忽略

    //小计
    @JsonIgnore
    public Integer getAllProductAmount() {
        Integer sum = 0;
        for (UserItem userItem : items) {
            sum += userItem.getAmount();
        }
        return sum;
    }

    //金额
    @JsonIgnore
    public Double getPrice() {
        Double price = 0d;
        for (UserItem userItem : items) {
            price += userItem.getAmount() * userItem.getSku().getPrice();
        }
        return price;
    }

    //运费 如果购买的所有的商品大于99元那么是免运费的
    @JsonIgnore
    public Float getExtra() {
        if(getPrice()<= 99) {
            return 10f;
        }else {
            return 0f;
        }
    }

    //总金额
    @JsonIgnore
    public Double getAllPrice() {
        return getPrice()+getExtra();
    }


    //删除购物车中的购物项
    public void delUserItem(Long skuId) {
        Sku sku = new Sku();
        sku.setId(skuId);
        UserItem userItem = new UserItem();
        userItem.setSku(sku);
        //移除购物项
        items.remove(userItem);
    }
//清空购物车
    public void clearCart() {
        items.clear();
    }
}
