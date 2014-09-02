package iambob.me.yubo.utils;

import android.os.SystemClock;

import iambob.me.yubo.models.Contact;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GeneralUtils {

    /**
     * check if the given array of numbers contains the given number
     * @param numbers the array to check membership in
     * @param number the number to check for
     * @return true if the number is in numbers
     */
    public static boolean containsNumber(int[] numbers, int number) {
        for (int i = 0 ; i < numbers.length ; i++) {
            if (numbers[i] == number) {
                return false;
            }
        }

        return true;
    }

    /**
     * search through a list of contacts to try and find the one having the given name
     * @param contacts list of contacts to search through
     * @param contactName the name of the contact to find
     * @return the found Contact, or null if not found
     */
    public static Contact findContactByName(ArrayList<Contact> contacts, String contactName) {
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().equals(contactName.toLowerCase())) {
                return c;
            }
        }

        return null;
    }
}
