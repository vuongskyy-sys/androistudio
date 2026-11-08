package vn.edu.vhu.ltdd.doanandroi.models;

import java.io.Serializable;

public class Tutor implements Serializable {
    private String id;
    private String name;
    private String avatar;
    private String title;
    private String university;
    private String subjects; // Comma separated: "Toán 12, Luyện thi ĐH"
    private String category; // "math", "languages", "sciences", "tech"
    private double hourlyRate;
    private float rating;
    private int reviewsCount;
    private String district;
    private String mode;
    private String badge;
    private String bio;
    private String experience;
    private String achievements;
    private boolean verified;

    public Tutor() {
    }

    public Tutor(String id, String name, String avatar, String title, String university,
                 String subjects, String category, double hourlyRate, float rating,
                 int reviewsCount, String district, String mode, String badge,
                 String bio, String experience, String achievements, boolean verified) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.title = title;
        this.university = university;
        this.subjects = subjects;
        this.category = category;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.district = district;
        this.mode = mode;
        this.badge = badge;
        this.bio = bio;
        this.experience = experience;
        this.achievements = achievements;
        this.verified = verified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
