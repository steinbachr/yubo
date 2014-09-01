package iambob.me.yubo.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import iambob.me.yubo.R;
import iambob.me.yubo.activities.WaitForContactsActivity;
import iambob.me.yubo.models.Contact;
import iambob.me.yubo.utils.ContactsUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class TutorialActivity extends WaitForContactsActivity {
    static final String STEP_NUMBER_KEY = "step";

    TutorialStepFragment step1Fragment;
    TutorialStepFragment step2Fragment;
    FragmentManager fragManager;
    static ArrayList<Contact> loadedContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        if (savedInstanceState == null) {
            new ContactsUtils(this).getContacts();
            loadedContacts = new ArrayList<Contact>();

            Bundle fragArgs = new Bundle();
            fragArgs.putInt(STEP_NUMBER_KEY, 1);
            step1Fragment = new TutorialStepFragment();
            step1Fragment.setArguments(fragArgs);

            fragArgs = new Bundle();
            fragArgs.putInt(STEP_NUMBER_KEY, 2);
            step2Fragment = new TutorialStepFragment();
            step2Fragment.setArguments(fragArgs);

            fragManager = getFragmentManager();
            fragManager.beginTransaction().add(R.id.container, step1Fragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutorial, menu);
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

    @Override
    public void onContactsLoaded(ArrayList<Contact> contacts) {
        loadedContacts.addAll(contacts);
        step1Fragment.notifyContactsAdapter();
    }

    /**-- Button Click Implementations --**/
    public void nextStepClick(View v) {
        fragManager.beginTransaction().replace(R.id.container, step2Fragment).commit();
    }

    public void finishSetupClick(View v) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }


    /*****----- FRAGMENTS -----*****/

    public static class TutorialStepFragment extends Fragment {
        ArrayAdapter<Contact> contactsAdapter;

        public TutorialStepFragment() {}

        /**
         * given a step, return all the layout information for that step as a hashmap with the keys
         * being the canonical names for the layout parameters and the values being the ids of those
         * layouts
         * @param forStep the step to get layout parameters for
         * @return map of names to layout params
         */
        private HashMap<String, Integer> layoutsForStep(int forStep) {
            HashMap<String, Integer> layoutsMap = new HashMap<String, Integer>();
            switch (forStep) {
                case 1:
                    layoutsMap.put("root", R.layout.fragment_step1);
                    layoutsMap.put("list", R.id.allowLocationToContacts);
                    break;
                case 2:
                    layoutsMap.put("root", R.layout.fragment_step2);
                    layoutsMap.put("list", R.id.wantLocationFromContacts);
                    break;
                default:
                    break;
            }

            return layoutsMap;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Bundle args = getArguments();
            int step = args.getInt(STEP_NUMBER_KEY);

            HashMap<String, Integer> layoutMap = this.layoutsForStep(step);
            View rootView = inflater.inflate(layoutMap.get("root"), container, false);
            contactsAdapter = new ArrayAdapter<Contact>(getActivity(), android.R.layout.simple_list_item_1, loadedContacts);

            ListView allowedToViewLocationLv = (ListView)rootView.findViewById(layoutMap.get("list"));
            allowedToViewLocationLv.setAdapter(contactsAdapter);
            return rootView;
        }

        public void notifyContactsAdapter() {
            contactsAdapter.notifyDataSetChanged();
        }
    }
}
