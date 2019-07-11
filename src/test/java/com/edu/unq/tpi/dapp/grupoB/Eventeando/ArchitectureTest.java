package com.edu.unq.tpi.dapp.grupoB.Eventeando;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import javax.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.edu.unq.tpi.dapp.grupoB.Eventeando")
public class ArchitectureTest {

    @ArchTest
    private final ArchRule noInstanceVariableShouldBePublic =
            fields().that().areNotStatic()
                    .should().notBePublic()
                    .because("encapsulation never should be broken");

    @ArchTest
    private final ArchRule noAccessToStandardStreams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    private final ArchRule persistableEntitiesMustBeInDominio =
            classes().that().areAnnotatedWith(Entity.class)
                    .should().resideInAPackage("com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio")
                    .because("persistable entities must be in .dominio");

    @ArchTest
    private final ArchRule serviceNamingConvention =
            classes().that().resideInAPackage("main.java.com.edu.unq.tpi.dapp.grupoB.Eventeando.service")
                    .should().haveSimpleNameEndingWith("Service");

    @ArchTest
    private final ArchRule domainClassesShouldBePublic =
            classes().that().resideInAPackage("com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio")
                .should().bePublic();

    @ArchTest
    private final ArchRule persistenceClassesShouldHaveSpringRepositoryAnnotation =
            classes().that().resideInAPackage("main.java.com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence")
                .should().beAnnotatedWith(Repository.class);
}
