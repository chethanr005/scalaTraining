package com.chethan;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by $CapName on Nov 09, 2023.
 */

public class NewPreciseEntity {
    public final int id;

    public final String name;
    public final boolean isRemoved;

    public NewPreciseEntity(    @JsonProperty("id") int id,
                                @JsonProperty("name") String name,
                                @JsonProperty("isRemoved") boolean isRemoved) {
        this.id = id;
        this.name = name;
        this.isRemoved = isRemoved;
    }
}
