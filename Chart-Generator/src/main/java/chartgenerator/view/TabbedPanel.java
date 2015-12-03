package chartgenerator.view;

import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TLDWebsitesPanel tldWebsitesPanel;
	private SubdomainBlogsPanel subdomainBlogsPanel;
	private VideoSubmissionsPanel videoSubmissionsPanel;
	private ArticleSubmissionsPanel articleSubmissionsPanel;
	private BlogCommentsPanel  blogCommentsPanel;
	private BookmarksPanel bookmarksPanel;
	private T2Panel t2Panel;

	public TabbedPanel() {
		super();
		initComponents();
	}

	private void initComponents() {
		tldWebsitesPanel = new TLDWebsitesPanel();
		addTab("TLD Websites", tldWebsitesPanel);
		
		subdomainBlogsPanel = new SubdomainBlogsPanel();
		addTab("Subdomain Blogs", subdomainBlogsPanel);
		
		videoSubmissionsPanel = new VideoSubmissionsPanel();
		addTab("Video Submissions", videoSubmissionsPanel);
		
		articleSubmissionsPanel = new ArticleSubmissionsPanel();
		addTab("Article Submissions", articleSubmissionsPanel);
		
		blogCommentsPanel = new BlogCommentsPanel();
		addTab("Blog Comments", blogCommentsPanel);
		
		bookmarksPanel = new BookmarksPanel();
		addTab("Bookmarks", bookmarksPanel);
		
		t2Panel = new T2Panel();
		addTab("T2", t2Panel);
	}
}
