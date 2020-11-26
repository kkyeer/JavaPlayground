package lab.annotations.processor.sourcecode;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 16:56 2019/4/14
 * @Modified By:
 */
@SupportedAnnotationTypes({"taste.annotations.processor.sourcecode.*"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ToStringProcessor extends AbstractProcessor {
    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ToString.class);
        for (Element element : elements) {
            System.out.println(element.getClass());
        }
        return true;
    }
}
