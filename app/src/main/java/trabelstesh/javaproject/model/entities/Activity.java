package trabelstesh.javaproject.model.entities;

import android.widget.Switch;

import java.util.Calendar;

public class Activity
{
    private int id;
    private Description description;
    private String country;
    private Calendar startDate;
    private Calendar endDate;
    private int cost;
    private String shortDescription;
    private int BusinessId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Description getDescription() {
        return description;
    }
    public void setDescription(String description)
    {
        for (Description d: Description.values())
            if (d.toString() == description) this.description = d;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Calendar getStartDate() {
        return startDate;
    }
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    public Calendar getEndDate() {
        return endDate;
    }
    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public int getBusinessId() {
        return BusinessId;
    }
    public void setBusinessId(int businessId) {
        BusinessId = businessId;
    }

    public Activity(int id, Description description, String country, Calendar startDate, Calendar endDate, int cost, String shortDescription, int businessId) {
        this.id = id;
        this.description = description;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.shortDescription = shortDescription;
        BusinessId = businessId;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", description=" + description +
                ", country='" + country + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cost=" + cost +
                ", shortDescription='" + shortDescription + '\'' +
                ", BusinessId=" + BusinessId +
                '}';
    }
}
