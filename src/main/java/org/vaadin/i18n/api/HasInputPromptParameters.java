package org.vaadin.i18n.api;

import com.vaadin.data.Property;

/**
 * A component that is providing parameters for it's {@link Translator#translate(String, Object[])} (String, Object[])}
 */
@SuppressWarnings("unused")
public interface HasInputPromptParameters extends Property {
    Object[] getInputPromptParameters();
}
