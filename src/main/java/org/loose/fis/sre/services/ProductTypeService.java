package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.TypeAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.ProductType;
import org.loose.fis.sre.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class ProductTypeService {

    private static ObjectRepository<ProductType> productTypeRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("product-type.db").toFile())
                .openOrCreate("test", "test");

        productTypeRepository = database.getRepository(ProductType.class);
    }

    public static void addType(String type, int price) throws TypeAlreadyExistsException {
        checkTypeDoesNotAlreadyExist(type);
        productTypeRepository.insert(new ProductType(type, price));
    }

    public static void checkTypeDoesNotAlreadyExist(String type) throws TypeAlreadyExistsException {
        for (ProductType productType : productTypeRepository.find()) {
            if (Objects.equals(type, productType.getType()))
                throw new TypeAlreadyExistsException(type);
        }
    }

    public static ArrayList<ProductType> productTypes() {
        ArrayList<ProductType> list = new ArrayList<>();
        for(ProductType productType : productTypeRepository.find()) {
            list.add(productType);
        }
        return list;
    }
}
