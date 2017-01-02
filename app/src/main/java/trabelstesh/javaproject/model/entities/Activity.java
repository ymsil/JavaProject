package trabelstesh.javaproject.model.entities;

import android.widget.Switch;

import java.util.Calendar;
import java.util.Date;

public class Activity
{
    private long id;
    private Description description;
    private String country;
    private Date startDate;
    private Date endDate;
    private int cost;
    private String shortDescription;
    private long BusinessId;

    public long getId() {
        return id;
    }
    public void setId(long id) {
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
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
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
    public Long getBusinessId() {
        return BusinessId;
    }
    public void setBusinessId(Long businessId) {
        BusinessId = businessId;
    }

    public Activity(long id, Description description, String country, Date startDate, Date endDate, int cost, String shortDescription, long businessId) {
        this.id = id;
        this.description = description;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.shortDescription = shortDescription;
        this.BusinessId = businessId;
    }
    public Activity() {}

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
