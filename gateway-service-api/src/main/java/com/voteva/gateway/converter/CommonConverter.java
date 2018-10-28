package com.voteva.gateway.converter;

import com.voteva.common.grpc.model.GPage;
import com.voteva.common.grpc.model.GPageable;
import com.voteva.common.grpc.model.GUuid;

import java.util.UUID;

public class CommonConverter {
/*
    public static GPage convert(Page page) {
        return GPage.newBuilder()
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .build();
    }

    public static Pageable convert(GPageable pageable) {
        return PageRequest.of(pageable.getPage(), pageable.getSize());
    }*/

    public static GUuid convert(UUID uuid) {
        return GUuid.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();
    }

    public static UUID convert(GUuid guuid) {
        return UUID.fromString(guuid.getUuid());
    }
}
