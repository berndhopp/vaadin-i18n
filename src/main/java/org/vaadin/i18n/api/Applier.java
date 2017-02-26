package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

public interface Applier {
    /**
     * calling this method will ask the currently used {@link Translator} for translations of every
     * Component in the current {@link com.vaadin.ui.UI}'s scope and feed these translations to the
     * {@link com.vaadin.ui.Component#setCaption(String)} methods of the annotated objects.
     */
    void applyAll();

    /**
     * calling this method will ask the currently used {@link Translator} for translations of
     * the given Component. The component has to be registered beforehand.
     *
     * @param component the component to be translated
     * @throws IllegalArgumentException if the component is not registered
     */
    void apply(Component... component);
}
