package iambob.me.yubo.listeners;

import android.view.View;
import android.widget.ArrayAdapter;

import iambob.me.yubo.adapters.ContactsArrayAdapter;
import iambob.me.yubo.models.Contact;

public class RemoveFriendListener implements View.OnClickListener {
    ContactsArrayAdapter<Contact> adapter;
    Contact toRemove;

    public RemoveFriendListener(ContactsArrayAdapter<Contact> adapter, Contact toRemove) {
        this.adapter = adapter;
        this.toRemove = toRemove;
    }

    public void onClick(View v) {
        this.adapter.onContactRemoved(this.toRemove);
        this.adapter.remove(this.toRemove);
    }
}
