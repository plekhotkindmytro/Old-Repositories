import java.util.*;
import java.io.*;

class TagLoader {

	private static List<String> tagList;
	private static final String SIZE = "size";
	public static List<String> getTagList() throws IOException {
		
		if (tagList == null) {
			tagList = new ArrayList<String>();	
			
			BufferedReader inputStream = new BufferedReader(new FileReader("tags.txt"));
			String line;
			while ((line = inputStream.readLine())!= null) {
				tagList.add(line);

			}
		}

		return tagList;
	}

	public static void main(String [] args)  throws IOException {
		getTagList();

		for (int i = 0; i < tagList.size()-1; i+=5) {
		
			System.out.format("%-20s%-20s%-20s%-20s", 
				tagList.get(i), 
				tagList.get(i+1), 
				tagList.get(i+2), 
				tagList.get(i+3));
			System.out.println();
		}

		final int tagListSize = tagList.size();		
				
		System.out.println("\n+----------+");
		System.out.format("|   %-4s   |\n", SIZE);
		System.out.println("+----------+");
		System.out.format("|   %-4s   |\n", tagListSize);
		System.out.println("+----------+");
		
	}
}
