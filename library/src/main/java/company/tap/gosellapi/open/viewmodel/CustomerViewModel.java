package company.tap.gosellapi.open.viewmodel;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class CustomerViewModel implements Serializable ,Comparable<CustomerViewModel>{

    private String name;
    private String middleName;
    private String lastName;
    private String email;
    private String mobile;
    private String sdn;

    public CustomerViewModel(@NonNull final String name,
                             @NonNull final String middle,
                             @NonNull final String last,
                             @NonNull final String email,
                             @NonNull final String sdn,
                             @NonNull String mobile) {
        setSimpleText(name,middle,last,email,sdn,mobile);
    }

    @NonNull
    public String getName() {
        return name;
    }


    @NonNull
    public String getEmail()
    {
        return email;
    }

    @NonNull
    public String getMobile(){
        return mobile;
    }

    public void setSimpleText(@NonNull final String name,
                              @NonNull final String middle,
                              @NonNull final String last,
                              @NonNull final String email,
                              @NonNull final String sdn,
                              @NonNull String mobile) {
        this.name = name;
        this.middleName = middle;
        this.lastName = last;
        this.email = email;
        this.sdn = sdn;
        this.mobile = mobile;
    }


    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSdn() {
        return sdn;
    }

    @Override
    public int compareTo(CustomerViewModel o) {

        String thisMobile = getMobile();
        String otherMobile = o.getMobile();

        return thisMobile.compareToIgnoreCase(otherMobile);
    }
}
