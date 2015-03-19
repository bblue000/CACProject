package com.rubick.cac.android.contact.model;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/3/19.
 */
public class CACContactModel {

    public String displayName;
    public String mobileNumber;
    public String homeNumber;
    public String workNumber;
    public String email;
    public String company;
    public String jobTitle;

    public CACContactModel() {

    }

    public static CACContactModel simplePhone(String displayName, String mobileNumber) {
        CACContactModel model = new CACContactModel();
        model.displayName = displayName;
        model.mobileNumber = mobileNumber;
        model.homeNumber = mobileNumber;
        model.workNumber = mobileNumber;
        return model;
    }

    public static CACContactModel simplePhoneWithMail(String displayName, String mobileNumber, String email) {
        CACContactModel model = new CACContactModel();
        model.displayName = displayName;
        model.mobileNumber = mobileNumber;
        model.homeNumber = mobileNumber;
        model.workNumber = mobileNumber;
        model.email = email;
        return model;
    }

    public static CACContactModel simplePhoneWithCom(String displayName, String mobileNumber,String company, String jobTitle) {
        CACContactModel model = new CACContactModel();
        model.displayName = displayName;
        model.mobileNumber = mobileNumber;
        model.homeNumber = mobileNumber;
        model.workNumber = mobileNumber;
        model.company = company;
        model.jobTitle = jobTitle;
        return model;
    }

    public static CACContactModel simplePhoneWithMailAndCom(String displayName, String mobileNumber, String email, String company, String jobTitle) {
        CACContactModel model = new CACContactModel();
        model.displayName = displayName;
        model.mobileNumber = mobileNumber;
        model.homeNumber = mobileNumber;
        model.workNumber = mobileNumber;
        model.email = email;
        model.company = company;
        model.jobTitle = jobTitle;
        return model;
    }
}
