package company.tap.sample.viewmodels;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

public class CustomerViewModel implements Serializable ,Comparable<CustomerViewModel>{

    private String ref;
    private String name;
    private String middleName;
    private String lastName;
    private String email;
    private String mobile;
    private String sdn;

    public CustomerViewModel(@Nullable String ref,
            @NonNull final String name,@NonNull final String middle,
                             @NonNull final String last,@NonNull final String email,
                             @NonNull String sdn, @NonNull String mobile) {
        setSimpleText(ref,name,middle,last,email,sdn,mobile);
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    @NonNull
    public String getEmail()
    {
        return email;
    }


    public String getSdn() {
        return sdn;
    }


    public String getRef() {
        return ref;
    }

    @NonNull
    public String getMobile(){
        return mobile;
    }

    public void setSimpleText(@Nullable String ref ,@NonNull final String name,@NonNull final String middle,
                              @NonNull final String last,@NonNull final String email,
                              @NonNull String sdn, @NonNull String mobile) {
        this.ref = ref;
        this.name = name;
        this.middleName = middle;
        this.lastName = last;
        this.email = email;
        this.sdn = sdn;
        this.mobile = mobile;
    }

    @Override
    public int compareTo(CustomerViewModel o) {

        String thisMobile = getMobile();
        String otherMobile = o.getMobile();

        return thisMobile.compareToIgnoreCase(otherMobile);
    }
}
