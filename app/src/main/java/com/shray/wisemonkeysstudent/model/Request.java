package com.shray.wisemonkeysstudent.model;

/**
 * Created by Shray on 5/14/2017.
 */

public class Request {

    String tutorName;
    String studentUid;
    String tutorUid;
    String Status;
    String key;
    String studentToken;
    String languageLevel;
    String studenName;

    public Request(String studenName, String studentUid, String tutorUid,
                   String key, String Status, String studentToken,String tutorName,String languageLevel) {
        this.tutorName = tutorName;
        this.studentUid = studentUid;
        this.tutorUid = tutorUid;
        this.Status = Status;
        this.key = key;
        this.studentToken = studentToken;
        this.studenName=studenName;
        this.languageLevel=languageLevel;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

    public String getStudenName() {
        return studenName;
    }

    public void setStudenName(String studenName) {
        this.studenName = studenName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public String getTutorUid() {
        return tutorUid;
    }

    public void setTutorUid(String tutorUid) {
        this.tutorUid = tutorUid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStudentToken() {
        return studentToken;
    }

    public void setStudentToken(String studentToken) {
        this.studentToken = studentToken;
    }
}
