package iambob.me.yubo.adapters;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.view.LayoutInflater;

import org.w3c.dom.Text;

import java.util.ArrayList;

import iambob.me.yubo.listeners.RemoveFriendListener;
import iambob.me.yubo.models.Contact;
import iambob.me.yubo.database.Database;
import iambob.me.yubo.R;


public class MyFriendsAdapter extends ContactsArrayAdapter<Contact> {
    public MyFriendsAdapter(Context context, int resourceId, ArrayList<Contact> contacts, Database db) {
        super(context, resourceId, contacts, db);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item_my_friend, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Contact contact = this.getItem(position);

        holder.friendNameTv = (TextView)convertView.findViewById(R.id.myFriendName);
        holder.friendNameTv.setText(contact.getName());

        holder.removeFriendBtn = (Button)convertView.findViewById(R.id.removeMyFriend);
        holder.removeFriendBtn.setOnClickListener(new RemoveFriendListener(this, contact));

        holder.friendLocationBtn = (Button)convertView.findViewById(R.id.getFriendLocation);
        holder.friendLocationBtn.setOnClickListener(new GetLocationListener(contact));

        return convertView;
    }

    static class ViewHolder {
        TextView friendNameTv;
        Button friendLocationBtn, removeFriendBtn;
    }

    /**-- ContactsArrayAdapter Overrides --**/
    @Override
    public void onContactRemoved(Contact removed) {
        removed.setWantsLocationOf(false);
        db.updateContact(removed);
    }

    @Override
    protected ArrayList<Contact> contactsForAdapter() {
        return this.db.getFollowedContacts();
    }

    /**-- Listeners --**/

    class GetLocationListener implements View.OnClickListener {
        Contact clicked;

        public GetLocationListener(Contact clicked) {
            this.clicked = clicked;
        }

        @Override
        public void onClick(View v) {
            ViewGroup parent = (ViewGroup)v.getParent();
            ProgressBar progressBar = (ProgressBar)parent.findViewById(R.id.getFriendLocationProgress);
            progressBar.setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
        }
    }
}
