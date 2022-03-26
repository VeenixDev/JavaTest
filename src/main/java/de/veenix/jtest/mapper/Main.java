package de.veenix.jtest.mapper;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        MyEntity entity = new MyEntity(UUID.randomUUID(), "Hans", "Flammenwerfer");
        MyDTO dto = Mapper.mapToDTO(MyDTO.class, entity);

        System.out.println(entity);
        System.out.println(dto);
    }

}
