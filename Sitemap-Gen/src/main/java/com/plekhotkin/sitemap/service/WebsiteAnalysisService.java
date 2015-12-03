package com.plekhotkin.sitemap.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plekhotkin.sitemap.util.ApplicationProperties;

public class WebsiteAnalysisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsiteAnalysisService.class);

    private final URL mainPage;
    private final int depthLevel;

    public WebsiteAnalysisService(String startPageUrl) throws MalformedURLException {
        mainPage = getMainPage(startPageUrl);
        depthLevel = Integer.parseInt(ApplicationProperties.getProperty("depthLevel"));
    }

    private URL getMainPage(String documentUrl) throws MalformedURLException {
        URL url = new URL(documentUrl);
        String filePart = "";
        URL mainPage = new URL(url.getProtocol(), url.getHost(), url.getPort(), filePart);
        return mainPage;
    }

    public Set<String> getUrls() throws IOException {
        final Set<String> urls = new LinkedHashSet<String>();

        Set<String> currentLevelInnerLinks = getInnerLinks(mainPage.toString(), urls);
        urls.addAll(currentLevelInnerLinks);

        int level = 1;
        while (level <= depthLevel && currentLevelInnerLinks.size() != 0) {
            LOGGER.debug("Level {}:", level);
            final Set<String> nextLevelInnerLinks = new LinkedHashSet<String>();
            for (String link : currentLevelInnerLinks) {
                try {
                    nextLevelInnerLinks.addAll(getInnerLinks(link, urls));
                } catch (Exception e) {
                    LOGGER.warn("Problem with analysing url '{}': {}", link, e.getMessage());
                }
            }
            // nextLevelInnerLinks.removeAll(urls);
            urls.addAll(nextLevelInnerLinks);

            currentLevelInnerLinks = nextLevelInnerLinks;
            level++;
        }

        // clean up
        for (Iterator<String> iterator = urls.iterator(); iterator.hasNext();) {
            String url = iterator.next();
            if (!url.endsWith("/") && urls.contains(url.concat("/"))) {
                iterator.remove();
            }
        }
        LOGGER.debug("Total inner link count: {}", urls.size());
        return urls;
    }

    private Set<String> getInnerLinks(String documentUrl, Set<String> existingLinks) throws IOException {
        Document document = Jsoup.connect(documentUrl).get();
        Elements anchors = document.select("a[href]");
        Set<String> innerLinks = new LinkedHashSet<String>();
        for (Element a : anchors) {
            String href = a.attr("abs:href");
            href = removeRef(href);
            if (innerLink(href) && !existingLinks.contains(href) && !href.contains("/.")) {
                innerLinks.add(href);
            }
        }
        LOGGER.debug("Added {} inner links.", innerLinks.size());
        return innerLinks;
    }

    private String removeRef(String href) {
        int refIndex = href.indexOf('#');
        String result = href;
        if (refIndex != -1) {
            result = href.substring(0, refIndex);
        }
        return result;

    }

    private boolean innerLink(String urlString) {
        boolean isInnerLink = false;
        try {
            URL url = new URL(urlString);
            isInnerLink = mainPage.getProtocol().equals(url.getProtocol()) && mainPage.getHost().equals(url.getHost()) && mainPage.getPort() == url.getPort();
        } catch (MalformedURLException e) {
            LOGGER.warn(e.getMessage());
        }
        return isInnerLink;
    }

}
