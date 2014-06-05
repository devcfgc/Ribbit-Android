package com.devcfgc.ribbit;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

public class MessageAdapter extends ArrayAdapter<ParseObject> {

	protected Context mContext;
	protected List<ParseObject> mMessages;

	public MessageAdapter(Context context, List<ParseObject> messages) {
		super(context, R.layout.message_item, messages);
		mContext = context;
		mMessages = messages;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		// If the holder doesn't exist we create a new one
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.message_item, null);
			holder = new ViewHolder();
			holder.iconImageView = (ImageView) convertView
					.findViewById(R.id.messageIcon);
			holder.nameLabel = (TextView) convertView
					.findViewById(R.id.senderLabel);
			
			//we need to set the tag to reused when we scroll
			convertView.setTag(holder);
			
		} else {
			// If the holder exist
			holder = (ViewHolder) convertView.getTag();
		}

		ParseObject message = mMessages.get(position);

		if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(
				ParseConstants.TYPE_IMAGE)) {
			holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
		} else if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(
				ParseConstants.TYPE_VIDEO)) {
			holder.iconImageView
					.setImageResource(R.drawable.ic_action_play_over_video);
		} else if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(
				ParseConstants.TYPE_TXT)) {
			// TODO
		}

		holder.nameLabel.setText(message
				.getString(ParseConstants.KEY_SENDER_NAME));

		return convertView;
	}

	private static class ViewHolder {
		ImageView iconImageView;
		TextView nameLabel;
	}
	
	public void refill(List<ParseObject> messages) {
		mMessages.clear();
		mMessages.addAll(messages);
		notifyDataSetChanged();
	}

}