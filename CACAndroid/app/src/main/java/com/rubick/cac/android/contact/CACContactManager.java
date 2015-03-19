package com.rubick.cac.android.contact;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.rubick.cac.android.contact.model.CACContactModel;

import java.util.ArrayList;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/3/19.
 */
public class CACContactManager {

    private static CACContactManager sInstance;
    public static CACContactManager getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new CACContactManager(context.getApplicationContext());
        }
        return sInstance;
    }
    protected Context mContext;
    private CACContactManager(Context context) {
        mContext = context;
    }

    public boolean validContact(CACContactModel contact) {
        if (null == contact) {
            return false;
        }
        return !TextUtils.isEmpty(contact.displayName)
                && (!TextUtils.isEmpty(contact.mobileNumber)
                    || !TextUtils.isEmpty(contact.homeNumber)
                    || !TextUtils.isEmpty(contact.workNumber));
    }

    public boolean addContact(CACContactModel contact) {
        if (!validContact(contact)) {
            return false;
        }
        ArrayList <ContentProviderOperation> ops = new ArrayList< ContentProviderOperation >();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        contact.displayName).build());

        //------------------------------------------------------ Mobile Number
        if (!TextUtils.isEmpty(contact.mobileNumber)) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.mobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        //------------------------------------------------------ Home Numbers
        if (!TextUtils.isEmpty(contact.homeNumber)) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.homeNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build());
        }

        //------------------------------------------------------ Work Numbers
        if (!TextUtils.isEmpty(contact.workNumber)) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.workNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Email
        if (!TextUtils.isEmpty(contact.email)) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, contact.email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Organization
        if (!TextUtils.isEmpty(contact.company) && !TextUtils.isEmpty(contact.jobTitle)) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, contact.company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, contact.jobTitle)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }

        // Asking the Contact provider to create a new contact
        try {
            ContentProviderResult[] results = mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            ContentProviderResult insertNameResult = results[1];
            return null != insertNameResult.uri;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
