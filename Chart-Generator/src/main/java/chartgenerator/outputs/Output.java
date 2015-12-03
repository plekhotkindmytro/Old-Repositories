package chartgenerator.outputs;

import org.joda.time.LocalDate;

public interface Output {

	String getCsvString(LocalDate date);
}
