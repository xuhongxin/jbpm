/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.services.cdi.impl.query;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

import org.dashbuilder.dataprovider.DataSetProviderRegistry;
import org.dashbuilder.dataset.DataSetManager;
import org.dashbuilder.dataset.def.DataSetDefRegistry;
import org.jbpm.kie.services.impl.query.QueryServiceImpl;
import org.jbpm.shared.services.impl.TransactionalCommandService;
import org.kie.internal.identity.IdentityProvider;
import org.kie.internal.runtime.cdi.BootOnLoad;

@Named("QueryServiceCDIImpl-startable")
@BootOnLoad
@ApplicationScoped
public class QueryServiceCDIImpl extends QueryServiceImpl {
    
    @Inject
    private Instance<DataSetDefRegistry> dataSetDefRegistryInstance;
    @Inject
    private Instance<DataSetManager> dataSetManagerInstance;    
    @Inject
    private Instance<DataSetProviderRegistry> providerRegistryInstance;

    @Inject
    @Override
    public void setIdentityProvider(IdentityProvider identityProvider) {
        super.setIdentityProvider(identityProvider);
    }

    @Inject
    @Override
    public void setCommandService(TransactionalCommandService commandService) {
        super.setCommandService(commandService);
    }

    @PostConstruct
    @Override
    public void init() {
        if (!dataSetManagerInstance.isUnsatisfied()) {
            setDataSetManager(dataSetManagerInstance.get());
        }
        if (!dataSetDefRegistryInstance.isUnsatisfied()) {
            setDataSetDefRegistry(dataSetDefRegistryInstance.get());
        }
        if (!providerRegistryInstance.isUnsatisfied()) {
            setProviderRegistry(providerRegistryInstance.get());
        }
        super.init();
    }

}
