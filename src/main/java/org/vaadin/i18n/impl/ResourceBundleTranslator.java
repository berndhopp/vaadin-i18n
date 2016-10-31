package org.vaadin.i18n.impl;

import com.vaadin.server.VaadinSession;

import org.vaadin.i18n.api.Translator;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleTranslator implements Translator {

    private static final Locale DEFAULT_FALLBACK_LOCALE = Locale.ENGLISH;
    private final String resourceBundleName;
    private final Locale defaultLocale;
    private final ClassLoader classLoader;

    public ResourceBundleTranslator(String resourceBundleName) {
        this(resourceBundleName, DEFAULT_FALLBACK_LOCALE, ClassLoader.getSystemClassLoader());
    }

    public ResourceBundleTranslator(String resourceBundleName, Locale defaultLocale) {
        this(resourceBundleName, defaultLocale, ClassLoader.getSystemClassLoader());
    }

    public ResourceBundleTranslator(String resourceBundleName, ClassLoader classLoader) {
        this(resourceBundleName, DEFAULT_FALLBACK_LOCALE, classLoader);
    }

    public ResourceBundleTranslator(String resourceBundleName, Locale defaultLocale, ClassLoader classLoader) {
        if (resourceBundleName == null)
            throw new IllegalArgumentException("resourceBundleName cannot be null");

        if (resourceBundleName.isEmpty())
            throw new IllegalArgumentException("resourceBundleName cannot be empty");

        if (defaultLocale == null)
            throw new IllegalArgumentException("defaultLocale cannot be null");

        if (classLoader == null)
            throw new IllegalArgumentException("classLoader cannot be null");

        this.defaultLocale = defaultLocale;
        this.classLoader = classLoader;
        this.resourceBundleName = resourceBundleName;
    }

    @Override
    public String translate(String template) {

        if (template == null) throw new IllegalArgumentException("template cannot be null");
        if (template.isEmpty()) throw new IllegalArgumentException("template cannot be empty");

        Locale locale = VaadinSession.getCurrent().getLocale();

        if (locale == null)
            locale = defaultLocale;

        final ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName, locale, classLoader);

        return bundle.getString(template);
    }
}
