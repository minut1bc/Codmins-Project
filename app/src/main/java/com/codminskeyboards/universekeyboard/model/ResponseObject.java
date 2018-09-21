package com.codminskeyboards.universekeyboard.model;

public class ResponseObject<T> {

    private String message;

    private String status_code;

    private String response_code;

    private T response_data;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus_code ()
    {
        return status_code;
    }

    public void setStatus_code (String status_code)
    {
        this.status_code = status_code;
    }

    public String getResponse_code ()
    {
        return response_code;
    }

    public void setResponse_code (String response_code)
    {
        this.response_code = response_code;
    }

    public T getResponse_data ()
    {
        return response_data;
    }

    public void setResponse_data (T response_data)
    {
        this.response_data = response_data;
    }
}
