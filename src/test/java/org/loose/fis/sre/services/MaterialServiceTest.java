package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loose.fis.sre.exceptions.MaterialAlreadyExistsException;
import org.loose.fis.sre.model.Material;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaterialServiceTest {

    private static final String NAME = "name";
    private static final int PRICE = 0;

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        MaterialService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        MaterialService.close();
    }

    @Test
    @DisplayName("Database is initialized, and there are no materials")
    void testDatabaseIsInitializedAndNoMaterialIsPersisted() {
        assertThat(MaterialService.getAllMaterials()).isNotNull();
        assertThat(MaterialService.getAllMaterials()).isEmpty();
    }

    @Test
    @DisplayName("Material is successfully persisted to Database")
    void testMaterialIsAddedToDatabase() throws MaterialAlreadyExistsException {
        MaterialService.addMaterial(NAME, PRICE);
        assertThat(MaterialService.getAllMaterials()).isNotEmpty();
        assertThat(MaterialService.getAllMaterials()).size().isEqualTo(1);
        Material material = MaterialService.getAllMaterials().get(0);
        assertThat(material).isNotNull();
        assertThat(material.getName()).isEqualTo(NAME);
        assertThat(material.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Material can not be added twice")
    void testMaterialCanNotBeAddedTwice() {
        assertThrows(MaterialAlreadyExistsException.class, () -> {
            MaterialService.addMaterial(NAME, PRICE);
            MaterialService.addMaterial(NAME, PRICE);
        });
    }

    @Test
    @DisplayName("Material successfully removed")
    void testMaterialSuccessfullyRemoved() throws MaterialAlreadyExistsException{
        MaterialService.addMaterial(NAME, PRICE);
        MaterialService.removeMaterial(new Material(NAME, PRICE));
        assertThat(MaterialService.getAllMaterials()).isEmpty();
    }

    @Test
    @DisplayName("Not all materials removed from database")
    void testNotAllMaterialsRemoved() throws MaterialAlreadyExistsException{
        MaterialService.addMaterial(NAME + "1", PRICE);
        MaterialService.addMaterial(NAME + "2", PRICE);
        MaterialService.removeMaterial(new Material(NAME + "1", PRICE));
        assertThat(MaterialService.getAllMaterials()).isNotEmpty();
        assertThat(MaterialService.getAllMaterials().size()).isEqualTo(1);
        assertThat(MaterialService.getAllMaterials().get(0)).isEqualTo(new Material(NAME + "2", PRICE));
    }

    @Test
    @DisplayName("A specific exception thrown if the material name can be found in the database")
    void testCheckMaterialDoesNotAlreadyExists() throws MaterialAlreadyExistsException {
        MaterialService.addMaterial(NAME, PRICE);
        assertThrows(MaterialAlreadyExistsException.class, () -> {
            MaterialService.checkMaterialDoesNotAlreadyExist(NAME);
        });
    }

    @Test
    @DisplayName("Price is successfully changed for a specific material")
    void testCorrectMaterialPriceChanged() throws MaterialAlreadyExistsException{
        MaterialService.addMaterial(NAME, PRICE);
        MaterialService.modifyPrice(NAME, PRICE + 1);
        Material material = MaterialService.getAllMaterials().get(0);
        assertThat(material.getName()).isEqualTo(NAME);
        assertThat(material.getPrice()).isEqualTo(PRICE + 1);
    }

    @Test
    @DisplayName("Correct material object returned")
    void testCorrectMaterialReturned() throws MaterialAlreadyExistsException {
        MaterialService.addMaterial(NAME, PRICE);
        Material material = MaterialService.getMaterial(NAME);
        assertThat(material).isEqualTo(MaterialService.getAllMaterials().get(0));
    }
}