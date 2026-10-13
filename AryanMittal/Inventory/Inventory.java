package AryanMittal.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Inventory {
    
}

interface LowInventoryObsever {
    public void onLowStock(WareHouse wareHouse, Product priProduct, Integer stock);

}

class EmailAlertObserver implements  LowInventoryObsever{
    public void onLowStock(WareHouse wareHouse, Product priProduct, Integer stock){
        System.out.println("low stock for "+ priProduct.getName() + "at warehouse " +  wareHouse.getId() + " stock count left is " +  stock);
    }
}

class InventoryManagementSystem{
    private WareHouseRepo wareHouseRepo;
    private OrderFullfilementStartegy orderFullFilmentStartegy;
    private NotificationRouter notificationRouter;

    
	public InventoryManagementSystem(WareHouseRepo warehousesrepo, OrderFullfilementStartegy orderFullFilmentStartegy) {
		this.wareHouseRepo = warehousesrepo;
		this.orderFullFilmentStartegy = orderFullFilmentStartegy;
	}

	public List<WareHouse> getWarehouses() {
		return this.wareHouseRepo.getWarehouses();
	}
	public OrderFullfilementStartegy getOrderFullFilmentStartegy() {
		return orderFullFilmentStartegy;
	}

    public boolean fulfillOrder(Order order){
        boolean isAbleToFulFill = this.orderFullFilmentStartegy.check(order, this.wareHouseRepo);
        if(!isAbleToFulFill)return false;
        boolean processOrder = this.orderFullFilmentStartegy.process(order, this.wareHouseRepo);
        checkStock(order);
        return processOrder;
    }

    public void addStock(WareHouse warehouse, Product product, int stock){
        warehouse.addStock(product, stock);
    }

    public void checkStock(Order order){
        for(Product product : order.getOrderData().keySet()){
            for(WareHouse wh : this.wareHouseRepo.getWarehouses()){
                for(InventoryProduct ip : wh.getInventoryProducts()){
                    if(ip.getProduct().getId() == product.getId()){
                        if(ip.getStock() <10){
                            this.notificationRouter.alert(product, wh, ip.getStock());
                        }
                    }
                }
            }
        }
    }


    
}

class NotificationRouter{

    HashMap <Product, List<LowInventoryObsever>> map;
	public NotificationRouter(HashMap<Product, List<LowInventoryObsever>> map) {
		this.map = map;
	}

    public void alert(Product product, WareHouse wh, int stock){
        for(LowInventoryObsever lio : map.get(product)){
            lio.onLowStock(wh, product, stock);
        }
    }

    
}

class WareHouseRepo{
    private List<WareHouse> warehouses;

    public WareHouseRepo(List<WareHouse> warehouses) {
		this.warehouses = warehouses;
	}

	public List<WareHouse> getWarehouses() {
		return warehouses;
	}

}

class WareHouse{
    private int id;
    private List<InventoryProduct> inventoryProducts;

    public WareHouse(List<InventoryProduct> inventoryProducts, int id) {
		this.inventoryProducts = inventoryProducts;
        this.id = id;
	}

    public int getId(){
        return this.id;
    }

	public List<InventoryProduct> getInventoryProducts() {
		return inventoryProducts;
	}

    public void addStock(Product product, int stock){
        for(InventoryProduct inventoryProduct :  inventoryProducts){
            if(inventoryProduct.getProduct().getId() == product.getId()){
                inventoryProduct.addStock(stock);
            }
        }
    }

    public void reduceStock(Product product, int stock){
        for(InventoryProduct inventoryProduct :  inventoryProducts){
            if(inventoryProduct.getProduct().getId() == product.getId()){
                inventoryProduct.reduceStock(stock);
            }
        }
    }

    public int viewStock(Product product){
        for(InventoryProduct inventoryProduct :  inventoryProducts){
            if(inventoryProduct.getProduct().getId() == product.getId()){
                return inventoryProduct.getStock();
            }
        }
        return 0;
    }

    
}

