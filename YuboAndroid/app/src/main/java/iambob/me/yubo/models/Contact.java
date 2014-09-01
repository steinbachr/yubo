package iambob.me.yubo.models;

import android.content.Context;

import java.util.Comparator;

import iambob.me.yubo.database.Database;


public class Contact {
    /**-- Intent Keys --**/
    public static final String CONTACT_NAME = "contact name";
    public static final String CONTACT_NUMBER = "contact number";

    private String contactId;
    private boolean wantsLocationOf;
    private boolean allowsLocationTo;
    private String name;
    private String number;

    /**-- Constructors --**/
    public Contact(String contactId, boolean wantsLocationOf, boolean allowsLocationTo, String name, String number) {
        this.contactId = contactId;
        this.wantsLocationOf = wantsLocationOf;
        this.allowsLocationTo = allowsLocationTo;
        this.name = name;
        this.number = number;
    }


    /**-- Getters / Setters --**/

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public boolean isAllowsLocationTo() {
        return allowsLocationTo;
    }

    public void setAllowsLocationTo(boolean allowsLocationTo) {
        this.allowsLocationTo = allowsLocationTo;
    }

    public boolean isWantsLocationOf() {
        return wantsLocationOf;
    }

    public void setWantsLocationOf(boolean wantsLocationOf) {
        this.wantsLocationOf = wantsLocationOf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    /**-- Overrides --**/
    public String toString() {
        return this.getName();
    }
}
