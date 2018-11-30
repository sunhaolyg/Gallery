package com.gallery;

public class RegisterCodeBean {

    private String success;
    private String error;
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegisterCodeBean{" +
                "success=" + success +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
