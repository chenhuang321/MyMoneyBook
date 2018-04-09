package com.dreamgallery.mymoneybook.feature;

public class BillItem {

    private String type;
    private String fee;
    private String time;
    private String remark;
    private boolean isExpense;

    public BillItem(String type, String fee, String time, String remark, String budge) {
        this.type   = type;
        this.fee    = fee;
        this.time   = time;
        this.remark = remark;
        isExpense = (budge.equals("支出"));
    }

    public String getType() {
        return this.type;
    }

    public String getFee() {
        return fee;
    }

    public String getTime() {
        return time;
    }

    public String getRemark() {
        return remark;
    }

    public boolean isExpense() {
        return isExpense;
    }
}
