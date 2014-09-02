package iambob.me.yubo.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.app.FragmentActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import iambob.me.yubo.R;
import iambob.me.yubo.adapters.ChooseFriendsAdapter;
import iambob.me.yubo.adapters.ContactsArrayAdapter;
import iambob.me.yubo.models.Contact;
import iambob.me.yubo.database.Database;
import iambob.me.yubo.adapters.MyFriendsAdapter;
import iambob.me.yubo.adapters.AllowedFriendsAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeActivity extends FragmentActivity {
    static ArrayList<Contact> myFriends;
    static ArrayList<Contact> contactsWithPermission;
    static AllowedFriendsFragment allowedFriendsFragment;
    static MyFriendsFragment myFriendsFragment;
    static ModifyFriendsFragment modifyFriendsFragment;
    static Database db;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new Database(this);
        myFriends = db.getFollowedContacts();
        contactsWithPermission = db.getContactsWithPermission();

        allowedFriendsFragment = AllowedFriendsFragment.newInstance();
        myFriendsFragment = MyFriendsFragment.newInstance();

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


    /*****-----< Button Actions >-----*****/
    /**
     * a helper for the two below button actions.
     * @param whichFragment can be one of FOR_MY_FRIENDS or FOR_ALLOWED_FRIENDS. Affects how the modifyFriendsFragment
     *                      is initialized
     */
    private void clickModifyFriendsHelper(int whichFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(ModifyFriendsFragment.FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        modifyFriendsFragment = ModifyFriendsFragment.newInstance(whichFragment);
        modifyFriendsFragment.show(ft, ModifyFriendsFragment.FRAGMENT_TAG);
    }

    public void clickModifyMyFriends(View v) {
        clickModifyFriendsHelper(ModifyFriendsFragment.FOR_MY_FRIENDS);
    }

    public void clickModifyAllowedFriends(View v) {
        clickModifyFriendsHelper(ModifyFriendsFragment.FOR_ALLOWED_FRIENDS);
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
                    return myFriendsFragment;
                case 1:
                    return allowedFriendsFragment;
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
        MyFriendsAdapter adapter;

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
            adapter = new MyFriendsAdapter(getActivity(), R.layout.list_item_my_friend, myFriends, db);
            myFriendsLv.setAdapter(adapter);

            return rootView;
        }

        public void updateAdapterFromDb() {
            this.adapter.updateAdapterFromDb();
        }
    }


    public static class AllowedFriendsFragment extends Fragment {
        AllowedFriendsAdapter adapter;

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
            adapter = new AllowedFriendsAdapter(getActivity(), R.layout.list_item_allowed_friend, contactsWithPermission, db);
            allowedFriendsLv.setAdapter(adapter);

            return rootView;
        }

        public void updateAdapterFromDb() {
            this.adapter.updateAdapterFromDb();
        }
    }


    public static class ModifyFriendsFragment extends DialogFragment {
        static final String FRAGMENT_TAG = "dialog";
        static final String MODIFY_FRIENDS_FRAGMENT_FOR_KEY = "modify_friends_for";
        static final int FOR_ALLOWED_FRIENDS = 1;
        static final int FOR_MY_FRIENDS = 2;

        static ModifyFriendsFragment newInstance(int modifyFor) {
            ModifyFriendsFragment f = new ModifyFriendsFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt(MODIFY_FRIENDS_FRAGMENT_FOR_KEY, modifyFor);
            f.setArguments(args);

            return f;
        }
        public ModifyFriendsFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle bundle = getArguments();
            final int modifyFor = bundle.getInt(MODIFY_FRIENDS_FRAGMENT_FOR_KEY);

            View rootView = inflater.inflate(R.layout.fragment_modify_friends, container, false);

            String adapterFor;
            if (modifyFor == FOR_ALLOWED_FRIENDS) {
                adapterFor = ChooseFriendsAdapter.ADAPTER_FOR_FRIEND_PERMISSIONS;
            } else {
                adapterFor = ChooseFriendsAdapter.ADAPTER_FOR_MY_FRIENDS;
            }

            ChooseFriendsAdapter contactsAdapter = new ChooseFriendsAdapter(getActivity(), R.layout.list_item_choose_friend, db.getAllContacts(), adapterFor, db);

            ListView contactLv = (ListView) rootView.findViewById(R.id.modifyFriendsLv);
            contactLv.setAdapter(contactsAdapter);

            rootView.findViewById(R.id.modifyMyFriendsBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (modifyFor == FOR_ALLOWED_FRIENDS) {
                        allowedFriendsFragment.updateAdapterFromDb();
                    } else {
                        myFriendsFragment.updateAdapterFromDb();
                    }
                    modifyFriendsFragment.dismiss();
                }
            });

            return rootView;
        }
    }
}
