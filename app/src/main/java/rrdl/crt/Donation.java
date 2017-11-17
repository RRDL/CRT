package rrdl.crt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.bassaer.chatmessageview.model.User;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.github.bassaer.chatmessageview.views.MessageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Donation extends Fragment {


    private static ChatView mChatView;
    private static MessageList mMessageList;
    private ArrayList<User> mUsers;
    private int myId ;
    private Bitmap myIcon ;
    private String myName ;
    private User me ;
    private static User you ;
    private MessageSender mMessageSenderCallback;




    public Donation() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myId = 0;
        myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        myName = "User";
        me = new User(myId, myName, myIcon);
        you = new User(myId, myName, myIcon);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_donation, container, false);
        initUsers();
        mChatView =  v.findViewById(R.id.chat_view);
        loadMessages();
        //Set UI parameters if you need
        mChatView.setRightBubbleColor(Color.RED);
        mChatView.setLeftBubbleColor(Color.RED);
        mChatView.setSendButtonColor(ContextCompat.getColor(v.getContext(), R.color.bleeding));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.WHITE);
        mChatView.setUsernameTextColor(Color.RED);
        mChatView.setSendTimeTextColor(Color.RED);
        mChatView.setDateSeparatorColor(Color.RED);
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);
        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new message
                String msg = mChatView.getInputText();
                //Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(msg)
                        .hideIcon(true)
                        .build();
                //send msg throw socket
                //Set to chat view
                mMessageSenderCallback.sendMessage(msg);
                mChatView.send(message);
                mMessageList.add(message);
                //Reset edit text
                mChatView.setInputText("");
                //receiveMessage(msg);

            }

        });
        return v;
    }


    public static void receiveMessage(String sendText) {
        Message message = new Message.Builder()
                .setUser(you)
                .setRightMessage(false)
                .setMessageText(sendText)
                .hideIcon(true)
                .build();
        //Set to chat view
        mChatView.send(message);
        mMessageList.add(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        if (requestCode != READ_REQUEST_CODE || resultCode != RESULT_OK || data == null) {
            return;
        } */
        Uri uri = data.getData();

        try {
            Bitmap picture = MediaStore.Images.Media.getBitmap(mChatView.getContext().getContentResolver(), uri);
            Message message = new Message.Builder()
                    .setRightMessage(true)
                    .setMessageText(Message.Type.PICTURE.name())
                    .setUser(mUsers.get(0))
                    .hideIcon(true)
                    .setPicture(picture)
                    .setType(Message.Type.PICTURE)
                    .setMessageStatusType(Message.MESSAGE_STATUS_ICON)
                    .build();
            mChatView.send(message);
            //Add message list
            mMessageList.add(message);
            receiveMessage(Message.Type.PICTURE.name());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mMessageSenderCallback = (MessageSender) context;
        } catch (ClassCastException e) {
            // Error, class doesn't implement the interface
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMessageSenderCallback = null;
    }


    interface OnFragmentInteractionListener {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();}



    private void initUsers() {
        mUsers = new ArrayList<>();
//        int myId = 0;
   //     Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
     //   String myName = "User";
        final User me = new User(myId, myName, myIcon);
        final User you = new User(myId, myName, myIcon);
        mUsers.add(me);
        mUsers.add(you);
    }

    /**
     * Load saved messages
     */
    private void loadMessages() {
        List<Message> messages = new ArrayList<>();
        mMessageList = AppData.getMessageList(getContext());
        if (mMessageList == null) {
            mMessageList = new MessageList();
        } else {
            for (int i = 0; i < mMessageList.size(); i++) {
                Message message = mMessageList.get(i);
                //Set extra info because they were removed before save messages.
                for (User user : mUsers) {
                    if (message.getUser().getId() == user.getId()) {
                        message.getUser().setIcon(user.getIcon());
                        message.hideIcon(true);
                    }
                }
                if (!message.isDateCell() && message.isRightMessage()) {
                    message.hideIcon(true);

                }
                message.setMessageStatusType(Message.MESSAGE_STATUS_ICON_RIGHT_ONLY);
                message.setStatusIconFormatter(new MyMessageStatusFormatter(getContext()));
                message.setStatus(MyMessageStatusFormatter.STATUS_DELIVERED);
                messages.add(message);
            }
        }
        MessageView messageView = mChatView.getMessageView();
        messageView.init(messages);
        messageView.setSelection(messageView.getCount() - 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUsers();
    }
    @Override
    public void onPause() {
        super.onPause();
        //Save message
        AppData.putMessageList(getContext(), mMessageList);
    }
    interface MessageSender {
        void sendMessage(String message);

    }



}
