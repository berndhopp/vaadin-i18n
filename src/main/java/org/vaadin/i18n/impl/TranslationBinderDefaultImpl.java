package org.vaadin.i18n.impl;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;

import org.vaadin.i18n.api.TranslationBinder;
import org.vaadin.i18n.api.Translator;

import java.util.HashMap;
import java.util.Map;

public class TranslationBinderDefaultImpl implements TranslationBinder {

    private final Translator translator;

    public TranslationBinderDefaultImpl(Translator translator) {
        if (translator == null) throw new IllegalArgumentException("translator cannot be null");

        this.translator = translator;
    }

    public void bind() {
        TranslatedComponentSet translatedComponentSet = VaadinSession.getCurrent().getAttribute(TranslatedComponentSet.class);

        if (translatedComponentSet != null) {
            for (Map.Entry<Component, String> entry : translatedComponentSet.entrySet()) {
                final String template = entry.getValue();
                final Component component = entry.getKey();
                component.setCaption(translator.translate(template));
            }
        }
    }

    public void register(Component component, String template) {
        if (component == null) throw new IllegalArgumentException("component cannot be null");
        if (template == null || template.equals(""))
            throw new IllegalArgumentException("template cannot be null or empty");

        TranslatedComponentSet translatedComponentSet = VaadinSession.getCurrent().getAttribute(TranslatedComponentSet.class);

        if (translatedComponentSet == null) {
            translatedComponentSet = new TranslatedComponentSet();
            VaadinSession.getCurrent().setAttribute(TranslatedComponentSet.class, translatedComponentSet);
        }

        translatedComponentSet.put(component, template);
    }

    private final class TranslatedComponentSet extends HashMap<Component, String> {
    }
}
