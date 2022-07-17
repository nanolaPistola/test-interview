//package com.multicert.test;
//
//import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
//import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
//import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
//
//import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
//import com.tngtech.archunit.junit.AnalyzeClasses;
//import com.tngtech.archunit.junit.ArchTest;
//import com.tngtech.archunit.lang.ArchRule;
//
//@AnalyzeClasses(packagesOf = MulticertApp.class, importOptions = DoNotIncludeTests.class)
//class TechnicalStructureTest {
//
//    // prettier-ignore
//    @ArchTest
//    static final ArchRule respectsTechnicalArchitectureLayers = layeredArchitecture()
//        .layer("Config").definedBy("..config..")
//        .optionalLayer("Service").definedBy("..service..")
//        .layer("Persistence").definedBy("..repository..")
//        .layer("Domain").definedBy("..domain..")
//
//        .whereLayer("Config").mayNotBeAccessedByAnyLayer()
//        .whereLayer("Service").mayOnlyBeAccessedByLayers("Web", "Config")
//        .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service", "Security", "Web", "Config")
//        .whereLayer("Domain").mayOnlyBeAccessedByLayers("Persistence", "Service", "Security", "Web", "Config")
//
//        .ignoreDependency(belongToAnyOf(MulticertApp.class), alwaysTrue())
//        .ignoreDependency(alwaysTrue(), belongToAnyOf(
//            com.multicert.test.config.Constants.class,
//            com.multicert.test.config.ApplicationProperties.class
//        ));
//}