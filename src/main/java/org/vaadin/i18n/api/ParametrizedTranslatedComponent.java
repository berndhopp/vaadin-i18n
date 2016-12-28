package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

/**
 * A component that is providing paramters for it's {@link Translator#translate(String, Object[])}
 */
@SuppressWarnings("unused")
public interface ParametrizedTranslatedComponent extends Component {
    Object[] parameters();
}
