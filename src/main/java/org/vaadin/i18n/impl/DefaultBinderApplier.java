package org.vaadin.i18n.impl;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import org.vaadin.i18n.api.Applier;
import org.vaadin.i18n.api.Binder;
import org.vaadin.i18n.api.TranslationException;
import org.vaadin.i18n.api.Translator;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class DefaultBinderApplier implements Applier, Binder {

    private static class TranslationPack{
        String captionTemplate;
        String descriptionTemplate;
    }

    private final Translator translator;
    private final Map<Component, TranslationPack> componentsToTemplates = new HashMap<Component, TranslationPack>();

    public DefaultBinderApplier(Translator translator){
        if(translator == null){
            throw new NullPointerException("translator cannot be null");
        }

        this.translator = translator;
    }

    @Override
    public void applyAll() throws TranslationException{
        for (Map.Entry<Component, TranslationPack> entry : componentsToTemplates.entrySet()) {
            final Component component = entry.getKey();
            final TranslationPack translationPack = entry.getValue();
            translateInternal(component, translationPack);
        }
    }

    private void translateInternal(Component component, TranslationPack translationPack) {
        if(translationPack.captionTemplate != null){
            final String caption = translator.translate(translationPack.captionTemplate);
            component.setCaption(caption);
        }

        if(translationPack.descriptionTemplate != null){
            final String description = translator.translate(translationPack.descriptionTemplate);
            ((AbstractComponent)component).setDescription(description);
        }
    }

    @Override
    public void apply(Component... components) {

        if(components == null || components.length == 0){
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

        if(components == null || components.length == 0){
            throw new IllegalArgumentException("components cannot be null or empty");
        }

        for (Component component : components) {
            componentsToTemplates.remove(component);
        }

        return new DefaultTerminate();
    }

    private class DefaultTerminate implements Terminate{
        @Override
        public Binder then() {
            return DefaultBinderApplier.this;
        }
    }

    private class DefaultBind implements Bind{

        private final TranslationPack translationPack;

        private DefaultBind(Component component) {
            if(component == null ){
                throw new IllegalArgumentException("component cannot be null");
            }

            TranslationPack translationPack = componentsToTemplates.get(component);

            if(translationPack == null){
                translationPack = new TranslationPack();
                componentsToTemplates.put(component, translationPack);
            }

            this.translationPack = translationPack;
        }

        @Override
        public Bind toCaption(String template) {

            if(template == null || template.isEmpty()){
                throw new IllegalArgumentException("template cannot be null or empty");
            }

            translationPack.captionTemplate = template;

            return this;
        }

        @Override
        public Bind toDescription(String template) {

            if(template == null || template.isEmpty()){
                throw new IllegalArgumentException("template cannot be null or empty");
            }

            translationPack.descriptionTemplate = template;

            return this;
        }

        @Override
        public Binder then() {
            return DefaultBinderApplier.this;
        }
    }
}
