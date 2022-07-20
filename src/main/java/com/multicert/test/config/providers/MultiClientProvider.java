package com.multicert.test.config.providers;

import com.multicert.test.repository.ConfigRepository;
import com.multicert.test.resource.ClientResourceImplementation;
import com.multicert.test.resource.GeneralActionsInterface;
import com.multicert.test.resource.es.EsClientImplementation;
import com.multicert.test.resource.es.USAClientImplementation;
import com.multicert.test.resource.pt.PtClientImplementation;
import com.multicert.test.service.ClientService;
import com.multicert.test.service.mapper.EsClientMapper;
import com.multicert.test.service.mapper.PtClientMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MultiClientProvider {

    @Value("${provider.pt:false}")
    public Boolean ptProvider;
    @Value("${provider.es:false}")
    public Boolean esProvider;
    @Value("${provider.usa:false}")
    public Boolean usaProvider;
    private final PtClientMapper ptClientMapper;

    private final EsClientMapper esClientMapper;

    private final ClientService clientService;

    public MultiClientProvider(PtClientMapper ptClientMapper, EsClientMapper esClientMapper, ClientService clientService) {
        this.ptClientMapper = ptClientMapper;
        this.esClientMapper = esClientMapper;
        this.clientService = clientService;

    }

    @Bean
    public ClientResourceImplementation createGenericClient(ConfigRepository configRepository) {
        List<GeneralActionsInterface> generalActionsInterfaceList = new ArrayList<>();
        if(Boolean.TRUE.equals(ptProvider)) {
            generalActionsInterfaceList.add(new PtClientImplementation(clientService, ptClientMapper));
        }
        if(Boolean.TRUE.equals(esProvider)) {
            generalActionsInterfaceList.add(new EsClientImplementation(clientService, esClientMapper));
        }
        if(Boolean.TRUE.equals(usaProvider)) {
            generalActionsInterfaceList.add(new USAClientImplementation(clientService, esClientMapper));
        }
        return new ClientResourceImplementation(generalActionsInterfaceList, configRepository);
    }
}
