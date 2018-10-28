package com.voteva.users.converter;

import com.voteva.common.grpc.model.GUuid;

import java.util.UUID;

public class ModelConverter {

    public static GUuid convert(UUID uuid) {
        return GUuid.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();
    }
}
