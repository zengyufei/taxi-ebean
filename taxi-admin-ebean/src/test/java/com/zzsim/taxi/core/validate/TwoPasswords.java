package com.zzsim.taxi.core.validate;

import com.zzsim.taxi.admin.validate.annotation.Matche;
import com.zzsim.taxi.admin.validate.annotation.Matches;

@Matches({
        @Matche(field = "password", notBlank = true, message = "密码不能为空"),
        @Matche(field = "confirmPassword", notBlank = true, message = "重复密码不能为空"),
})
public class TwoPasswords {
    @Matche(field = "password", notBlank = true, message = "密码不能为空")
    private String password;
    @Matche(field = "confirmPassword", notBlank = true, message = "重复密码不能为空")
    private String confirmPassword;
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public String getConfirmPassword() {  
        return confirmPassword;  
    }  
  
    public void setConfirmPassword(String confirmPassword) {  
        this.confirmPassword = confirmPassword;  
    }  
}    