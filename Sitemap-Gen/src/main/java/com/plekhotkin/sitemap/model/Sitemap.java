package com.plekhotkin.sitemap.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (
    name = "urlset")
public final class Sitemap {

    @XmlAttribute (
        name = "xmlns")
    public final String xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9";

    private List<SitemapUrl> sitemapUrls;

    public List<SitemapUrl> getSitemapUrls() {
        return sitemapUrls;
    }

    @XmlElementRefs (@XmlElementRef (
        type = SitemapUrl.class))
    public void setSitemapUrls(List<SitemapUrl> sitemapUrls) {
        this.sitemapUrls = sitemapUrls;
    }
}
