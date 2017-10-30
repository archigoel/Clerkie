package com.app.codingtest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatFragment extends Fragment {

    private EditText messageET;
    private ListView messagesContainer;
    private FloatingActionButton sendButton;
    private ChatAdapter adapter;
    String currentDateTimeString;
    String messageText;
    String messageCopied ;
    List<Chat> chatList = new ArrayList<Chat>();
    private ClipboardManager myClipboard;
    private ClipData myClip;
    int send_count = 0;
    Chat chat;
    View view;
    RelativeLayout relativeLayout;
    private ArrayList<Chat> chatHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        currentDateTimeString = sdf.format(d);
        messagesContainer = (ListView)view. findViewById(R.id.messagesContainer);
        messageET = (EditText)view. findViewById(R.id.messageEdit);
        sendButton = (FloatingActionButton)view. findViewById(R.id.chatSendButton);
        myClipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        relativeLayout = (RelativeLayout)view. findViewById(R.id.container);
        adapter = new ChatAdapter(getActivity(), chatList);
        messagesContainer.setAdapter(adapter);
        initControls();
        
        return view;
    }

    private void initControls() {

        Chat msg = new Chat();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Welcome to Clerkie, your money manager!!");
        displayMessage(msg);
        Chat msg1 = new Chat();
        msg1.setMessage("How can I help you?");
        displayMessage(msg1);

        // Enable the Copy Paste using Clipboard
        messagesContainer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                chat = chatList.get(position);
                System.out.println("Item Position  " + position);
                messageCopied = chat.getMessage();
                System.out.println("Message copied   " + messageCopied);
                myClip = ClipData.newPlainText("text", messageCopied);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getActivity(), "Text Copied",
                        Toast.LENGTH_SHORT).show();
                return false;

            }
        });

        messageET.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData abc = myClipboard.getPrimaryClip();
                ClipData.Item item = abc.getItemAt(0);
                String text = item.getText().toString();
                messageET.setText(text);
                Toast.makeText(getActivity(), "Text Pasted",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.
                            INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sendButton.getWindowToken(), 0);

                chat = new Chat();
                messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                send_count = send_count + 1;

                chat.setId(122);//dummy
                chat.setMessage(messageText);
                chat.setDate(currentDateTimeString);
                chat.setMe(true);

                messageET.setText("");
                displayMessage(chat);
                //  Auto show the incoming message after few ms
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Chat msg = new Chat();

                        msg.setId(1);
                        msg.setMe(false);
                        msg.setDate(currentDateTimeString);

                        if (send_count == 1) {
                            msg.setMessage("We can help you with your finances.");
                            displayMessage(msg);
                            Chat msg1 = new Chat();
                            msg1.setMessage("Would you like to know more about it?");
                            displayMessage(msg1);
                        } else if (send_count == 2) {
                            msg.setMessage("That's great.");
                            displayMessage(msg);
                            Chat msg1 = new Chat();
                            msg1.setMessage("Let's get started then");
                            displayMessage(msg1);
                        } else {
                            msg.setMessage("Random message");
                            displayMessage(msg);
                        }
                    }
                }, 1000);
            }

        });
    }


    public void displayMessage(Chat message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

}