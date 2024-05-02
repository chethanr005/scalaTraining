package com.chethan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by $CapName on Nov 09, 2023.
 */

public class Extraction {


    static String single   = "{\"id\":2,\"isRemoved\":false,\"name\":\"Nandi Mines\"}";
    static String response = "[{\"id\":2,\"isRemoved\":false,\"name\":\"Nandi Mines\"},{\"id\":5,\"isRemoved\":false,\"name\":\"Ubbalagundi Mines\"},{\"id\":36,\"isRemoved\":false,\"name\":\"REACTORE VTC\"},{\"id\":8,\"isRemoved\":false," +
            "\"name\":\"Site-8\"},{\"id\":1,\"isRemoved\":false,\"name\":\"Reactore Mine1\"},{\"id\":4,\"isRemoved\":false,\"name\":\"Reactore Mine3\"},{\"id\":100,\"isRemoved\":false,\"name\":\"Test Mine\"},{\"id\":6,\"isRemoved\":false,\"name\":\"Reactore Mine4\"},{\"id\":3,\"isRemoved\":false,\"name\":\"Reactore Mine2\"}]";

    public static void main(String[] args) throws JsonProcessingException {


        ObjectMapper objectMapper = new ObjectMapper();
        List<NewPreciseEntity> newPreciseEntities = objectMapper.readValue(response, new TypeReference<List<NewPreciseEntity>>() {
        });


        System.out.println(newPreciseEntities.size());

    }
}
