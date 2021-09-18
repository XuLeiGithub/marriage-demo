package com.jiuyu.bo;

import java.io.Serializable;

/**
 * <p>Title: LogAddressBO</p>
 * <p>Description: </p>
 *
 * @author he_jiebing@jiuyv.com
 * @date 2021年4月24日 下午7:57:44
 */

public class LogAddressBO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4391796405858117896L;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
