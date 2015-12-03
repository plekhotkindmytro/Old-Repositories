package com.plekhotkin.sitemap.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plekhotkin.sitemap.exception.ServiceException;
import com.plekhotkin.sitemap.model.ChangeFrequency;
import com.plekhotkin.sitemap.model.LastModification;
import com.plekhotkin.sitemap.model.Priority;
import com.plekhotkin.sitemap.model.Sitemap;
import com.plekhotkin.sitemap.model.SitemapUrl;
import com.plekhotkin.sitemap.util.ApplicationProperties;

public class SitemapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SitemapService.class);

    private static String sitemapFolder = ApplicationProperties.getProperty("sitemapFolder");

    public String generateSitemap(String websiteUrl, ChangeFrequency changeFrequency, LastModification lastmod, Priority priority) throws ServiceException {
        try {
            WebsiteAnalysisService analysisService = new WebsiteAnalysisService(websiteUrl);
            Set<String> urls = analysisService.getUrls();

            Sitemap sitemap = buildSitemap(urls, changeFrequency, lastmod, priority);
            String fileName = saveSitemap(sitemap, websiteUrl);

            return fileName;
        } catch (IOException e) {
            throw new ServiceException(e);
        } catch (JAXBException e) {
            throw new ServiceException(e);
        }
    }

    private Sitemap buildSitemap(Set<String> urls, ChangeFrequency changeFrequency, LastModification lastmod, Priority priority) {
        Sitemap sitemap = new Sitemap();
        List<SitemapUrl> sitemapUrls = new ArrayList<SitemapUrl>();
        for (String url : urls) {
            SitemapUrl sitemapUrl = new SitemapUrl();
            sitemapUrl.setLocation(url);
            if (changeFrequency != null) {
                sitemapUrl.setChangeFrequency(changeFrequency);
            }

            if (lastmod == LastModification.SERVER) {
                sitemapUrl.setLastModification(new Date());
            }

            if (priority == Priority.RANDOM) {
                BigDecimal random = new BigDecimal(Math.random());
                random = random.setScale(1, BigDecimal.ROUND_DOWN);
                sitemapUrl.setPriority(random);
            }

            LOGGER.debug("Sitemap URL: " + url);
            sitemapUrls.add(sitemapUrl);
        }

        sitemap.setSitemapUrls(sitemapUrls);
        return sitemap;
    }

    private String saveSitemap(Sitemap sitemap, String websiteUrl) throws MalformedURLException, JAXBException {
        URL url = new URL(websiteUrl);
        String fileName = url.getHost();
        fileName = fileName.replace(url.getProtocol(), "");
        fileName = fileName.replace(":", "");
        fileName += ".xml";

        new File(sitemapFolder).mkdirs();

        File file = new File(sitemapFolder + fileName);

        JAXBContext jaxbContext = JAXBContext.newInstance(Sitemap.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(sitemap, file);

        return fileName;
    }

}
