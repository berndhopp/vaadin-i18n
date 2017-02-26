package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

/**
 * A Binder binds translations to {@link Component}s
 *
 * @author Bernd Hopp bernd@vaadin.com
 */
@SuppressWarnings("unused")
public interface Binder {

    Bind bind(Component component);

    Terminate unbind(Component... components);

    interface Bind extends Terminate {
        Bind toCaption(String template);

        Bind toDescription(String template);
    }

    interface Terminate {
        Binder then();
    }
}
