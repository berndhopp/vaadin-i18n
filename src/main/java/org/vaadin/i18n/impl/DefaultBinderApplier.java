package org.vaadin.i18n.impl;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import org.vaadin.i18n.api.Applier;
import org.vaadin.i18n.api.Binder;
import org.vaadin.i18n.api.TranslationException;
import org.vaadin.i18n.api.Translator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("unused")
public final class DefaultBinderApplier implements Applier, Binder {

    private static boolean inited = false;
    private final Translator translator;
    private final Map<Component, TranslationPack> componentsToTemplates = new HashMap<>();

    private DefaultBinderApplier(Translator translator) {
        if (translator == null) {
            throw new NullPointerException("translator cannot be null");
        }

        this.translator = translator;
    }

    public static void init(Supplier<Translator> translatorSupplier) {

        requireNonNull(translatorSupplier);

        if (inited) {
            throw new IllegalStateException("init() cannot be called more than once");
        }

        VaadinService.getCurrent().addSessionInitListener(
                sessionInitEvent -> {
                    final Translator translator = requireNonNull(translatorSupplier.get());
                    final DefaultBinderApplier defaultBinderApplier = new DefaultBinderApplier(translator);
                    sessionInitEvent.getSession().setAttribute(Binder.class, defaultBinderApplier);
                    sessionInitEvent.getSession().setAttribute(Applier.class, defaultBinderApplier);
                }
        );

        inited = true;
    }

    @Override
    public void applyAll() throws TranslationException {
        for (Map.Entry<Component, TranslationPack> entry : componentsToTemplates.entrySet()) {
            final Component component = entry.getKey();
            final TranslationPack translationPack = entry.getValue();
            translateInternal(component, translationPack);
        }
    }

    private void translateInternal(Component component, TranslationPack translationPack) {
        if (translationPack.captionTemplate != null) {
            applyCaption(component, translationPack);
        }

        if (translationPack.descriptionTemplate != null) {
            applyDescription((AbstractComponent) component, translationPack);
        }
    }

    private void applyDescription(AbstractComponent component, TranslationPack translationPack) {
        final String description = translator.translate(translationPack.descriptionTemplate);
        component.setDescription(description);
    }

    private void applyCaption(Component component, TranslationPack translationPack) {
        final String caption = translator.translate(translationPack.captionTemplate);
        component.setCaption(caption);
    }

    @Override
    public void apply(Component... components) {

        if (components == null || components.length == 0) {
            throw new IllegalArgumentException("components cannot be null or empty");
        }

        for (Component component : components) {
            final TranslationPack translationPack = componentsToTemplates.get(component);

            if (translationPack != null) {
                translateInternal(component, translationPack);
            }
        }
    }

    @Override
    public Bind bind(Component component) {
        return new DefaultBind(component);
    }

    @Override
    public Terminate unbind(Component... components) {

        if (components == null || components.length == 0) {
            throw new IllegalArgumentException("components cannot be null or empty");
        }

        for (Component component : components) {
            componentsToTemplates.remove(component);
        }

        return new DefaultTerminate();
    }

    private static class TranslationPack {
        String captionTemplate;
        String descriptionTemplate;
    }

    private class DefaultTerminate implements Terminate {
        @Override
        public Binder then() {
            return DefaultBinderApplier.this;
        }
    }

    private class DefaultBind implements Bind {

        private final TranslationPack translationPack;
        private final Component component;

        private DefaultBind(Component component) {

            this.component = requireNonNull(component, "component cannot be null");

            TranslationPack translationPack = componentsToTemplates.get(component);

            if (translationPack == null) {
                translationPack = new TranslationPack();
                componentsToTemplates.put(component, translationPack);
            }

            this.translationPack = translationPack;
        }

        @Override
        public Bind toCaption(String template) {

            if (template == null || template.isEmpty()) {
                throw new IllegalArgumentException("template cannot be null or empty");
            }

            translationPack.captionTemplate = template;

            applyCaption(component, translationPack);

            return this;
        }

        @Override
        public Bind toDescription(String template) {

            if (template == null || template.isEmpty()) {
                throw new IllegalArgumentException("template cannot be null or empty");
            }

            if (!(component instanceof AbstractComponent)) {
                throw new IllegalArgumentException("this method can only be called on instances of AbstractComponent");
            }

            translationPack.descriptionTemplate = template;

            applyDescription((AbstractComponent) component, translationPack);

            return this;
        }

        @Override
        public Binder then() {
            return DefaultBinderApplier.this;
        }
    }
}
