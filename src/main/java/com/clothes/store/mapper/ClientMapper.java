package com.clothes.store.mapper;

import com.clothes.store.controller.request.ClientInsert;
import com.clothes.store.controller.request.ClientUpdate;
import com.clothes.store.controller.response.ClientResponse;
import com.clothes.store.domain.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ClientMapper {
    ClientMapper instance = Mappers.getMapper(ClientMapper.class);
    ClientResponse mapToClientResponse(Client client);
    Client matToClient(ClientInsert clientDto);
    Client matToClient(ClientUpdate clientDto);
    List<ClientResponse> matToListClientResponse(Collection<Client> clients);
}
