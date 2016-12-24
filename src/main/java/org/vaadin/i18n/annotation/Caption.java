package org.vaadin.i18n.annotation;

import com.vaadin.ui.Component;

import org.vaadin.i18n.api.Translator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
/**
 * This annotation binds vaadin-i18n to Dependency-Injection frameworks like Spring, Guice or Java CDI.
 * It may be attached to a {@link com.vaadin.ui.Component} that needs its {@link Component#getCaption()}
 * translated for different {@link java.util.Locale}s. Translations are is evaluated by the the configured
 * {@link org.vaadin.i18n.api.Translator#translate(String)} which gets passed the {@link Caption#value()}.
 *
 * See the implementation ( Spring, Guice, CDI ) for details how to set up {@link org.vaadin.i18n.api.Translator}
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
public @interface Caption {

    /**
     * this should be the name of the template or message that the {@link Translator} being used
     * understands. It may or may not contain parameters, that the Translator has to resolve.
     * <pre>
     *  <code>
     * {@literal @}Caption("{{user.firstname}} {{user.lastname}}")
     * public class FirstNameLastNameLabel extends
     * Label {
     * }
     *  </code>
     *
     *  <code>
     * {@literal @}Caption("user_fullname")
     * public class FirstNameLastNameLabel extends
     * Label {
     * }
     *  </code>
     * </pre>
     */
    String value();
}

