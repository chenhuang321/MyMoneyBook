package com.dreamgallery.mymoneybook.feature;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamgallery.mymoneybook.R;

import java.util.ArrayList;

public class BillItemsAdapter extends ArrayAdapter<BillItem> {

    private Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView textViewFee;
        TextView textViewTime;
        TextView textViewRemark;
        ImageView imageViewType;
    }

    public BillItemsAdapter(ArrayList<BillItem> billItemArrayList, Context mContext) {
        super(mContext, R.layout.billsitem, billItemArrayList);
        this.context = mContext;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        BillItem billItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.billsitem, parent, false);
            viewHolder.textViewFee     = convertView.findViewById(R.id.text_fee_show);
            viewHolder.textViewTime    = convertView.findViewById(R.id.text_time_show);
            viewHolder.textViewRemark  = convertView.findViewById(R.id.text_remarks_show);
            viewHolder.imageViewType   = convertView.findViewById(R.id.image_type_show);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        if(billItem != null) {
            viewHolder.textViewTime.setText(billItem.getTime());
            viewHolder.textViewRemark.setText(billItem.getRemark());
            viewHolder.textViewFee.setText(billItem.getFee());
            int income_color_id = this.context.getColor(R.color.income);
            int expense_color_id = this.context.getColor(R.color.expense);
            viewHolder.textViewFee.setTextColor(!billItem.isExpense()? income_color_id : expense_color_id);
            switch (billItem.getType()) {
                case "衣":
                    System.out.println("Clothing...");
                    viewHolder.imageViewType.setImageResource(R.drawable.outfit);
                    break;
                case "食":
                    viewHolder.imageViewType.setImageResource(R.drawable.food);
                    break;
                case "住":
                    viewHolder.imageViewType.setImageResource(R.drawable.house);
                    break;
                case "行":
                    viewHolder.imageViewType.setImageResource(R.drawable.transit);
                    break;
                default:
                    System.out.println("Breaking time!");
                    break;
            }
        }
        return result;
    }
}
