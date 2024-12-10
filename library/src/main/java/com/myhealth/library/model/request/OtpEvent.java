package com.myhealth.library.model.request;

public class OtpEvent {
    private String email;
    private String otp;

   public OtpEvent(String email, String otp){
        this.email = email;
        this.otp = otp;
    }
    public OtpEvent(){}

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
