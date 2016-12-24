package org.vaadin.i18n.api;

import com.vaadin.ui.Component;

import java.util.Collection;

/**
 * A component that is providing paramters for it's {@link Translator#translate(String, Collection<Object>)}
 */
public interface ParametrizedTranslatedComponent extends Component{
    Collection<Object> parameters();
}
