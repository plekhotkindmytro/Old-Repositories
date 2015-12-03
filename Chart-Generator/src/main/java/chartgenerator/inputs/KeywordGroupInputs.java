/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chartgenerator.inputs;

import java.util.Collections;
import java.util.List;

import chartgenerator.model.KeywordData;

/**
 *
 * @author dmytroplekhotkin
 */
public final class KeywordGroupInputs {
	public final int frequency;
	public final String name;
	public final List<KeywordData> anchorData;
	public final List<KeywordData> linkData;

	public KeywordGroupInputs(final String name, final int frequency,
			final List<KeywordData> anchorData, final List<KeywordData> linkData) {
		this.name = name;
		this.frequency = frequency;
		this.anchorData = Collections.unmodifiableList(anchorData);
		this.linkData = Collections.unmodifiableList(linkData);
	}

}
