package iambob.me.yubo.adapters;

import android.media.Image;
import android.provider.ContactsContract;
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

import iambob.me.yubo.models.Contact;
import iambob.me.yubo.database.Database;
import iambob.me.yubo.R;


/**
 * we make the choose friends adapter generic because it can be used in many different circumstances
 * (i.e. we use it for initially choosing who can see the users location, which friends the user
 * wants to see location info for, and later - in the home screen - to modify these selections)
 */
public class ChooseFriendsAdapter extends ArrayAdapter<Contact> {
    public static final String ADAPTER_FOR_MY_FRIENDS = "my_friends_adapter";
    public static final String ADAPTER_FOR_FRIEND_PERMISSIONS = "friend_permissions_adapter";
    private String adapterFor; //one of the constants defined above
    private Database db;

    public ChooseFriendsAdapter (Context context, int resourceId, ArrayList<Contact> contacts, String adapterFor) {
        super(context, resourceId, contacts);
        this.adapterFor = adapterFor;
    }

    public ChooseFriendsAdapter (Context context, int resourceId, ArrayList<Contact> contacts, String adapterFor, Database db) {
        super(context, resourceId, contacts);
        this.adapterFor = adapterFor;
        this.db = db;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item_choose_friend, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Contact contact = this.getItem(position);

        holder.friendNameTv = (TextView)convertView.findViewById(R.id.chooseFriendName);
        holder.friendNameTv.setText(contact.getName());

        holder.chooseFriendBtn = (ImageView)convertView.findViewById(R.id.chooseFriendBtn);
        holder.chooseFriendBtn.setOnClickListener(new ChooseFriendListener(contact));

        boolean isSelected = adapterFor.equals(ADAPTER_FOR_MY_FRIENDS) ? contact.isWantsLocationOf() : contact.isAllowsLocationTo();
        int imageResource = isSelected ? R.drawable.add_friend_selected : R.drawable.add_friend_unselected;
        holder.chooseFriendBtn.setImageResource(imageResource);

        return convertView;
    }

    static class ViewHolder {
        TextView friendNameTv;
        ImageView chooseFriendBtn;
    }


    /**-- Listeners --**/

    class ChooseFriendListener implements View.OnClickListener {
        Contact clicked;

        public ChooseFriendListener(Contact clicked) {
            this.clicked = clicked;
        }

        @Override
        public void onClick(View v) {
            boolean isSelected;
            if (adapterFor.equals(ADAPTER_FOR_MY_FRIENDS)) {
                isSelected = this.clicked.toggleWantsLocationOf();
            } else {
                isSelected = this.clicked.toggleAllowsLocationTo();
            }

            ImageView clickedView = (ImageView)v;
            if (isSelected) {
                clickedView.setImageResource(R.drawable.add_friend_selected);
            } else {
                clickedView.setImageResource(R.drawable.add_friend_unselected);
            }

            db.updateContact(clicked);
        }
    }

}
