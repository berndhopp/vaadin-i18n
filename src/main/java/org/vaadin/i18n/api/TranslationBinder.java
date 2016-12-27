package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

/**
 * A TranslationBinder binds translations to {@link com.vaadin.ui.Component}s
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
public interface TranslationBinder {

    /**
     * calling this method will ask the currently used {@link Translator} for translations of every
     * Component in the current {@link com.vaadin.ui.UI}'s scope and feed these translations to the
     * {@link com.vaadin.ui.Component#setCaption(String)} methods of the annotated objects.
     */
    void bindAll();

    /**
     * calling this method will ask the currently used {@link Translator} for translations of
     * the given Component. The component has to be registered beforehand.
     * @param component the component to be translated
     */
    void bind(Component component);

    /**
     * register a component in this TranslationBinder
     *
     * @param component the {@link Component} to be registered
     * @param template  the template
     */
    void register(Component component, String template);
}
