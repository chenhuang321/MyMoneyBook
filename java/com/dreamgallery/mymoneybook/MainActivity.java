package com.dreamgallery.mymoneybook;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dreamgallery.mymoneybook.fragment.FragmentAccount;
import com.dreamgallery.mymoneybook.helper.ImageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private View               currentView;
    private ImageHelper        imageHelper;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private LinearLayout mTabBtnMessage;
    private LinearLayout mTabBtnFriends;
    private LinearLayout mTabBtnAddress;
    private LinearLayout mTabBtnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCurrentView(); // Set the view for helper class
        _ini_bottom_bar(); // Initialize the bottom bar
        _ini_onClickListener(); // Initialize the on click listener method
        _ini_current_view();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            /**
             *
             * @return The number of fragments
             */
            @Override
            public int getCount() {
                return mFragments.size();
            }
            /**
             *
             * @param arg0 Index argument option to get selected fragment
             * @return selected fragment
             */
            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentIndex;
            /**
             * Overrides from the parent method
             * @param position The position of selected page
             */
            @Override
            public void onPageSelected(int position) {
                _reset_img_btn();
                currentIndex = position;
                imageHelper.change(currentIndex);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.我要记账:
                System.out.println("You have selected recorder item!");
                Intent recordIntent = new Intent(this, RecorderActivity.class);
                startActivity(recordIntent);
                break;
            case R.id.我要查账:
                System.out.println("You have selected finder item!");
                break;
            case R.id.账单总览:
                System.out.println("You have selected overview item!");
                Intent billsIntent = new Intent(this, BillsActivity.class);
                startActivity(billsIntent);
                break;
            default:
                System.out.println("Just give me a break!");
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCurrentView() {
        currentView = findViewById(R.id.btn_tab_bottom_setting).getRootView();
    }

    private void _ini_bottom_bar() {
        imageHelper = new ImageHelper(5, currentView);
        _reset_img_btn();
        imageHelper.change(0);
    }

    private void _ini_onClickListener() {
        // Get the buttons from the ImageHelper
        ImageButton btn_0 = imageHelper.getImageButtonSource(0);
        ImageButton btn_1 = imageHelper.getImageButtonSource(1);
        ImageButton btn_2 = imageHelper.getImageButtonSource(2);
        ImageButton btn_3 = imageHelper.getImageButtonSource(3);
        // Set the OnClickListener to each ImageButton
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
    }

    private void _ini_current_view() {
        mTabBtnMessage = findViewById(R.id.id_tab_bottom_weixin);
        mTabBtnFriends = findViewById(R.id.id_tab_bottom_friend);
        mTabBtnAddress = findViewById(R.id.id_tab_bottom_contact);
        mTabBtnSetting = findViewById(R.id.id_tab_bottom_setting);
        mViewPager = findViewById(R.id.id_viewpager);
        FragmentAccount fragmentaccount = new FragmentAccount();
        mFragments.add(fragmentaccount);
    }

    private void _reset_img_btn() {
        if(imageHelper != null) {
            imageHelper.reset();
        }
        else {
            System.out.println("Seriously? Your ImageHelper object is null!");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tab_bottom_message:
                System.out.println("You choice 0");
                imageHelper.change(0);
                break;
            case R.id.btn_tab_bottom_friend:
                System.out.println("You choice 1");
                imageHelper.change(1);
                break;
            case R.id.btn_tab_bottom_contact:
                System.out.println("You choice 2");
                imageHelper.change(2);
                break;
            case R.id.btn_tab_bottom_setting:
                System.out.println("You choice 3");
                imageHelper.change(3);
                break;
            default:
                System.out.println("Just give me a break!");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.saveState();
    }
}
