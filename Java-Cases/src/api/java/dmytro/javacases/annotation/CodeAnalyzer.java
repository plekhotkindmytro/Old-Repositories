package dmytro.javacases.annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("dmytro.javacases.annotation.Bottleneck")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class CodeAnalyzer extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		for (Element elem : roundEnv.getElementsAnnotatedWith(Bottleneck.class)) {
			Bottleneck complexity = elem.getAnnotation(Bottleneck.class);
			String message = "annotation found in " + elem.getSimpleName()
					+ " with bottleneck " + complexity.value();
			processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
					message);
		}
		return true;
	}

}
