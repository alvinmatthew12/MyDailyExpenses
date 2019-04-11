package model;

public class ExpensesDBModel {

    String strExpName;
    double strExpPrice;
    String strExpDate;

    public ExpensesDBModel(String strExpName, double strExpPrice, String strExpDate){
        this.strExpName = strExpName;
        this.strExpPrice = strExpPrice;
        this.strExpDate = strExpDate;
    }

    public ExpensesDBModel()
    {

    }

    public String getStrExpName() {return strExpName;}
    public void setStrExpName(String strExpName) {this.strExpName = strExpName;}
    public double getStrExpPrice() {return strExpPrice;}
    public void setStrExpPrice(double strExpPrice){
        this.strExpPrice = strExpPrice;
    }
    public String getStrExpDate() {return strExpDate;}
    public void setStrExpDate(String strExpDate) {this.strExpDate = strExpDate;}
}
