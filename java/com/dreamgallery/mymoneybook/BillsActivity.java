package com.dreamgallery.mymoneybook;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.dreamgallery.mymoneybook.feature.BillItem;
import com.dreamgallery.mymoneybook.feature.BillItemsAdapter;
import com.dreamgallery.mymoneybook.feature.MyDB;

import java.util.ArrayList;

/**
 * @author Chen Huang at 2018/4/6
 */
public class BillsActivity extends Activity {

    private Context context;
    ImageView imageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bills);
        context     = getApplicationContext();
        imageButton = findViewById(R.id.返回按钮);
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MyDB myFinancialDB = new MyDB(context);
        ListView billItemsList = findViewById(R.id.list_bills);
        Cursor cursor = myFinancialDB.selectRecords();
        ArrayList<BillItem> billItems = new ArrayList<>();
        if (cursor.getCount() > 0) {
            System.out.println("Cursor count: " + cursor.getCount());
            if (cursor.moveToFirst()) {
                System.out.println("Debug Hint: Moved to first");
                do {
                    double fee   = cursor.getDouble(cursor.getColumnIndex(MyDB.FINANCE_fee));
                    String type  = cursor.getString(cursor.getColumnIndex(MyDB.FINANCE_TYPE));
                    String note  = cursor.getString(cursor.getColumnIndex(MyDB.FINANCE_note));
                    String time  = cursor.getString(cursor.getColumnIndex(MyDB.FINANCE_TIME));
                    String budge = cursor.getString(cursor.getColumnIndex(MyDB.FINANCE_budge));
                    String format_fee = ((budge.equals("支出"))? "-" : "+") + String.format(Double.toString(fee), "###.##");
                    BillItem tmp = new BillItem(type, format_fee, time, note, budge);
                    billItems.add(tmp);
                    cursor.moveToNext();
                } while (cursor.moveToNext());
            }
        }
        BillItemsAdapter adapter = new BillItemsAdapter(billItems, context);
        billItemsList.setAdapter(adapter);

        cursor.close();
        myFinancialDB.close();
    }
}