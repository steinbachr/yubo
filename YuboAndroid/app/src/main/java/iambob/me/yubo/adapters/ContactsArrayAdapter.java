package iambob.me.yubo.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import iambob.me.yubo.models.Contact;


public abstract class ContactsArrayAdapter<Contact> extends ArrayAdapter<Contact> {
    public abstract void onContactRemoved(Contact removed);

    public ContactsArrayAdapter(Context context, int resourceId, ArrayList<Contact> contacts) {
        super(context, resourceId, contacts);
    }
}
