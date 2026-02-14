package com.example.carparking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class CarParkingApplicationTests {

    @Test
    void contextLoads() {
        ApplicationModules modules = ApplicationModules.of(CarParkingApplication.class)
                .verify();
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

}
