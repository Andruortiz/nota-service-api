package co.edu.uco.noteserviceapi;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class HexagonalArchitectureTest {

    private JavaClasses projectClasses;

    @BeforeEach
    void setUp() {
        projectClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("co.edu.uco.noteserviceapi");
    }

    @Test
    @DisplayName("Should ensure that layers follow strict hexagonal architecture dependency directions")
    void shouldEnsureLayersFollowStrictHexagonalArchitectureDependencyDirections() {
        Architectures.LayeredArchitecture hexagonalArchitecture = layeredArchitecture()
                .consideringAllDependencies()
                .layer("Domain").definedBy("co.edu.uco.noteserviceapi.domain..")
                .layer("Services").definedBy("co.edu.uco.noteserviceapi.services..")
                .layer("Infrastructure").definedBy("co.edu.uco.noteserviceapi.infrastructure..")
                .layer("Crosscutting").definedBy("co.edu.uco.noteserviceapi.crosscutting..")
                .layer("Bootstrap").definedBy("co.edu.uco.noteserviceapi.bootstrap..");

        hexagonalArchitecture
                // Domain rules
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Services", "Infrastructure", "Bootstrap")

                // Services rules
                .whereLayer("Services").mayOnlyBeAccessedByLayers("Infrastructure", "Bootstrap")

                // Infrastructure rules
                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()

                // Crosscutting rules
                .whereLayer("Crosscutting").mayOnlyBeAccessedByLayers("Domain", "Services", "Infrastructure", "Bootstrap")

                // Bootstrap rulesmayBeAccessedByAllLayers
                .whereLayer("Bootstrap").mayNotBeAccessedByAnyLayer();

        hexagonalArchitecture.check(projectClasses);
    }

    @Test
    @DisplayName("Should ensure that domain classes are fully independent of external frameworks")
    void shouldEnsureThatDomainClassesAreFullyIndependentOfExternalFrameworks() {
        classes().that().resideInAPackage("co.edu.uco.noteserviceapi.domain..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "co.edu.uco.noteserviceapi.domain..",
                        "co.edu.uco.noteserviceapi.crosscutting..",
                        "java..",
                        "org.slf4j.."
                )
                .check(projectClasses);
    }

    @Test
    @DisplayName("Should ensure that controllers reside within the proper infrastructure input package")
    void shouldEnsureThatControllersResideWithinTheProperInfrastructureInputPackage() {
        classes().that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("co.edu.uco.noteserviceapi.infrastructure.input.controller..")
                .check(projectClasses);
    }

    @Test
    @DisplayName("Should ensure that JPA entities reside within the proper infrastructure output package")
    void shouldEnsureThatJpaEntitiesResideWithinTheProperInfrastructureOutputPackage() {
        classes().that().haveSimpleNameEndingWith("JpaEntity")
                .should().resideInAPackage("co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity..")
                .check(projectClasses);
    }
}
