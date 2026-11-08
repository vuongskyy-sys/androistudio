package vn.edu.vhu.ltdd.doanandroi.models;

import java.io.Serializable;

public class Booking implements Serializable {
    private String id;
    private String tutorId;
    private String tutorName;
    private String tutorAvatar;
    private String parentName;
    private String phone;
    private String studentName;
    private String studentGrade;
    private String subject;
    private String mode;
    private String date;
    private String timeSlot;
    private String note;
    private String status; // "Confirmed", "Pending", "Cancelled"

    public Booking() {
    }

    public Booking(String id, String tutorId, String tutorName, String tutorAvatar,
                   String parentName, String phone, String studentName, String studentGrade,
                   String subject, String mode, String date, String timeSlot, String note, String status) {
        this.id = id;
        this.tutorId = tutorId;
        this.tutorName = tutorName;
        this.tutorAvatar = tutorAvatar;
        this.parentName = parentName;
        this.phone = phone;
        this.studentName = studentName;
        this.studentGrade = studentGrade;
        this.subject = subject;
        this.mode = mode;
        this.date = date;
        this.timeSlot = timeSlot;
        this.note = note;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorAvatar() {
        return tutorAvatar;
    }

    public void setTutorAvatar(String tutorAvatar) {
        this.tutorAvatar = tutorAvatar;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
