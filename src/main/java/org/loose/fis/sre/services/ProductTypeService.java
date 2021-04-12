package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.ProductType;
import org.loose.fis.sre.model.User;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class ProductTypeService {

    private static ObjectRepository<ProductType> productTypeRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("product-type.db").toFile())
                .openOrCreate("test", "test");

        productTypeRepository = database.getRepository(ProductType.class);
    }

    
}
