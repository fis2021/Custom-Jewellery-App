package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.MaterialAlreadyExistsException;
import org.loose.fis.sre.model.Material;
import org.loose.fis.sre.model.ProductType;

import java.util.ArrayList;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class MaterialService {

    private static ObjectRepository<Material> materialRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("materials.db").toFile())
                .openOrCreate("test", "test");

        materialRepository = database.getRepository(Material.class);
    }

    public static void addMaterial(String name, int price) throws MaterialAlreadyExistsException {
        checkMaterialDoesNotAlreadyExist(name);
        materialRepository.insert(new Material(name, price));
    }

    public static void removeMaterial(Material material){
        materialRepository.remove(eq("name", material.getName()));
    }

    public static void checkMaterialDoesNotAlreadyExist(String name) throws MaterialAlreadyExistsException {
        for (Material material : materialRepository.find()) {
            if (Objects.equals(name, material.getName()))
                throw new MaterialAlreadyExistsException(name);
        }
    }

    public static ArrayList<Material> materials() {
        ArrayList<Material> list = new ArrayList<>();
        for(Material material : materialRepository.find()) {
            list.add(material);
        }
        return list;
    }

    public static void modifyPrice(String name, int newPrice) {
        for(Material material : materialRepository.find()){
            if(Objects.equals(name, material.getName())) {
                materialRepository.update(eq("name", name), new Material(name, newPrice));
            }
        }
    }
}
