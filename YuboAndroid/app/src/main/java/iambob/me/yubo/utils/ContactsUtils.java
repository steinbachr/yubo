package iambob.me.yubo.utils;

import android.database.Cursor;
import android.app.LoaderManager;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.content.CursorLoader;
import android.content.Loader;
import android.os.Bundle;
import android.os.Build;

import java.util.ArrayList;

import iambob.me.yubo.activities.WaitForContactsActivity;
import iambob.me.yubo.models.Contact;
import iambob.me.yubo.database.Database;


public class ContactsUtils implements LoaderManager.LoaderCallbacks<Cursor>{
    private WaitForContactsActivity enclosing;

    private final String NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME;
    private final String[] PROJECTION = {
        Contacts._ID,
        Contacts.LOOKUP_KEY,
        NAME,
        Contacts.HAS_PHONE_NUMBER,
        Phone.NUMBER,
        Phone.TYPE
    };

    public ContactsUtils(WaitForContactsActivity enclosing) {
        this.enclosing = enclosing;
    }


    public void getContacts() {
        this.enclosing.getLoaderManager().initLoader(0, null, this);
    }

    /**-- Helpers --**/
    /**
     * clean the given phone number to remove spaces, dashes and other non numerics
     * @param rawNumber the number to clean
     */
    private String cleanNumber(String rawNumber) {
        if (rawNumber != null) {
            return rawNumber.replaceAll("[A-Za-z+() -]", "").trim();
        } else {
            return "";
        }
    }

    /**-- Loader Manager Overrides --**/
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        return new CursorLoader(
                this.enclosing,
                Data.CONTENT_URI,
                PROJECTION,
                Phone.NUMBER + " NOT LIKE '%@%'",
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ArrayList<Contact> fetchedContacts = new ArrayList<Contact>();

        /** DEBUG ONLY! **/
//        new Database(this.enclosing).clearDb();

        final int NOT_A_PHONE = 0;
        int keyIndex = cursor.getColumnIndex(Contacts.LOOKUP_KEY);
        int nameIndex = cursor.getColumnIndex(this.NAME);
        int numberIndex = cursor.getColumnIndex(Phone.NUMBER);
        int phoneTypeIndex = cursor.getColumnIndex(Phone.TYPE);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int phoneType = cursor.getInt(phoneTypeIndex);
            if (phoneType > NOT_A_PHONE) {
                fetchedContacts.add(new Contact(cursor.getString(keyIndex), false, false, cursor.getString(nameIndex), cleanNumber(cursor.getString(numberIndex))));
            }
            cursor.moveToNext();
        }

        new Database(this.enclosing).processContacts(fetchedContacts);
        this.enclosing.onContactsLoaded(fetchedContacts);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

