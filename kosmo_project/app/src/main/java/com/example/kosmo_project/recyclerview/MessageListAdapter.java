package com.example.kosmo_project.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kosmo_project.R;
import com.example.kosmo_project.listviewadapter.Lecture_ListViewAdapter;
import com.example.kosmo_project.listviewadapter.ListViewItem;
import com.example.kosmo_project.listviewadapter.Recv_ListViewAdapter;
import com.example.kosmo_project.listviewadapter.Score_ListViewAdapter;
import com.example.kosmo_project.vo.Lecture;
import com.example.kosmo_project.vo.Message;
import com.example.kosmo_project.vo.StdScore;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = Message.VIEW_TYPE_MESSAGE_SENT;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = Message.VIEW_TYPE_MESSAGE_RECEIVED;

    private Context mContext;
    private List<Message> mMessageList;
    private InputMethodManager imm;



    public MessageListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
        imm = (InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE);
    }


    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) mMessageList.get(position);

        if (message.getType() == VIEW_TYPE_MESSAGE_SENT) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            view.setOnClickListener((v)->{
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            });
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            view.setOnClickListener((v)->{
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            });
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }



    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, isVocieText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            isVocieText = (TextView) itemView.findViewById(R.id.text_voice);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getTime());

            if(message.isVoice())
                isVocieText.setText("Voice : ");
            else
                isVocieText.setText("");
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;
        ListView listView;
        ListView score_listview;
        ListView timetable_listview;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
            listView = (ListView) itemView.findViewById(R.id.recv_message_listview);
            score_listview = (ListView) itemView.findViewById(R.id.score_listview);
            timetable_listview = (ListView) itemView.findViewById(R.id.timetable_listview);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            timeText.setText(message.getTime());
            nameText.setText(message.getNickname());
            Recv_ListViewAdapter recv_listViewAdapter = null;
            Score_ListViewAdapter score_listViewAdapter = null;
            Lecture_ListViewAdapter lecture_listViewAdapter = null;

            if(message.getWeathers()!=null){
                recv_listViewAdapter = new Recv_ListViewAdapter(message.getWeathers());
                listView.setAdapter(recv_listViewAdapter);
                Utility.setListViewHeightBasedOnChildren(listView);
            }else{
                recv_listViewAdapter = new Recv_ListViewAdapter(new ArrayList<ListViewItem>());
                listView.setAdapter(recv_listViewAdapter);
                Utility.setListViewHeightBasedOnChildren(listView);
            }

            if(message.getStdScoreList()!=null){
                score_listViewAdapter = new Score_ListViewAdapter(message.getStdScoreList());
                score_listview.setAdapter(score_listViewAdapter);
                Utility.setListViewHeightBasedOnChildren(score_listview);
            }else{
                score_listViewAdapter = new Score_ListViewAdapter(new ArrayList<StdScore>());
                score_listview.setAdapter(score_listViewAdapter);
                Utility.setListViewHeightBasedOnChildren(score_listview);
            }

            if(message.getLectures()!=null){
                lecture_listViewAdapter = new Lecture_ListViewAdapter(message.getLectures());
                timetable_listview.setAdapter(lecture_listViewAdapter);
                Utility.setListViewHeightBasedOnChildren(timetable_listview);
            }else{
                lecture_listViewAdapter = new Lecture_ListViewAdapter(new ArrayList<Lecture>());
                timetable_listview.setAdapter(lecture_listViewAdapter);
                Utility.setListViewHeightBasedOnChildren(timetable_listview);
            }

        }
    }
}
