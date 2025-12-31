
import java.util.*;

class main{
    public static void main(String[] args) {
        
        
      


    }
  
}

class couponService{
    List<coupon> allcoupons;
    couponValidator couponValidator;

    public List<coupon> getApplicableCoupons(orderContext orderContext ) {
        List<coupon> eligileCoupons = new ArrayList<>();
        for (coupon coupon: allcoupons){
            if(couponValidator.validate(orderContext, coupon)){
                eligileCoupons.add(coupon);
            }
        }
        return eligileCoupons;
    }

    public boolean validateCoupon(coupon coupon, orderContext orderContext){
        return couponValidator.validate(orderContext, coupon);
    }
}

interface discountStrategy {
    public int calculateDiscount(orderContext order);
}

class flatdiscountStrategy implements discountStrategy{
    final int flatDiscount;
    public flatdiscountStrategy(int val){
        this.flatDiscount = val;
    }
    @Override
    public int calculateDiscount(orderContext orderContext){
        return Math.max(0,orderContext.order.price - flatDiscount);
    }
}

class percentageDiscountStrategty implements discountStrategy{
    final double percentage;
    public percentageDiscountStrategty(double val){
        this.percentage = val;
    }

    @Override
    public int calculateDiscount(orderContext orderContext){
        return (int) ((this.percentage/100)*orderContext.order.price);
    }
}

interface couponRule {
    public boolean validate(orderContext orderContext);
}

class premiumUserCouponRule implements couponRule{
    final userType usertype = userType.PREMIUM;
    @Override
    public boolean validate (orderContext orderContext){
        return orderContext.user.userType == this.usertype;
    }
}

class minAmountCouponRule implements couponRule{
    final int minAmount;
    public minAmountCouponRule(int val){
        this.minAmount =val;
    }
    @Override
    public boolean validate (orderContext orderContext){
        return orderContext.order.price >= this.minAmount;
    }
}

class coupon{
    int id;
    List<couponRule> couponRules;
    boolean isStackable;
}

class user {
    int id;
    userType userType;
}

enum userType{
    PREMIUM, 
    REGULAR, 
    BLACKLISTED
}


class order {
    int id;
    int price;
}

class orderContext{
    order order;
    user user;
}

class couponValidator{
    

    public boolean validate(orderContext orderContext,  coupon coupon) {
        for(couponRule rule :coupon.couponRules){
            if (!rule.validate(orderContext)){
                return false;
            }
        }
        return true;
    }
}


/**
 * 
 * coupon aaya usko validate karne ke bahut rules hai , or maybe ek so list 
 * 
 * and coupon rule is prop of coupon 
 * 
 * and apply karne ke strategy alag alag ho skte hai
 * 
 * 
 * so coupon with list of rules might be one might be more 
 * and strategy to apply discount
 * 
 * phir aaya user and order jiske basis pe validate karna hai
 * 
 * 
 * so ek coupon validtor correct kya karega wo generic hoga na 
 * 
 * usko bhejo jitne bhi coupon hai system mai with order context and will give you validated one 
 * 
 * 
 */