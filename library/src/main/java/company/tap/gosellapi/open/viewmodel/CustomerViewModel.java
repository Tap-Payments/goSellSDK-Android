package company.tap.gosellapi.open.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * The type Customer view model.
 */
public class CustomerViewModel implements Serializable ,Comparable<CustomerViewModel>{

    private String ref;
    private String name;
    private String middleName;
    private String lastName;
    private String email;
    private String mobile;
    private String sdn;

    /**
     * Instantiates a new Customer view model.
     *
     * @param name   the name
     * @param middle the middle
     * @param last   the last
     * @param email  the email
     * @param sdn    the sdn
     * @param mobile the mobile
     */
    public CustomerViewModel(@Nullable String ref,
                             @NonNull final String name,
                             @NonNull final String middle,
                             @NonNull final String last,
                             @NonNull final String email,
                             @NonNull final String sdn,
                             @NonNull String mobile) {
        setSimpleText(name,middle,last,email,sdn,mobile);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @NonNull
    public String getName() {
        return name;
    }


    /**
     * Gets email.
     *
     * @return the email
     */
    @NonNull
    public String getEmail()
    {
        return email;
    }

    /**
     * Get mobile string.
     *
     * @return the string
     */
    @NonNull
    public String getMobile(){
        return mobile;
    }

    /**
     * Sets simple text.
     *
     * @param name   the name
     * @param middle the middle
     * @param last   the last
     * @param email  the email
     * @param sdn    the sdn
     * @param mobile the mobile
     */
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


    /**
     * Gets middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets sdn.
     *
     * @return the sdn
     */
    public String getSdn() {
        return sdn;
    }

    /**
     * return customer ref.
     * @return
     */
    public String getRef() {
        return ref;
    }

    @Override
    public int compareTo(CustomerViewModel o) {

        String thisMobile = getMobile();
        String otherMobile = o.getMobile();

        return thisMobile.compareToIgnoreCase(otherMobile);
    }
}
