/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The Class TestJUnitClasses. Main connector of the . It does not belongs to the public API of the
 * project.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({TestDownloadIcon.class, TestStatusBar.class, TestIntegration1.class, TestIntegration2.class, TestIntegration3.class, TestIntegration4.class, TestUnit1.class })

public class TestJUnitClasses {
}
