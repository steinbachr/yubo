package iambob.me.yubo.activities;

import android.app.Activity;

import java.util.ArrayList;

import iambob.me.yubo.models.Contact;

public abstract class WaitForContactsActivity extends Activity {
    public abstract void onContactsLoaded(ArrayList<Contact> loadedContacts);
}