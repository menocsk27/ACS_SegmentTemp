/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The Class TestJUnitClasses. Main connector of the . It does not belongs to the public API of the
 * project.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({TestSet1.class, TestSet2.class, TestSet3.class})

public class TestJUnitClasses {
}
