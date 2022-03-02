/*
 * Copyright (C) 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.isif.reviewandroid.notification;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import cn.isif.reviewandroid.R;

/** Mock data for each of the Notification Style Demos. */
public final class MockDatabase {

    public static StyleSocialAppData getStyleData() {
        return StyleSocialAppData.getInstance();
    }

    /** Represents data needed for BigPictureStyle Notification. */
    public static class StyleSocialAppData extends MockNotificationData {

        private static StyleSocialAppData sInstance = null;

        // Unique data for this Notification.Style:
        private int mBigImage;
        private String mBigContentTitle;
        private String mSummaryText;

        private CharSequence[] mPossiblePostResponses;

        private ArrayList<String> mParticipants;

        public static StyleSocialAppData getInstance() {
            if (sInstance == null) {
                sInstance = getSync();
            }
            return sInstance;
        }

        private static synchronized StyleSocialAppData getSync() {
            if (sInstance == null) {
                sInstance = new StyleSocialAppData();
            }

            return sInstance;
        }

        private StyleSocialAppData() {
            // Standard Notification values:
            // Title/Content for API <16 (4.0 and below) devices.
            mContentTitle = "Bob's Post";
            mContentText = "[Picture] Like my shot of Earth?";
            mPriority = NotificationCompat.PRIORITY_HIGH;

            // Style notification values:
            mBigImage = R.drawable.earth;
            mBigContentTitle = "Bob's Post";
            mSummaryText = "Like my shot of Earth?";

            // This would be possible responses based on the contents of the post.
            mPossiblePostResponses = new CharSequence[] {"Yes", "No", "Maybe?"};

            mParticipants = new ArrayList<>();
            mParticipants.add("Bob Smith");

            // Notification channel values (for devices targeting 26 and above):
            mChannelId = "channel_social_1";
            // The user-visible name of the channel.
            mChannelName = "Sample Social";
            // The user-visible description of the channel.
            mChannelDescription = "Sample Social Notifications";
            mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
            mChannelEnableVibrate = true;
            mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE;
        }

        public int getBigImage() {
            return mBigImage;
        }

        public String getBigContentTitle() {
            return mBigContentTitle;
        }

        public String getSummaryText() {
            return mSummaryText;
        }

        public CharSequence[] getPossiblePostResponses() {
            return mPossiblePostResponses;
        }

        public ArrayList<String> getParticipants() {
            return mParticipants;
        }

        @Override
        public String toString() {
            return getContentTitle() + " - " + getContentText();
        }
    }

    /** Represents standard data needed for a Notification. */
    public abstract static class MockNotificationData {

        // Standard notification values:
        protected String mContentTitle;
        protected String mContentText;
        protected int mPriority;

        // Notification channel values (O and above):
        protected String mChannelId;
        protected CharSequence mChannelName;
        protected String mChannelDescription;
        protected int mChannelImportance;
        protected boolean mChannelEnableVibrate;
        protected int mChannelLockscreenVisibility;

        // Notification Standard notification get methods:
        public String getContentTitle() {
            return mContentTitle;
        }

        public String getContentText() {
            return mContentText;
        }

        public int getPriority() {
            return mPriority;
        }

        // Channel values (O and above) get methods:
        public String getChannelId() {
            return mChannelId;
        }

        public CharSequence getChannelName() {
            return mChannelName;
        }

        public String getChannelDescription() {
            return mChannelDescription;
        }

        public int getChannelImportance() {
            return mChannelImportance;
        }

        public boolean isChannelEnableVibrate() {
            return mChannelEnableVibrate;
        }

        public int getChannelLockscreenVisibility() {
            return mChannelLockscreenVisibility;
        }
    }

    public static Uri resourceToUri(Context context, int resId) {
        return Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://"
                        + context.getResources().getResourcePackageName(resId)
                        + "/"
                        + context.getResources().getResourceTypeName(resId)
                        + "/"
                        + context.getResources().getResourceEntryName(resId));
    }
}
