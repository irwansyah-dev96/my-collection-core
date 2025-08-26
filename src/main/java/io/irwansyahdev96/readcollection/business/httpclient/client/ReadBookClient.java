package io.irwansyahdev96.readcollection.business.httpclient.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.irwansyahdev96.readcollection.base.constant.Server;
import io.irwansyahdev96.readcollection.base.dao.BaseClient;

@Component
public class ReadBookClient extends BaseClient{
    
    @Value("${app.host.read}")
    private String serverRead;

    public Boolean isExistReadbook(String issbn){
        StringBuilder sb = new StringBuilder();
        sb.append(serverRead)
        .append(Server.PATH_READ_BOOK)
        .append(String.format("/%s/readbook", issbn));

        Map<String,Object> map = get(sb.toString());

        if(map.isEmpty())
            return null;

        @SuppressWarnings("unchecked")
        Map<String,Object> statusMap = (Map<String,Object>) map.get("data");

        Boolean isReadbook = (Boolean)statusMap.get("isReadbook");

        if(isReadbook == null)
            return null;

        return isReadbook;
    }
}
