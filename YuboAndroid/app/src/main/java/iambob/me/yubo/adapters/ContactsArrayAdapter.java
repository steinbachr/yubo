package iambob.me.yubo.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import iambob.me.yubo.models.Contact;
import iambob.me.yubo.database.Database;


public abstract class ContactsArrayAdapter<Contact> extends ArrayAdapter<Contact> {
    protected Database db;

    public abstract void onContactRemoved(Contact removed);
    /* get the arraylist of contact which backs this array adapter */
    protected abstract ArrayList<Contact> contactsForAdapter();

    public ContactsArrayAdapter(Context context, int resourceId, ArrayList<Contact> contacts, Database db) {
        super(context, resourceId, contacts);
        this.db = db;
    }

    /**
     * this method polls the database for the most up to date contacts list and then notifies the adapter that the data has changed
     */
    public void updateAdapterFromDb() {
        ArrayList<Contact> contactsFromDb = this.contactsForAdapter();
        this.clear();
        this.addAll(contactsFromDb);
        this.notifyDataSetChanged();
    }
}
