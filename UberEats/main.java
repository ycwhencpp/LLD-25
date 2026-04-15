package UberEats;
import java.util.HashMap;
import java.util.List;

public class main {

}


class Cart{
    List<Coupon> coupons;
    User user;
    List<Item> menuItems;

    public boolean applyCoupon(Coupon coupon){
       if(coupons.isEmpty()){
            coupons.add(coupon);
            return true;
       }

       if(!coupon.isStackable) return false;

       coupons.add(coupon);

       return true;

    }

    public int couponDiscount(){
        int basePrice = this.calculateBasePrice();
        int discountedAmount = 0;
        for(Coupon coupon : coupons){
            int currentDiscount = coupon.calculateDiscountAmount(basePrice);
            discountedAmount += currentDiscount;
            basePrice-= currentDiscount;
        }
        return discountedAmount;
    }

    public int calculateBasePrice(){
        int total =0 ;
        for(Item menuItem : menuItems){
            total+=menuItem.total();
        }
        return total;
    }
}

class PricingContext{
    Cart cart;
    Receipt receipt;
}
class PricingEngine{
    List<PricingStep> pricingSteps;
    PricingContext pricingContext;

    public Receipt checkout(){
        for(PricingStep pricingStep : pricingSteps){
            pricingStep.calculate(pricingContext);
        }
        return pricingContext.receipt;
    }
}

class Receipt{
    HashMap<PricingStep, Integer> data = new HashMap<>();

    int currentPrice = 0;

    public int currentPrice(){
        return this.currentPrice;
    }

    public void updateCurrentPrice(PricingStep pricingStep, int updatedPrice){
        int diff = Math.abs(updatedPrice - currentPrice);
        data.put(pricingStep, diff);
        this.currentPrice = updatedPrice;
    } 

    public void showReceipt(){
        for(PricingStep pricingStep: data.keySet()){
            System.out.println(pricingStep + "-> " + data.get(pricingStep));
        }
    }
}

interface PricingStep {
    public void calculate(PricingContext  pricingContext);
}

class SubtotalPricingStep implements PricingStep {
    public void calculate(PricingContext context) {
        int basePrice = context.cart.calculateBasePrice();
        context.receipt.updateCurrentPrice(this, basePrice);
    }
}

class SurgePricingStep implements PricingStep {
    public int surgePrice;

    public SurgePricingStep(int surgePrice){
        this.surgePrice = surgePrice;
    }

    public void calculate(PricingContext pricingContext){
        int total = pricingContext.receipt.currentPrice();
        pricingContext.receipt.updateCurrentPrice(this, total * surgePrice);
    }
}

class ApplyCouponCodePricingStep implements PricingStep{
     public void calculate(PricingContext pricingContext){
        int total = pricingContext.receipt.currentPrice();
        int getCouponDiscount = pricingContext.cart.couponDiscount();
        pricingContext.receipt.updateCurrentPrice(this,  total - getCouponDiscount);

    }
}
class DeliveryFeePricingStep implements PricingStep {
    public int deliveryFee;

    public DeliveryFeePricingStep(int deliveryFee){
        this.deliveryFee = deliveryFee;
    }

    public void calculate(PricingContext pricingContext){
        int total = pricingContext.receipt.currentPrice();
        if(pricingContext.cart.user.userType == UserType.PREMIUM) return;
        pricingContext.receipt.updateCurrentPrice(this,  total + deliveryFee);

    }
}

class Coupon {
    String name;
    boolean isStackable;
    DiscountStartegy discountStartegy;

    public Coupon(String name, boolean isStackable, DiscountStartegy discountStartegy){
        this.name = name;
        this.isStackable = isStackable;
        this.discountStartegy = discountStartegy;
    }

    public int calculateDiscountAmount(int amount){
        return discountStartegy.calculate(amount);
    }

}

interface DiscountStartegy {
    public int calculate(int amount);
}

class FlatDiscountStartegy implements DiscountStartegy{
    int flatAmount;
    public FlatDiscountStartegy(int flatAmount){
        this.flatAmount = flatAmount;
    }

    public int calculate(int amount){
        //should we show error? or what ?
        return Math.max(0, amount-flatAmount);
    }
}

class PercentageDiscountStartegy implements DiscountStartegy{
    int percentage;
    public PercentageDiscountStartegy(int percentage){
        this.percentage = percentage;
    }

    public int calculate(int amount){
        //should we show error? or what ?
       int percentageAmount = (amount * 100)/Math.max(1,percentage);
       return Math.max(0,amount -percentageAmount);
    }
}


interface MenuItem {
    public int price();
}

class BaseMenuItem implements MenuItem{

    int basePrice;
    int name;

    public BaseMenuItem(int basePrice, int name){
        this.basePrice = basePrice;
        this.name = name;
    }

    public int price(){
        return this.basePrice;
    }
}

abstract class CustomizationDecorator implements MenuItem{
    MenuItem baseItem;
    int fixedPrice;

    public CustomizationDecorator(MenuItem menuItem, int fixedPrice){
        this.baseItem = menuItem;
        this.fixedPrice = fixedPrice;
    }

    public int price(){
        return this.baseItem.price() + this.fixedPrice;
    }
}


class CheeseToppingDecorator extends CustomizationDecorator{
    public CheeseToppingDecorator(MenuItem menuItem, int fixedPrice){
       super(menuItem, fixedPrice);
    }
}
class ExtraDipToppingDecorator extends CustomizationDecorator{
    public ExtraDipToppingDecorator(MenuItem menuItem, int fixedPrice){
       super(menuItem, fixedPrice);
    }
}


class Item{

    MenuItem menuItem;
    int quantity; 

    public Item(MenuItem menuItem, int quantity){
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public int total(){
        return menuItem.price() * quantity;
    }
}

class User{
    String name;
    UserType userType;
}

enum UserType {
    PREMIUM, 
    REGULAR
}