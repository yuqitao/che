/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.ext.git.client.remote.add;

import org.eclipse.che.ide.ext.git.client.GitLocalizationConstant;
import org.eclipse.che.ide.ext.git.client.GitResources;
import org.eclipse.che.ide.ui.window.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.validation.constraints.NotNull;

/**
 * The implementation of {@link AddRemoteRepositoryView}.
 *
 * @author Andrey Plotnikov
 */
@Singleton
public class AddRemoteRepositoryViewImpl extends Window implements AddRemoteRepositoryView {
    interface AddRemoteRepositoryViewImplUiBinder extends UiBinder<Widget, AddRemoteRepositoryViewImpl> {
    }

    private static AddRemoteRepositoryViewImplUiBinder ourUiBinder = GWT.create(AddRemoteRepositoryViewImplUiBinder.class);

    @UiField
    TextBox name;
    @UiField
    TextBox url;
    Button btnOk;
    Button btnCancel;
    @UiField(provided = true)
    final   GitResources            res;
    @UiField(provided = true)
    final   GitLocalizationConstant locale;
    private ActionDelegate          delegate;

    /**
     * Create view.
     *
     * @param resources
     * @param locale
     */
    @Inject
    protected AddRemoteRepositoryViewImpl(GitResources resources, GitLocalizationConstant locale) {
        this.res = resources;
        this.locale = locale;
        this.ensureDebugId("git-addRemoteRepository-window");

        Widget widget = ourUiBinder.createAndBindUi(this);

        this.setTitle("Add remote repository");
        this.setWidget(widget);

        btnCancel = createButton(locale.buttonCancel(), "git-addRemoteRepository-btnCancel", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelClicked();
            }
        });
        addButtonToFooter(btnCancel);

        btnOk = createButton(locale.buttonOk(), "git-addRemoteRepository-btnOk", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkClicked();
            }
        });
        addButtonToFooter(btnOk);
    }

    @Override
    protected void onEnterClicked() {
        if (isWidgetFocused(btnOk)) {
            delegate.onOkClicked();
            return;
        }

        if (isWidgetFocused(btnCancel)) {
            delegate.onCancelClicked();
        }
    }

    /** {@inheritDoc} */
    @NotNull
    @Override
    public String getName() {
        return name.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@NotNull String name) {
        this.name.setText(name);
    }

    /** {@inheritDoc} */
    @NotNull
    @Override
    public String getUrl() {
        return url.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setUrl(@NotNull String url) {
        this.url.setText(url);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableOkButton(boolean enable) {
        btnOk.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        this.hide();
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        this.show(name);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @UiHandler({"name", "url"})
    public void onValueChanged(KeyUpEvent event) {
        delegate.onValueChanged();
    }

}
