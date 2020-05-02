package com.example.shaadi;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shaadi.data.ProfileContract.ProfileEntry;
import com.example.shaadi.utilities.ItemNumberIndexForRecyclerList;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final Context mContext;
    private Cursor mCursor;
    private final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    private List<Object> mRecyclerViewItems;

    final private ProfileAdapterOnClickHandler mClickHandler;

    public interface  ProfileAdapterOnClickHandler{
        void onClick(String profileUsername, int acceptanceId, int adapterPosition);
    }

    public void swapCursor(Cursor newCursor) {
        Log.i(LOG_TAG,"cursor swapped");
        mCursor = newCursor;
        Log.i(LOG_TAG, String.valueOf(mCursor.getCount()));

    }

    public RecyclerViewAdapter(Context context, ProfileAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler=clickHandler;
    }

    public void setRecyclerViewItems(List<Object> recyclerViewItems){
        mRecyclerViewItems = recyclerViewItems;
    }

    public List<Object> getmRecyclerViewItems() {
        return mRecyclerViewItems;
    }

    public class ProfileItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final SimpleDraweeView profileImageView;
        final TextView profileName; // format "First name & first letter of last name"
        final TextView profileAge; // format "21 yrs"
        final TextView profileCityState; // format "City, State"
        final TextView acceptanceResult;
        final Button declineButton;
        final Button acceptButton;

        ProfileItemViewHolder(View view) {
            super(view);

            profileImageView = view.findViewById(R.id.profileImageView);
            profileName = view.findViewById(R.id.profile_name);
            profileAge = view.findViewById(R.id.ageTextView);
            profileCityState = view.findViewById(R.id.cityStateTextView);
            acceptanceResult = view.findViewById(R.id.acceptanceResultTextView);
            declineButton = view.findViewById(R.id.declineButton);
            acceptButton = view.findViewById(R.id.acceptButton);


            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        //return mCursor.getCount();
        return mRecyclerViewItems.size();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View profileItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.profile_card, viewGroup, false);
        profileItemLayoutView.setFocusable(true);
        return new ProfileItemViewHolder(profileItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final ProfileItemViewHolder profileItemHolder = (ProfileItemViewHolder) holder;

        ItemNumberIndexForRecyclerList itemNumberIndexForRecyclerList = (ItemNumberIndexForRecyclerList) mRecyclerViewItems.get(position);
        mCursor.moveToPosition(itemNumberIndexForRecyclerList.getCursorPosition());


        String profileUsername = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_USERNAME));


        String profileImageUrl = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_IMAGE));
        if (profileImageUrl != null && !profileImageUrl.equals("")) {
            Uri profileImageUri = Uri.parse(profileImageUrl);
            profileItemHolder.profileImageView.setImageURI(profileImageUri);
        }

        String profileFirstName = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_FIRST_NAME));
        String profileLastName = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_LAST_NAME));
        String displayName = profileFirstName + profileLastName.charAt(0); // like "Abhijeet R"
        profileItemHolder.profileName.setText(displayName);

        int profileAge = mCursor.getInt(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_AGE));
        String displayAge = Integer.toString(profileAge) + " yrs";
        profileItemHolder.profileAge.setText(displayAge);


        String profileCity = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_CITY));
        String profileState = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_STATE));
        String displayLocation = profileCity + ", " + profileState;
        profileItemHolder.profileCityState.setText(displayLocation);

        int acceptanceId = mCursor.getInt(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_ACCEPTANCE_ID));
        if (acceptanceId == -1){
            profileItemHolder.declineButton.setVisibility(View.VISIBLE);
            profileItemHolder.acceptButton.setVisibility(View.VISIBLE);
            profileItemHolder.acceptanceResult.setVisibility(View.GONE);
        }
        else {
            profileItemHolder.declineButton.setVisibility(View.GONE);
            profileItemHolder.acceptButton.setVisibility(View.GONE);
            profileItemHolder.acceptanceResult.setVisibility(View.VISIBLE);

            if (acceptanceId == 0){
                profileItemHolder.acceptanceResult.setTextColor(ContextCompat.getColor(mContext, R.color.decline));
                profileItemHolder.acceptanceResult.setText("Member declined");
            } else {
                profileItemHolder.acceptanceResult.setTextColor(ContextCompat.getColor(mContext, R.color.accept));
                profileItemHolder.acceptanceResult.setText("Member accepted");
            }
        }

        profileItemHolder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCursor.moveToPosition(position);

                String profileUsername = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_USERNAME));

                int acceptanceId = 0;
                mClickHandler.onClick(profileUsername, acceptanceId, position);

            }
        });

        profileItemHolder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCursor.moveToPosition(position);

                String profileUsername = mCursor.getString(mCursor.getColumnIndex(ProfileEntry.COLUMN_PROFILE_USERNAME));

                int acceptanceId = 1;
                mClickHandler.onClick(profileUsername, acceptanceId, position);

            }
        });

    }


}
