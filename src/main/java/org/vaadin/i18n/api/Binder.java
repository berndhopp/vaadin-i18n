package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

/**
 * A Binder binds translations to {@link Component}s
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
@SuppressWarnings("unused")
public interface Binder {

    Bind bind(Component... components);

    Unbind unbind(Component... components);

    interface Bind extends Terminate{
        Bind toCaption(String template);
        Bind toDescription(String template);
    }

    interface Unbind extends Terminate{
        Unbind fromCaption();
        Unbind fromDescription();
        Terminate fromAll();
    }

    interface Terminate {
        Binder and();
    }
}
