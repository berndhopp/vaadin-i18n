package org.vaadin.i18n.impl;

import com.vaadin.server.VaadinSession;
import org.vaadin.i18n.api.Translator;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * a 'default'- or reference-implementation of a translator that
 * uses resource-bundles
 */
@SuppressWarnings("unused")
public class ResourceTranslator implements Translator {

    private final String resourceName;
    private Locale oldLocale;
    private static final Map<Locale, Map<String, MessageFormat>> messageFormats = new ConcurrentHashMap<Locale, Map<String, MessageFormat>>();

    /**
     * @param resourceBundle the name of the {@link ResourceBundle} to use
     */
    public ResourceTranslator(String resourceBundle) {

        if (resourceBundle == null || resourceBundle.isEmpty()) {
            throw new IllegalArgumentException("resourceName cannot be null or empty");
        }

        this.resourceName = resourceBundle;
    }

    @Override
    public String translate(String template, Object[] parameters) {
        if (template == null) throw new IllegalArgumentException("template cannot be null");
        if (parameters == null) throw new IllegalArgumentException("parameters cannot be null");

        final Locale currentLocale = VaadinSession.getCurrent().getLocale();

        Map<String, MessageFormat> messageFormatMap;

        if (oldLocale == null || !oldLocale.equals(currentLocale)) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceName, currentLocale);

            messageFormatMap = messageFormats.get(currentLocale);

            if (messageFormatMap == null) {
                messageFormatMap = new ConcurrentHashMap<String, MessageFormat>(resourceBundle.keySet().size());
                for (String key : resourceBundle.keySet()) {
                    messageFormatMap.put(key, new MessageFormat(resourceBundle.getString(key), currentLocale));
                }

                messageFormats.put(currentLocale, messageFormatMap);
            }

            oldLocale = currentLocale;
        } else {
            messageFormatMap = messageFormats.get(currentLocale);
        }

        MessageFormat messageFormat = messageFormatMap.get(template);

        if(messageFormat == null){
            throw new IllegalArgumentException("template " + template + " not defined");
        }

        return messageFormat.format(parameters);
    }
}
