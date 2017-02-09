package org.vaadin.i18n.api;

import com.vaadin.data.Property;
import com.vaadin.ui.Component;

/**
 * A Binder binds translations to {@link Component}s
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
public interface Binder {

    Bind bind(Component... components);

    Unbind unbind(Component... components);

    interface Bind extends Terminate{
        Bind toCaption(String template);
        Bind toDescription(String template);
        Bind toValue(Property<?> property);
        Bind toInputPrompt(String template);
    }

    interface Unbind extends Terminate{
        Unbind fromCaption();
        Unbind fromDescription();
        Unbind fromValue();
        Unbind fromInputPrompt();
        Terminate fromAll();
    }

    interface Terminate {
        Binder and();
    }
}
