package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuildingNumberTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BuildingNumber(null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenBuildingNumberIs15() {
        BuildingNumber buildingNumber = new BuildingNumber("15");
        assertNotNull(buildingNumber);
    }

    @Test
    void Constructor_ShouldCreateObject_WhenBuildingNumberIs15A() {
        BuildingNumber buildingNumber = new BuildingNumber("15A");
        assertNotNull(buildingNumber);
    }

    @Test
    void Constructor_ShouldCreateObject_WhenBuildingNumberIs15By2() {
        BuildingNumber buildingNumber = new BuildingNumber("15/2");
        assertNotNull(buildingNumber);
    }

    @Test
    void Constructor_ShouldCreateObject_WhenBuildingNumberIs15ABy2() {
        BuildingNumber buildingNumber = new BuildingNumber("15A/2");
        assertNotNull(buildingNumber);
    }

    @Test
    void Constructor_ShouldCreateObject_WhenBuildingNumberIs70CBy10() {
        BuildingNumber buildingNumber = new BuildingNumber("70C/10");
        assertNotNull(buildingNumber);
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsA15() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BuildingNumber("A15");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsA15$() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BuildingNumber("15$");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsA_17() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BuildingNumber("_17");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsXD() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BuildingNumber("XD");
        });
    }

}
