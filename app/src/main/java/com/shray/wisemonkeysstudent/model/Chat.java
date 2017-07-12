package com.shray.wisemonkeysstudent.model;

/**
 * Created by Shray on 5/14/2017.
 */

public class Chat {
private String name,message;

    public Chat(String name,String message){
        this.name=name;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
