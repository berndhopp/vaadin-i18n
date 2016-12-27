package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

import org.vaadin.i18n.annotation.Caption;

/**
 * Translator is a provider of translations for any input String, which may or may not be dependent
 * on the current context. The Translator itself is responsible of choosing the correct current
 * {@link java.util.Locale} or whatever it uses and maybe resolve contextual information.
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
public interface Translator {
    /**
     * translate the 'template' to the correct translation.
     *
     * @param template the 'template' is supplied by {@link Caption#value()} or {@link TranslationBinder#register(Component, String)},
     *                 and is not restricted in any way. It may or may not contain parameters, that the Translator has to
     *                 resolve
     * @param parameters the parameters for this template
     * @return the translation
     */
    String translate(String template, Object[] parameters);
}
