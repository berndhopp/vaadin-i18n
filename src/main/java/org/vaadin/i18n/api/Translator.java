package org.vaadin.i18n.api;

/**
 * Translator is a provider of translations for any input String, which may or may not be dependent
 * on the current context. The Translator itself is responsible of choosing the correct current
 * {@link java.util.Locale} from the right source and maybe resolve contextual information.
 */
public interface Translator {
    /**
     * translate the 'template' to the correct translation.
     *
     * @param template the 'template' comes from {@link Caption#value()}, and is not restricted in
     *                 any way. It may or may not contain parameters, that the Translator has to
     *                 resolve
     * @return the translation
     */
    String translate(String template);
}
