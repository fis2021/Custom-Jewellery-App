package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.Material;

import java.util.Objects;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class MaterialService {

    private static ObjectRepository<Material> materialRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("materials.db").toFile())
                .openOrCreate("test", "test");

        materialRepository = database.getRepository(Material.class);
    }

    public static void addMaterial(String name, int price) throws Exception {
        checkMaterialDoesNotAlreadyExist(name);
        materialRepository.insert(new Material(name, price));
    }

    private static void checkMaterialDoesNotAlreadyExist(String name) throws Exception {
        for (Material material : materialRepository.find()) {
            if (Objects.equals(name, material.getName()))
                throw new Exception(name);
        }
    }
}
