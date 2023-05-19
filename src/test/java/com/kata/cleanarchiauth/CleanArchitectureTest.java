package com.kata.cleanarchiauth;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.kata.cleanarchiauth", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
class CleanArchitectureTest {

    private static final String APPLICATION_NAME = "application";
    private static final String APPLICATION_PATH = "..application..";

    private static final String DOMAIN_NAME = "domain";
    private static final String DOMAIN_PATH = "..domain..";

    private static final String USE_CASES_NAME = "usecases";
    private static final String USE_CASES_PATH = "..usecases..";

    private static final String INFRASTRUCTURE_NAME = "infrastructure";
    private static final String INFRASTRUCTURE_PATH = "..infrastructure..";


    @ArchTest
    static final ArchRule applicationLayerShouldNotBeAccessedByAnyLayer =
            layeredArchitecture()
                    .layer(APPLICATION_NAME).definedBy(APPLICATION_PATH)
                    .layer(DOMAIN_NAME).definedBy(DOMAIN_PATH)
                    .layer(USE_CASES_NAME).definedBy(USE_CASES_PATH)
                    .layer(INFRASTRUCTURE_NAME).definedBy(INFRASTRUCTURE_PATH)
                    .whereLayer(APPLICATION_NAME).mayNotBeAccessedByAnyLayer();


    @ArchTest
    static final ArchRule domainLayerCanBeAccessedByAnyLayers =
            layeredArchitecture()
                    .layer(APPLICATION_NAME).definedBy(APPLICATION_PATH)
                    .layer(DOMAIN_NAME).definedBy(DOMAIN_PATH)
                    .layer(USE_CASES_NAME).definedBy(USE_CASES_PATH)
                    .layer(INFRASTRUCTURE_NAME).definedBy(INFRASTRUCTURE_PATH)
                    .whereLayer(DOMAIN_NAME).mayOnlyBeAccessedByLayers(APPLICATION_NAME, INFRASTRUCTURE_NAME, USE_CASES_NAME);


    @ArchTest
    static final ArchRule useCasesShouldBeOnlyAccessedByApplicationAndInfrastructure =
            layeredArchitecture()
                    .layer(APPLICATION_NAME).definedBy(APPLICATION_PATH)
                    .layer(DOMAIN_NAME).definedBy(DOMAIN_PATH)
                    .layer(USE_CASES_NAME).definedBy(USE_CASES_PATH)
                    .layer(INFRASTRUCTURE_NAME).definedBy(INFRASTRUCTURE_PATH)
                    .whereLayer(USE_CASES_NAME).mayOnlyBeAccessedByLayers(APPLICATION_NAME, INFRASTRUCTURE_NAME);


    @ArchTest
    static final ArchRule infrastructureLayerShouldNotBeAccessedByAnyLayer =
            layeredArchitecture()
                    .layer(APPLICATION_NAME).definedBy(APPLICATION_PATH)
                    .layer(DOMAIN_NAME).definedBy(DOMAIN_PATH)
                    .layer(USE_CASES_NAME).definedBy(USE_CASES_PATH)
                    .layer(INFRASTRUCTURE_NAME).definedBy(INFRASTRUCTURE_PATH)
                    .whereLayer(INFRASTRUCTURE_NAME).mayNotBeAccessedByAnyLayer();

}

