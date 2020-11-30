package com.rutwik.farmerohfarmer.Models;

public class Output {
    
    String status;
    String message;
    Object result;

    public Output() {
    }

    public Output(String status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Output status(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Output message(String message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Output result(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " status='" + getStatus() + "'" +
            ", message='" + getMessage() + "'" +
            ", result='" + getResult() + "'" +
            "}";
    }

}
