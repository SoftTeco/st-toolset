package com.softteco.toolset.sample.persons;

import java.io.Serializable;

/**
 * @author serge
 */
public class PersonDto implements Serializable {

    public String account;
    public String name;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
