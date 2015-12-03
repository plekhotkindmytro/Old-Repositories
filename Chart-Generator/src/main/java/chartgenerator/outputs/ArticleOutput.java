package chartgenerator.outputs;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import chartgenerator.model.Links;

public class ArticleOutput implements Output {

	private final LocalDate creationDate;
	private final String id;
	private final Links links;
	private final List<String> anchorList;
	private final List<String> linkList;

	private final int anchorsCount;
	private final int imagesCount;
	private final int videosCount;
	private final int audiosCount;

	public ArticleOutput(LocalDate creationDate, String id, Links links,
			List<String> anchorList, List<String> linkList, int anchorsCount,
			int imagesCount, int videosCount, int audiosCount) {

		this.creationDate = creationDate;
		this.id = id;
		this.links = links;

		this.anchorList = anchorList;
		this.linkList = linkList;

		this.anchorsCount = anchorsCount;
		this.imagesCount = imagesCount;
		this.videosCount = videosCount;
		this.audiosCount = audiosCount;

	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	@Override
	public String getCsvString(LocalDate date) {
		final StringBuilder builder = new StringBuilder();

		if (creationDate.isEqual(date)) {

			builder.append("Article ID,LINKS");

			for (int i = 1; i <= anchorsCount; i++) {
				builder.append(",ANCHOR ").append(i);
			}
			for (int i = 1; i <= anchorsCount; i++) {
				builder.append(",LINK ").append(i);
			}
			builder.append(",IMAGES,VIDEOS,AUDIOS");

			builder.append(System.lineSeparator());

			builder.append(id).append(",");
			builder.append(links).append(",");
			for (int i = 0; i < anchorsCount; i++) {
				if (i < anchorList.size()) {
					builder.append(anchorList.get(i));
				}

				builder.append(",");
			}
			for (int i = 0; i < anchorsCount; i++) {
				if (i < linkList.size()) {
					builder.append(linkList.get(i));
				}

				builder.append(",");
			}
			builder.append(imagesCount).append(",");
			builder.append(videosCount).append(",");
			builder.append(audiosCount);

			builder.append(System.lineSeparator());
		}

		return builder.toString();
	}
}
