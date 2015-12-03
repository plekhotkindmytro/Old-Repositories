import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class PageAnalizer {

	private List<String> tagList;
	private List<URL> urlList;

	private Map<String, Map<String,Integer>> allInfo;
	
	public PageAnalizer(List<String> tagList, List<URL> urlList) {
		this.tagList = tagList;
		this.urlList = urlList;
	}

	public void analize() throws IOException {

		if (urlList != null) {
			allInfo = new HashMap<String, Map<String,Integer>>();


			for(URL url: urlList) {
				Map <String, Integer> tagCount = getTagCount(url);
				
				Map<String, Integer> sortedTagCount = sortByComparator(tagCount);
				
				save(url, sortedTagCount);
				
				allInfo.put(url.toString(), sortedTagCount);
			}

			// save all info.

			saveAll();
		} else {
			System.out.println("No pages to analize.");
		}
	}
	
	private Map<String, Integer> getTagCount(URL url) throws IOException{
		URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                     yc.getInputStream()));
        String inputLine;
        Map<String, Integer> tagsCount = new HashMap<String, Integer>();

		String pageMarkup = "";
        while ((inputLine = in.readLine()) != null) {
         	pageMarkup += inputLine;
         	
		}
		in.close();
		pageMarkup = pageMarkup.toLowerCase();
		
		if (tagList != null) {
				for(String tag : tagList) {
					Pattern pattern = Pattern.compile("<"+tag + "\\b");
					Matcher  matcher = pattern.matcher(pageMarkup);

					int count = 0;
					while (matcher.find()){
						count++;
					}
					if (tagsCount.containsKey(tag)) {
						tagsCount.put(tag, count + (int)tagsCount.get(tag));
					} else  if (count > 0){
						tagsCount.put(tag, count);
					}
				}
			}
		return tagsCount; 
	}

	private void save(URL url, Map <String, Integer> tagsCount) throws IOException {
		String urlString = url.toString();
		urlString = urlString.replace(url.getProtocol(), "");
		 
		urlString = urlString.replace("://", "");
		urlString = urlString.replace("/", "_"); 
		
		PrintWriter writer = new PrintWriter(new FileOutputStream("data/"+urlString + ".txt"));
	
		for(String key: tagsCount.keySet()) {
			writer.println(key + ": "+ tagsCount.get(key));
        }
        writer.close();
        System.out.println("***** "+urlString+ " *****");
	}

	private void saveAll() throws IOException {
		// Map<String, Map<String,Integer>> allInfo;
		
		Map<String, Integer> tagMap = new HashMap<String, Integer>();
		int allCount = 0;
		// Horrible!
		if (allInfo != null) {
			for (Map<String,Integer> info: allInfo.values()) {
				for (String tag: tagList) {
					if (info.containsKey(tag)) {
						allCount += (int)info.get(tag);
						if (tagMap.containsKey(tag)) {
							tagMap.put(tag, (int)tagMap.get(tag) + (int)info.get(tag));
						} else {
							tagMap.put(tag, (int)info.get(tag));
						}
					}
				}	
			}

			for (String tag: tagList) {
				if (tagMap.containsKey(tag)) {
					tagMap.put(tag + " "+((double) tagMap.get(tag) / (double) allCount) * 100+ "%", (int)tagMap.get(tag));
					tagMap.remove(tag);
				}
			}
		}
		
		Map<String, Integer> sortedTagMap = sortByComparator(tagMap);
				
		save(new URL("http://AllData"), sortedTagMap);
	}

	private static Map sortByComparator(Map unsortMap) {
 
		List list = new LinkedList(unsortMap.entrySet());
 
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
                                       .compareTo(((Map.Entry) (o1)).getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public static void main(String[] args) throws Exception {

       	TagLoader tagLoader = new TagLoader();
		List<String> tagList = tagLoader.getTagList();
		
		List<URL> urlList = new ArrayList<URL>();
		if (args.length == 0) {
				UrlLoader urlLoader = new UrlLoader();
				urlList = urlLoader.getUrlList();
		} else {
			for (int i = 0; i < args.length; i++) {
				urlList.add(new URL(args[i]));
			}
		}

		PageAnalizer pageAnalizer = new PageAnalizer(tagList, urlList);
		pageAnalizer.analize();
	}
}
