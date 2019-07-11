package com.edu.unq.tpi.dapp.grupoB.Eventeando;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import javax.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.edu.unq.tpi.dapp.grupoB.Eventeando")
public class ArchitectureTest {

    @ArchTest
    private final ArchRule no_instance_variable_should_be_public =
            fields().that().areNotStatic()
                    .should().notBePublic()
                    .because("encapsulation never should be broken");

    @ArchTest
    private final ArchRule no_access_to_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    private final ArchRule persistable_entities_must_be_in_dominio =
            classes().that().areAnnotatedWith(Entity.class)
                    .should().resideInAPackage("com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio")
                    .because("persistable entities must be in .dominio");

    @ArchTest
    private final ArchRule service_naming_convention =
            classes().that().resideInAPackage("main.java.com.edu.unq.tpi.dapp.grupoB.Eventeando.service")
                    .should().haveSimpleNameEndingWith("Service");

}
