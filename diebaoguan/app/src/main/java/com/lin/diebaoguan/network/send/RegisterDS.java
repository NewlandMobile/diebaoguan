package com.lin.diebaoguan.network.send;

import com.lin.diebaoguan.network.send.BaseSendTemplate;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class RegisterDS extends BaseSendTemplate {
    String encoding="utf8";
    String username;
    String password;
    String email;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
