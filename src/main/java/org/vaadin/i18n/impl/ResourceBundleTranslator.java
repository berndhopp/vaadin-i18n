package org.vaadin.i18n.impl;

import com.vaadin.server.VaadinSession;

import org.vaadin.i18n.api.Translator;

import java.util.Locale;

import static java.util.ResourceBundle.getBundle;

public class ResourceBundleTranslator implements Translator {

    private final String resourceBundleName;
    private final ClassLoader classLoader;

    public ResourceBundleTranslator(String resourceBundleName) {
        this(resourceBundleName, ClassLoader.getSystemClassLoader());
    }

    public ResourceBundleTranslator(String resourceBundleName, ClassLoader classLoader) {
        if (resourceBundleName == null)
            throw new IllegalArgumentException("resourceBundleName cannot be null");

        if (resourceBundleName.isEmpty())
            throw new IllegalArgumentException("resourceBundleName cannot be empty");

        if (classLoader == null)
            throw new IllegalArgumentException("classLoader cannot be null");

        this.classLoader = classLoader;
        this.resourceBundleName = resourceBundleName;
    }

    @Override
    public String translate(String template) {

        if (template == null) throw new IllegalArgumentException("template cannot be null");
        if (template.isEmpty()) throw new IllegalArgumentException("template cannot be empty");

        Locale locale = VaadinSession.getCurrent().getLocale();

        if(locale == null) {
            locale = Locale.getDefault();

            if(locale == null){
                throw new IllegalStateException("no locale in current VaadinSession and no default Locale set");
            }
        }

        return getBundle(resourceBundleName, locale, classLoader).getString(template);
    }
}
