package sqliteexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.ExpensesDBModel;

public class ExpensesDB extends SQLiteOpenHelper {
    public static final String dbName = "dbMyExpense";
    public static final String tblNameExpense = "expenses";
    public static final String colExpName = "expenses_name";
    public static final String colExpPrice = "expenses_price";
    public static final String colExpDate = "expenses_date";
    public static final String colExpTime = "expenses_time";
    public static final String colExpId = "expenses_id";

    public static final String strCrtTblExpenses = "CREATE TABLE "+ tblNameExpense + " ("+ colExpId +" INTEGER PRIMARY KEY, " + colExpName +" TEXT, " + colExpPrice +" REAL, "+ colExpDate +" DATE, " + colExpTime + " TEXT)";
    public static final String strDropTblExpenses = "DROP TABLE IF EXISTS "+ tblNameExpense;

    public ExpensesDB(Context context) {
        super(context, dbName, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(strCrtTblExpenses);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(strDropTblExpenses);
        onCreate(sqLiteDatabase);
    }

    public float fnInsertExpense(ExpensesDBModel meExpense)
    {
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colExpName, meExpense.getStrExpName());
        values.put(colExpPrice, meExpense.getStrExpPrice());
        values.put(colExpDate, meExpense.getStrExpDate());
        values.put(colExpTime, meExpense.getStrExpTime());

        retResult = db.insert(tblNameExpense, null, values);
        return retResult;
    }

    public float fnUpdateExpenses(ExpensesDBModel meExpense)
    {
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colExpName, meExpense.getStrExpName());
        values.put(colExpPrice, meExpense.getStrExpPrice());
        values.put(colExpDate, meExpense.getStrExpDate());
        values.put(colExpTime, meExpense.getStrExpTime());
        retResult = db.update(tblNameExpense, values, "expenses_id = ?", new String[] {meExpense.getStrExpId()});
        return retResult;
    }

    public double fnGetTotalExpenses()
    {
        double total = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT SUM("+colExpPrice+") as Total FROM " + tblNameExpense, null);
        if (cursor.moveToFirst()){
            total = cursor.getInt(cursor.getColumnIndex("Total"));

        }
        return total;
    }

    public ExpensesDBModel fnGetExpenses(int intExpId)
    {
        ExpensesDBModel modelExpenses = new ExpensesDBModel();
        String strSelQry = "Select * from "+ tblNameExpense + " where "+ colExpId +" = " + intExpId;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelQry, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        modelExpenses.setStrExpPrice(cursor.getDouble(cursor.getColumnIndex(colExpPrice)));
        modelExpenses.setStrExpName(cursor.getString(cursor.getColumnIndex(colExpName)));
        modelExpenses.setStrExpDate(cursor.getString(cursor.getColumnIndex(colExpDate)));
        modelExpenses.setStrExpTime(cursor.getString(cursor.getColumnIndex(colExpTime)));
        return modelExpenses;
    }

    public List<ExpensesDBModel> fnGetAllExpenses()
    {
        List<ExpensesDBModel> listExp = new ArrayList<>();
        String strSelAll = "Select * from " + tblNameExpense;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll, null);
        if (cursor.moveToFirst())
        {
            do {
                ExpensesDBModel modelExpenses = new ExpensesDBModel();
                modelExpenses.setStrExpId(cursor.getString(cursor.getColumnIndex(colExpId)));
                modelExpenses.setStrExpPrice(cursor.getDouble(cursor.getColumnIndex(colExpPrice)));
                modelExpenses.setStrExpName(cursor.getString(cursor.getColumnIndex(colExpName)));
                modelExpenses.setStrExpDate(cursor.getString(cursor.getColumnIndex(colExpDate)));
                modelExpenses.setStrExpTime(cursor.getString(cursor.getColumnIndex(colExpTime)));
                listExp.add(modelExpenses);
            } while (cursor.moveToNext());
        }
        return listExp;
    }
}
