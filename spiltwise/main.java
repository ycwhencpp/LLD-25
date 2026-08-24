import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

class main{
    public static void main(String[] args) {
        
        //only main and half is implemented

    }
  
}

class Splitwise{
    UserController userController;
    GroupController groupController;
    ExpensesController expensesController;

    //crud 
}

class User {
    int id;
    UserBalanceSheet userBalanceSheet;
    HashSet<User> friends;
}

class Group {
    int id;
    ExpensesController expensesController;
    HashSet<User> users;

}

class Expenses{
    int id;
    List<Split> splitData;
    User paidBy;
    SplitType splitType;
    int totalAmount;
    
}

enum SplitType{
    EQUAL,
    EXACT,
    PERCENTAGE
}

class Split{
    int id;
    int amount;
    User owedBy;
}

interface SplitStrategy{
    public int calculate();
    public boolean validate(List<Split> splitData, int amount);
}

class EqualSplitStrategy implements SplitStrategy{
    @Override
    public int calculate(){
        return 0;
    }
    @Override
    public boolean validate(List<Split> splitData, int amount){
        return true;
    }
}

class UserBalanceSheet{
    HashMap<User, Balance> userVsBalance;
    int totalOwe;
    int totalReturn;
}

class Balance{
    int oweAmount;
    int returnAmount;
}

class GroupBalanceSheet{
    HashMap<User,HashMap<User, Balance>> userVsBalance;
    int totalSpend;
}

class UserController{
    HashMap<Integer, User> IdToUserMap;

    //crud
}
class GroupController{
    HashMap<Integer, Group> IdToGroupMap;
}

class ExpensesController{
    BalanceSheetController balanceSheetController;
    SplitStrategyFactory splitStrategyFactory;

    //crud 
    public void addExpense(List<Split> splitData, User paidBy, int amount, SplitType splitType){
        //1. validate the split data
        //2. add the expense
        //3. update the balance sheet
        // call BalanceSheetController.updateBalanceSheet()
    }

}

class SplitStrategyFactory{
    public SplitStrategy getSplitStrategy(SplitType splitType){
        if(splitType == SplitType.EQUAL){
            return new EqualSplitStrategy();
        }
        return null;
    }
}

class BalanceSheetController{
    UserController userController;
    GroupController groupController;

    public void updateBalanceSheet(Expenses expenses){
        //update user balance sheet and group balance sheet

    }
}