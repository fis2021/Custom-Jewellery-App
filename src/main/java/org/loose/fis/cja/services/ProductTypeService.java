package org.loose.fis.cja.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.cja.exceptions.TypeAlreadyExistsException;
import org.loose.fis.cja.model.ProductType;

import java.util.ArrayList;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import static org.loose.fis.cja.services.FileSystemService.getPathToFile;

public class ProductTypeService {

    private static ObjectRepository<ProductType> productTypeRepository;
    private static Nitrite database;

    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(getPathToFile("product-type.db").toFile())
                .openOrCreate("test", "test");

        productTypeRepository = database.getRepository(ProductType.class);
    }

    public static void addType(String type, int price) throws TypeAlreadyExistsException {
        checkTypeDoesNotAlreadyExist(type);
        productTypeRepository.insert(new ProductType(type, price));
    }

    public static void removeType(ProductType productType) {
        productTypeRepository.remove(eq("type",productType.getType()));
    }

    public static void checkTypeDoesNotAlreadyExist(String type) throws TypeAlreadyExistsException {
        for (ProductType productType : productTypeRepository.find()) {
            if (Objects.equals(type, productType.getType()))
                throw new TypeAlreadyExistsException(type);
        }
    }

    public static ArrayList<ProductType> getAllProductTypes() {
        ArrayList<ProductType> list = new ArrayList<>();
        for(ProductType productType : productTypeRepository.find()) {
            list.add(productType);
        }
        return list;
    }

    public static void modifyPrice(String type, int newPrice) {
        for(ProductType productType : productTypeRepository.find()){
            if(Objects.equals(type, productType.getType())) {
                productTypeRepository.update(eq("type", type), new ProductType(type, newPrice));
            }
        }
    }

    public static ProductType getProductType(String type){
        for(ProductType productType : productTypeRepository.find())
            if(Objects.equals(type, productType.getType()))
                return productType;
         return null;
    }

    public static void close() {
        productTypeRepository.close();
        database.close();
    }
}
