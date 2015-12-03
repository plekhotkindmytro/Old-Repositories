import java.util.*;
import java.io.*;
import java.net.*;

class UrlLoader {

	public List<URL> getUrlList() throws IOException {
		
		List<URL> urlList = new ArrayList<URL>();

		BufferedReader inputStream = new BufferedReader(new FileReader("urls.txt"));

		String line;
		while ((line = inputStream.readLine())!= null) {
			urlList.add(new URL(line));

		}
		return urlList;
	}

	public static void main(String [] args)  throws IOException {
		UrlLoader urlLoader = new UrlLoader();
		List<URL> urlList = urlLoader.getUrlList();
		for (URL url: urlList) {
			System.out.println(url);	
		}
		System.out.println(urlList.size());
	}
}
