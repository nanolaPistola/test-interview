package com.multicert.test.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.multicert.test.common.Common;
import com.multicert.test.domain.Config;
import com.multicert.test.repository.ConfigRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.core.Response;
import java.util.*;

@AllArgsConstructor
public class ClientResourceImplementation implements ClientResource{

    public List<GeneralActionsInterface> generalActionsInterfaceList;

    public ConfigRepository configRepository;

    @Override
    public Response getClientList(String country, Integer page, Integer size) {
        if(Boolean.FALSE.equals(countryAvailable(country))) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();
        }
            for (GeneralActionsInterface generalActionsInterface : generalActionsInterfaceList) {
                if(generalActionsInterface.getCountry().equals(country)) {
                    return generalActionsInterface.getClientList(page, size);
                }
            }
        return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();

    }

    @Override
    public Response deleteClient(String country, Long id) {
        if(Boolean.FALSE.equals(countryAvailable(country))) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();
        }
        for (GeneralActionsInterface generalActionsInterface : generalActionsInterfaceList) {

            if(generalActionsInterface.getCountry().equals(country)) {
                return generalActionsInterface.deleteClient(id);
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();    }

    @Override
    public Response getClientByNif(String country, String nif) {
        if(Boolean.FALSE.equals(countryAvailable(country))) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();
        }
        for (GeneralActionsInterface generalActionsInterface : generalActionsInterfaceList) {
            if(generalActionsInterface.getCountry().equals(country)) {
                return generalActionsInterface.getClientByNif(nif);
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();    }

    @Override
    public Response getClientByName(String country, String name) {
        if(Boolean.FALSE.equals(countryAvailable(country))) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();
        }
        for (GeneralActionsInterface generalActionsInterface : generalActionsInterfaceList) {
            if(generalActionsInterface.getCountry().equals(country)) {
                return generalActionsInterface.getClientByName(name);
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();    }

    @Override
    public Response createClient(String country, Map<String, Object> client) {
        if(Boolean.FALSE.equals(countryAvailable(country))) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();
        }
        if(actionAvailable(country, "CREATE")) {

            for (GeneralActionsInterface generalActionsInterface : generalActionsInterfaceList) {
                if (generalActionsInterface.getCountry().equals(country)) {
                    return generalActionsInterface.createClient(client);
                }
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Action: CREATE not available" )).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();        }

    @Override
    public Response configCountry(String country, Map<String, Object> body) {
        Optional<Config> optionalConfig = configRepository.findByCountry(country);
        if(optionalConfig.isPresent()) {
            Common.resolve(() -> body.get("active")).filter(Objects::nonNull).ifPresent(value -> optionalConfig.get().setActive((Boolean) value));
            Common.resolve(() -> body.get("actions")).filter(Objects::nonNull).ifPresent(value -> optionalConfig.get().setActions(Common.objectToJson(value)));
            configRepository.save(optionalConfig.get());
             return Response.status(Response.Status.OK).entity(body).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "Country ".concat(country).concat(" Not Enabled"))).build();
    }

    private boolean actionAvailable(String country, String action) {
        Optional<Config> optionalConfig = configRepository.findByCountry(country);
        if(optionalConfig.isPresent()) {
            String actions = optionalConfig.get().getActions();
            Map<String, Object> actionsMap =  Common.writeValueAsMap(actions);
            return actionsMap != null && actionsMap.containsKey(action) && Boolean.TRUE.equals(actionsMap.get(action));
        }
        return false;
    }

    private boolean countryAvailable(String country) {
        Optional<Config> optionalConfig = configRepository.findByCountry(country);
        if(optionalConfig.isPresent()) {
            return optionalConfig.get().getActive();
        }
        return false;
    }
}
