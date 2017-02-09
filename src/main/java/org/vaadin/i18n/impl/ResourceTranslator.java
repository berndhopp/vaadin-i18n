package org.vaadin.i18n.impl;

import com.vaadin.server.VaadinSession;
import org.vaadin.i18n.api.Translator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * a 'default'- or reference-implementation of a translator that
 * uses resource-bundles
 */
@SuppressWarnings("unused")
public class ResourceTranslator implements Translator {

    private final String resourceName;
    private ResourceBundle resourceBundle;
    private Locale oldLocale;

    /**
     * @param resourceName the name of the {@link ResourceBundle} to use
     */
    public ResourceTranslator(String resourceName) {

        if (resourceName == null || resourceName.isEmpty()) {
            throw new IllegalArgumentException("resourceName cannot be null or empty");
        }

        this.resourceName = resourceName;
    }

    @Override
    public String translate(String template) {
        if (template == null) throw new IllegalArgumentException("template cannot be null");

        final Locale currentLocale = VaadinSession.getCurrent().getLocale();

        if (resourceBundle == null || !oldLocale.equals(currentLocale)) {
            resourceBundle = ResourceBundle.getBundle(resourceName, currentLocale);
            oldLocale = currentLocale;
        }

        return resourceBundle.getString(template);
    }
}
