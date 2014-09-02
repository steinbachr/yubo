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


public class AllowedFriendsAdapter extends ContactsArrayAdapter<Contact> {
    Database db;

    public AllowedFriendsAdapter(Context context, int resourceId, ArrayList<Contact> contacts, Database db) {
        super(context, resourceId, contacts);
        this.db = db;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item_allowed_friend, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Contact contact = this.getItem(position);

        holder.friendNameTv = (TextView)convertView.findViewById(R.id.allowedFriendName);
        holder.friendNameTv.setText(contact.getName());

        holder.removeFriendBtn = (Button)convertView.findViewById(R.id.removeAllowedFriend);
        holder.removeFriendBtn.setOnClickListener(new RemoveFriendListener(this, contact));

        return convertView;
    }

    static class ViewHolder {
        TextView friendNameTv;
        Button removeFriendBtn;
    }

    @Override
    public void onContactRemoved(Contact removed) {
        removed.setAllowsLocationTo(false);
        db.updateContact(removed);
    }
}
