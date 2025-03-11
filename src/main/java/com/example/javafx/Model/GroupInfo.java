package com.example.javafx.Model;

public class GroupInfo {
    private String groupName;
    private int maxEmployees;
    private String fillPercentage;

    public GroupInfo(String groupName, int maxEmployees, String fillPercentage) {
        this.groupName = groupName;
        this.maxEmployees = maxEmployees;
        this.fillPercentage = fillPercentage;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getMaxEmployees() {
        return maxEmployees;
    }

    public String getFillPercentage() {
        return fillPercentage;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMaxEmployees(int maxEmployees) {
        this.maxEmployees = maxEmployees;
    }

    public void setFillPercentage(String fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

}
