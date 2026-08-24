import java.util.List;

class main{
    public static void main(String[] args) {
        
        //only main and half is implemented

    }
  
}

class WalletSystem {
    User user;
    Wallet wallet;

    public void addMoney(double amount, addMoneyStartegy addMoneyStartegy){
        addMoneyStartegy.addMoney(wallet, amount);
    }
    public void debitMoney(double amount, debitMoneyStartegy debitMoneyStartegy){
        try {
        debitMoneyStartegy.debitMoney(wallet, amount);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class User {
    int id;
    UserStatus userStatus;
    Wallet wallet;
}
enum UserStatus{
    ACTIVE, 
    BLOCKED
}

class Wallet{
    int id;
    double balance;
    User user;
    List<Transcation> history;
}

class Transcation{
    int id;
    TranscationStatus transcationStatus;
    TranscationType transcationType;
    double amount;
    User receiver;
}

enum TranscationStatus{
    COMPLETED,
    FAILED,
    ROLLBACKED,
    PROCESSING,
}

enum TranscationType{
    DEBIT, 
    CREDIT
}

interface addMoneyStartegy{
    public Transcation addMoney(Wallet wallet, double amount);
}

class upiAddMoneyStartegy implements  addMoneyStartegy{
    @Override
    public Transcation addMoney(Wallet wallet, double amount){
        Transcation transcation = new Transcation();
        transcation.amount = amount;
        transcation.receiver = wallet.user;
        transcation.transcationStatus = TranscationStatus.PROCESSING;

        // core logic 
        transcation.transcationStatus = TranscationStatus.COMPLETED;

        transcation.transcationType = TranscationType.CREDIT;        
        wallet.balance+=amount;
        return transcation;
    }
}
class CardAddMoneyStartegy implements addMoneyStartegy{
    @Override
    public Transcation addMoney(Wallet wallet, double amount) {
        Transcation transcation = new Transcation();
        transcation.amount = amount;
        transcation.receiver = wallet.user;
        transcation.transcationStatus = TranscationStatus.PROCESSING;

        // core logic 
        transcation.transcationStatus = TranscationStatus.COMPLETED;

        transcation.transcationType = TranscationType.CREDIT;        
        wallet.balance+=amount;
        wallet.history.add(transcation);
        return transcation;
    }
}

interface debitMoneyStartegy{
    public Transcation debitMoney(Wallet wallet, double amount) throws  Exception ;
}

class upiDebitMoneyStartegy implements debitMoneyStartegy {
    public Transcation debitMoney(Wallet wallet, double amount) throws  Exception {
        synchronized (wallet) {
            if (wallet.balance < amount){
                throw new Exception("Insufficient func");
            }
            return new Transcation();
        }
    }
}