package com.plekhotkin.sitemap.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (
    name = "url")
public class SitemapUrl {

    private String location;

    private Date lastModification;

    private ChangeFrequency changeFrequency;

    private BigDecimal priority;

    public String getLocation() {
        return location;
    }

    @XmlElement(name="loc")
    public void setLocation(String location) {
        this.location = location;
    }

    public Date getLastModification() {
        return lastModification;
    }

    @XmlElement(name="lastmod")
    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public ChangeFrequency getChangeFrequency() {
        return changeFrequency;
    }

    @XmlElement(name="changefreq")
    public void setChangeFrequency(ChangeFrequency changeFrequency) {
        this.changeFrequency = changeFrequency;
    }

    public BigDecimal getPriority() {
        return priority;
    }

    @XmlElement(name="priority")
    public void setPriority(BigDecimal priority) {
        this.priority = priority;
    }

}
