package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

import org.vaadin.i18n.annotation.Caption;

/**
 * A TranslationBinder should be injected on every part of your application where you want your
 * translations to be bound to the {@link com.vaadin.ui.Component}s annotated with {@link Caption}
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
public interface TranslationBinder {

    /**
     * calling this method will ask the currently used {@link Translator} for translations of every
     * Component in the current {@link com.vaadin.ui.UI}'s scope and feed these translations to the
     * {@link com.vaadin.ui.Component#setCaption(String)} methods of the annotated objects.
     */
    void bind();

    /**
     * register a component in this TranslationBinder
     *
     * @param component the {@link Component} to be registered
     * @param template  the template
     */
    void register(Component component, String template);
}
