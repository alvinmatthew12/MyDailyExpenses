package model;

public class ExpensesDBModel {

    String strExpId;
    String strExpName;
    double strExpPrice;
    String strExpDate;
    String strExpTime;

    public ExpensesDBModel(String strExpId, String strExpName, double strExpPrice, String strExpDate, String strExpTime){
        this.strExpId = strExpId;
        this.strExpName = strExpName;
        this.strExpPrice = strExpPrice;
        this.strExpDate = strExpDate;
        this.strExpTime = strExpTime;

    }

    public ExpensesDBModel()
    {

    }

    public String getStrExpId() { return strExpId; }
    public void setStrExpId(String strExpId) { this.strExpId = strExpId; }
    public String getStrExpName() {return strExpName;}
    public void setStrExpName(String strExpName) {this.strExpName = strExpName;}
    public double getStrExpPrice() {return strExpPrice;}
    public void setStrExpPrice(double strExpPrice){
        this.strExpPrice = strExpPrice;
    }
    public String getStrExpDate() {return strExpDate;}
    public void setStrExpDate(String strExpDate) {this.strExpDate = strExpDate;}
    public String getStrExpTime() { return strExpTime; }
    public void setStrExpTime(String strExpTime) { this.strExpTime = strExpTime; }
}
