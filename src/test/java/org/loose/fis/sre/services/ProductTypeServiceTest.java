package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loose.fis.sre.exceptions.TypeAlreadyExistsException;
import org.loose.fis.sre.model.ProductType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeServiceTest {

    private static final String TYPE = "type";
    private static final int PRICE = 0;

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ProductTypeService.initDatabase();
    }

    @Test
    @DisplayName("Database is initialized, and there are no product types")
    void testDatabaseIsInitializedAndNoProductTypeIsPersisted() {
        assertThat(ProductTypeService.getAllProductTypes()).isNotNull();
        assertThat(ProductTypeService.getAllProductTypes()).isEmpty();
    }

    @Test
    @DisplayName("Product type is successfully persisted to Database")
    void testProductTypeIsAddedToDatabase() throws TypeAlreadyExistsException {
        ProductTypeService.addType(TYPE, PRICE);
        assertThat(ProductTypeService.getAllProductTypes()).isNotEmpty();
        assertThat(ProductTypeService.getAllProductTypes()).size().isEqualTo(1);
        ProductType productType = ProductTypeService.getAllProductTypes().get(0);
        assertThat(productType).isNotNull();
        assertThat(productType.getType()).isEqualTo(TYPE);
        assertThat(productType.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Product type can not be added twice")
    void testProductTypeCanNotBeAddedTwice() {
        assertThrows(TypeAlreadyExistsException.class, () -> {
            ProductTypeService.addType(TYPE, PRICE);
            ProductTypeService.addType(TYPE, PRICE);
        });
    }

    @Test
    @DisplayName("Product type successfully removed")
    void testProductTypeSuccessfullyRemoved() throws TypeAlreadyExistsException{
        ProductTypeService.addType(TYPE, PRICE);
        ProductTypeService.removeType(new ProductType(TYPE, PRICE));
        assertThat(ProductTypeService.getAllProductTypes()).isEmpty();
    }

    @Test
    @DisplayName("Not all product types removed from database")
    void testNotAllProductTypesRemoved() throws TypeAlreadyExistsException{
        ProductTypeService.addType(TYPE + "1", PRICE);
        ProductTypeService.addType(TYPE + "2", PRICE);
        ProductTypeService.removeType(new ProductType(TYPE + "1", PRICE));
        assertThat(ProductTypeService.getAllProductTypes()).isNotEmpty();
        assertThat(ProductTypeService.getAllProductTypes().size()).isEqualTo(1);
        assertThat(ProductTypeService.getAllProductTypes().get(0)).isEqualTo(new ProductType(TYPE + "2", PRICE));
    }

    @Test
    @DisplayName("A specific exception thrown if the product type can be found in the database")
    void testCheckProductTypeDoesNotAlreadyExists() throws TypeAlreadyExistsException {
        ProductTypeService.addType(TYPE, PRICE);
        assertThrows(TypeAlreadyExistsException.class, () -> {
            ProductTypeService.checkTypeDoesNotAlreadyExist(TYPE);
        });
    }

    @Test
    @DisplayName("Price is successfully changed for a specific product type")
    void testCorrectProductTypePriceChanged() throws TypeAlreadyExistsException{
        ProductTypeService.addType(TYPE, PRICE);
        ProductTypeService.modifyPrice(TYPE, PRICE + 1);
        ProductType productType = ProductTypeService.getAllProductTypes().get(0);
        assertThat(productType.getType()).isEqualTo(TYPE);
        assertThat(productType.getPrice()).isEqualTo(PRICE + 1);
    }

    @Test
    @DisplayName("Correct product type object returned")
    void testCorrectProductTypeReturned() throws TypeAlreadyExistsException {
        ProductTypeService.addType(TYPE, PRICE);
        ProductType productType = ProductTypeService.getProductType(TYPE);
        assertThat(productType).isEqualTo(ProductTypeService.getAllProductTypes().get(0));
    }
}