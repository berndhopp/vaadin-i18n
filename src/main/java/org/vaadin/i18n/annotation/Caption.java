package org.vaadin.i18n.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vaadin.i18n.api.TranslationBinder;
import org.vaadin.i18n.api.Translator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * when applied to a {@link com.vaadin.ui.Component},
 * {@link com.vaadin.ui.Component#setCaption(String)} will be called with
 * what the {@link Translator} resolves the value of this annotation, every time
 * {@link TranslationBinder#bind()} is called. In order to use this annotation properly,
 * a {@link TranslationModule} needs to be set up in the guice context.
 */
public @interface Caption {

    /**
     * this should be the name of the template or message that the {@link Translator} being used
     * understands. It may or may not contain parameters, that the Translator has to resolve, like
     * in
     * <pre>
     *  <code>
     * {@literal @}Caption("{{user.firstname}} {{user.lastname}}" public class FirstNameLastNameLabel extends
     * Label {
     * }
     *  </code>
     * </pre>
     */
    String value();
}

