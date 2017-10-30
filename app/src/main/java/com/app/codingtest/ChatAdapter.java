package com.app.codingtest;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private final List<Chat> chats;
    private Context context;

    public ChatAdapter(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    @Override
    public int getCount() {
        if (chats != null) {
            return chats.size();
        } else {
            return 0;
        }
    }

    @Override
    public Chat getItem(int position) {
        if (chats != null) {
            return chats.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Chat Chat = getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.list_item_chat, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        boolean myMsg = Chat.getIsme() ;
        System.out.println("Check if outgoing message "+ String.valueOf(myMsg));

        // Just a dummy check to simulate whether it's me or other sender
        setAlignment(holder, myMsg);
        holder.txtMessage.setText(Chat.getMessage());
        System.out.println("Message" + Chat.getMessage());
        holder.txtInfo.setText(Chat.getDate());
        System.out.println("Message" + Chat.getDate());
        final View finalConvertView = convertView;
        convertView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) finalConvertView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow( finalConvertView.getWindowToken(), 0);
                return true;
            }
        });

        return convertView;
    }

    public void add(Chat message) {
        chats.add(message);
    }

    public void add(List<Chat> messages) {
        chats.addAll(messages);
    }

    private void setAlignment(ViewHolder holder, boolean isMe) {
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) holder.chatLayout.getLayoutParams();

        // If message is outgoing align to the right
        if (isMe) {
            holder.chatLayout.setBackgroundResource(R.drawable.outgoing_message_background);

           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
           holder.chatLayout.setLayoutParams(layoutParams);

           layoutParams1 = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
           layoutParams1.gravity = Gravity.RIGHT;
           holder.txtMessage.setLayoutParams(layoutParams1);

           layoutParams1 = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
           layoutParams1.gravity = Gravity.RIGHT;
           holder.txtInfo.setLayoutParams(layoutParams1);
        }
        // If message is incoming align to the left
        else {

           holder.chatLayout.setBackgroundResource(R.drawable.login_button_background);

           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
           holder.chatLayout.setLayoutParams(layoutParams);

           layoutParams1 = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
           layoutParams1.gravity = Gravity.RIGHT;
           holder.txtMessage.setLayoutParams(layoutParams1);

           layoutParams1 = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
           layoutParams1.gravity = Gravity.RIGHT;
           holder.txtInfo.setLayoutParams(layoutParams1);
        }
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        holder.chatLayout = (LinearLayout) v.findViewById(R.id.chat_layout);
        holder.txtInfo = (TextView) v.findViewById(R.id.chat_time);
        return holder;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public TextView txtInfo;
        public LinearLayout chatLayout;
    }
}