class InventoryProduct{
    private Product product;
    private int count;
    private ReentrantLock reentrantLock;

	public InventoryProduct(Product product, int count) {
		this.product = product;
		this.count = count;
        this.reentrantLock = new ReentrantLock();
	}

	public Product getProduct() {
		return this.product;
	}
	public int getStock() {
		return count;
	}

    public boolean reduceStock(int stock) {
        try {
            reentrantLock.lock();
            if(this.count-stock<0) return false;
            this.count-=stock;
            return true;
        } finally{
            reentrantLock.unlock();
        }
	}

    public void addStock(int stock) {
		try {
            reentrantLock.lock();
            this.count+=stock;
        } finally{
            reentrantLock.unlock();
        }
	}


    
}

class Product{
    private int id;
    private String name;
    private int amount;
    private ProductCategory productCategory;

	public Product(int id, String name, int amount, ProductCategory productCategory) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.productCategory = productCategory;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getAmount() {
		return amount;
	}
	public ProductCategory getProductCategory() {
		return productCategory;
	}

    
}
enum ProductCategory{
    ELECTRONICS,
    UTENSILS,
    DAIRY
}

interface OrderFullfilementStartegy{
    public boolean check(Order order, WareHouseRepo wareHouseRepo);
    public boolean process(Order order, WareHouseRepo wareHouseRepo);
}

class WholeFirstOrderFullfilementStartegy implements OrderFullfilementStartegy{
    public boolean check(Order order, WareHouseRepo wareHouseRepo){
        List<WareHouse> wareHouses = wareHouseRepo.getWarehouses();
        HashMap<Product, Integer> map = order.getOrderData();

        for(Product product: map.keySet()){
            int stock = map.get(product);
            for(WareHouse wareHouse: wareHouses){
                for(InventoryProduct inventoryProduct : wareHouse.getInventoryProducts()){
                    if(inventoryProduct.getProduct().getId() == product.getId()){
                        stock-=inventoryProduct.getStock();
                        if(stock<=0) break;
                    }
                }
                if(stock<=0) break;
            }
            if(stock>0) return false;
        }
        return true;
        
    }
    public boolean process(Order order, WareHouseRepo wareHouseRepo){
        HashMap<InventoryProduct, Integer> eligibleProducts = new HashMap<>();
        List<WareHouse> wareHouses = wareHouseRepo.getWarehouses();
        HashMap<Product, Integer> map = order.getOrderData();
        boolean isAbleToFulFill = false;
        for(Product product: map.keySet()){
            int stock = map.get(product);
            for(WareHouse wareHouse: wareHouses){
                for(InventoryProduct inventoryProduct : wareHouse.getInventoryProducts()){
                    if(inventoryProduct.getProduct().getId() == product.getId()){
                        int stockToReduce = Math.min(stock,inventoryProduct.getStock());
                        if(inventoryProduct.getStock()>0) eligibleProducts.put(inventoryProduct, stockToReduce);
                        inventoryProduct.reduceStock(stockToReduce);
                        stock-=inventoryProduct.getStock();
                        if(stock<=0) break;
                    }
                }
                if(stock<=0) break;
            }
            if(stock>0) {
                isAbleToFulFill = true;
                break;
            }
        }

        if(isAbleToFulFill){
            rollback(eligibleProducts);
            return false;
        }
        return true;
    }

    public void rollback(HashMap<InventoryProduct, Integer> eligibleProducts){
        for(InventoryProduct inventoryProduct : eligibleProducts.keySet()){
            int stock = eligibleProducts.get(inventoryProduct);
            inventoryProduct.addStock(stock);
        }
    }
}

class Order{
    HashMap<Product, Integer> map;

    public Order(HashMap<Product, Integer> map) {
		this.map = map;
	}

	public HashMap<Product, Integer> getOrderData() {
		return map;
	}

}