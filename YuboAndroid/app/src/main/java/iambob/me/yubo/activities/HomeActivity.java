package iambob.me.yubo.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.app.FragmentActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import iambob.me.yubo.R;
import iambob.me.yubo.models.Contact;
import iambob.me.yubo.database.Database;

import java.util.ArrayList;


public class HomeActivity extends FragmentActivity {
    static ArrayList<Contact> contacts;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        contacts = new Database(this).getAllContacts();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setupActionBar();
        setupViewSwiping();
    }

    private void setupActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.title_fragment_friends)).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.title_fragment_allowed_contacts)).setTabListener(tabListener));
    }

    private void setupViewSwiping() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    getActionBar().setSelectedNavigationItem(position);
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*****-----< PagerAdapter >------*****/


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MyFriendsFragment.newInstance();
                case 1:
                    return AllowedFriendsFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    /*****-----< Fragments >-----*****/


    public static class MyFriendsFragment extends Fragment {
        public static MyFriendsFragment newInstance() {
            MyFriendsFragment fragment = new MyFriendsFragment();
            return fragment;
        }

        public MyFriendsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my_friends, container, false);

            ListView myFriendsLv = (ListView)rootView.findViewById(R.id.myFriendsLv);
            myFriendsLv.setAdapter(new ArrayAdapter<Contact>(getActivity(), android.R.layout.simple_list_item_1, contacts));

            return rootView;
        }
    }

    public static class AllowedFriendsFragment extends Fragment {
        public static AllowedFriendsFragment newInstance() {
            AllowedFriendsFragment fragment = new AllowedFriendsFragment();
            return fragment;
        }

        public AllowedFriendsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_allowed_friends, container, false);

            ListView allowedFriendsLv = (ListView)rootView.findViewById(R.id.allowedFriendsLv);
            allowedFriendsLv.setAdapter(new ArrayAdapter<Contact>(getActivity(), android.R.layout.simple_list_item_1, contacts));

            return rootView;
        }
    }

}
