package org.vaadin.i18n.api;

import com.vaadin.data.Property;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;

/**
 * A TranslationBinder binds translations to {@link com.vaadin.ui.Component}s
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
@SuppressWarnings("unused")
public interface TranslationBinder {

    /**
     * calling this method will ask the currently used {@link Translator} for translations of every
     * Component in the current {@link com.vaadin.ui.UI}'s scope and feed these translations to the
     * {@link com.vaadin.ui.Component#setCaption(String)} methods of the annotated objects.
     */
    void applyAll();

    /**
     * calling this method will ask the currently used {@link Translator} for translations of
     * the given Component. The component has to be registered beforehand.
     * @param component the component to be translated
     * @throws IllegalArgumentException if the component is not registered
     */
    void apply(Component component);

    /**
     * register a component in this TranslationBinder
     *
     * @param component the {@link Component} to be registered
     * @param template  the template
     */
    Component bindCaption(Component component, String template);

    Component bindDescription(Component component, String template);

    void bindValue(Property<?> property, String value);

    void bindInputPrompt(AbstractTextField component, String template);

    void unbind(Component component);

    void unbindCaption(Component component);

    void unbindDescription(Component component);

    void unbindValue(Property<?> property);

    void unbindInputPrompt(AbstractTextField abstractTextField);
}
