package com.dreamgallery.mymoneybook.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dreamgallery.mymoneybook.R;
import com.dreamgallery.mymoneybook.feature.MyDB;

import java.text.DecimalFormat;

public class FragmentAccount extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finance_check, container, false);
        MyDB mMysql;
        TextView textRemainder, textPay, textIncome;

        double resultIncome = 0, resultPay = 0;
        textIncome     = view.findViewById(R.id.value_income_current);
        textRemainder  = view.findViewById(R.id.value_money_left);
        textPay        = view.findViewById(R.id.value_payment_current);

        mMysql = new MyDB(getContext());

        Cursor cursor = mMysql.selectRecords();

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    double Fee = cursor.getDouble(cursor.getColumnIndex(MyDB.FINANCE_fee));
                    String budge = cursor.getString(cursor.getColumnIndex(MyDB.FINANCE_budge));
                    String note = cursor.getString(cursor.getColumnIndex(MyDB.FINANCE_note));
                    System.out.println("Do print: budge " + budge);
                    System.out.println("Do print: note " + note);

                    if (budge.equals("支出")) {
                        resultPay += Fee;
                    } else if (budge.equals("收入")) {
                        resultIncome += Fee;
                    }
                    cursor.moveToNext();
                } while (cursor.moveToNext());
            }
        }

        DecimalFormat df = new DecimalFormat("###.##");
        textPay.setText(String.valueOf(df.format(resultPay)));
        textIncome.setText(String.valueOf(df.format(resultIncome)));
        textRemainder.setText(String.valueOf(df.format(resultIncome - resultPay)));

        cursor.close();
        mMysql.close();

        return view;
    }
}