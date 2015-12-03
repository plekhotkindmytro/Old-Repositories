package chartgenerator.inputs;

import java.util.List;

public class GlobalSettings {

	public final String projectName;

	public final List<Double> articleFrequencies;
	public final List<Double> imageFrequencies;
	public final List<Double> audioFrequencies;
	public final List<Double> videoFrequencies;

	public final List<Integer> anchorFrequencies;

	public GlobalSettings(String projectName, List<Double> articleFrequencies,
			List<Double> imageFrequencies, List<Double> audioFrequencies,
			List<Double> videoFrequencies, List<Integer> anchorFrequencies) {

		this.projectName = projectName;

		this.articleFrequencies = articleFrequencies;
		this.imageFrequencies = imageFrequencies;
		this.audioFrequencies = audioFrequencies;
		this.videoFrequencies = videoFrequencies;
		this.anchorFrequencies = anchorFrequencies;

	}

}
