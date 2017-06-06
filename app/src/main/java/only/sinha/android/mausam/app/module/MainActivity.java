package only.sinha.android.mausam.app.module;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import only.sinha.android.mausam.app.R;
import only.sinha.android.mausam.app.module.common.MausamActivity;
import only.sinha.android.mausam.app.module.common.widget.PagerAdapter;
import only.sinha.android.mausam.app.module.navbar.NavBarFragment;
import only.sinha.android.mausam.app.module.weather.view.WeatherFragment;

public class MainActivity extends MausamActivity implements ViewPager.OnPageChangeListener, NavBarFragment.OnNavBarListener, WeatherFragment.OnWeatheListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar_rl)
    RelativeLayout mToolbarRl;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cityName)
    TextView mCityName;

    @BindView(R.id.currentTime)
    TextView mTime;

    @BindView(R.id.timezone)
    TextView mTimeZone;

    @BindView(R.id.entryViewPager)
    ViewPager mEntryViewPager;

    private PagerAdapter mPagerAdapter;

    private NavBarFragment mNavBarFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mEntryViewPager.setAdapter(mPagerAdapter);
        mEntryViewPager.addOnPageChangeListener(this);

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        initActionBarToggle();

        initNavBar(initMenu(getIntent().getExtras()));
    }

    private void initActionBarToggle() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideMenuOptions();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                showMenuOptions();
            }
        };

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void showMenuOptions() {
        // TODO:
//        mRlMenuOptionsHolder.setVisibility(View.VISIBLE);
//        mTvTitle.setVisibility(View.VISIBLE);
    }

    private void hideMenuOptions() {
        // TODO:
//        mRlMenuOptionsHolder.setVisibility(View.GONE);
//        mTvTitle.setVisibility(View.GONE);
    }

    private void initNavBar(Bundle bundle) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_menu_fl_navbar_holder,
                mNavBarFragment = NavBarFragment.newInstance(bundle)).commit();
    }

    private Bundle initMenu(Bundle bundle) {
        if (null == bundle) {
            bundle = new Bundle();
        }

        // TODO:
        /*if (bundle.containsKey(BundleKeys.SHOW_CRASH_REPORT)) {
            navigateToCrashReport();
        } else {
            bundle.putBoolean(BundleKeys.SHOW_NAVBAR_ITEM_PAGE, true);
        }*/

        return bundle;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void openNavDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START, true);
    }

    @Override
    public void closeNavDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPagerAdapter.getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setTitle(String title) {
        mCityName.setText(title);
    }

    @Override
    public void setTime(String title) {
        mTime.setText(title);
    }

    @Override
    public void setTimeZone(String title) {
        mTimeZone.setText(title);
    }

    @Override
    public Drawable getBackgrounColor() {
        return mToolbarRl.getBackground();
    }
}
