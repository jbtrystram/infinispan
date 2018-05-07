package org.infinispan.commons.test.skip;


import java.util.Arrays;

import org.testng.SkipException;

/**
 * Allows to skip a test on certain Operation Systems.
 */
public class SkipTestNG {
   /**
    * Use within a {@code @Test} method to skip that method on some OSes.
    * Use in a {@code @BeforeClass} method to skip all methods in a class on some OSes.
    */
   public static void skipOnOS(OS... oses) {
      OS currentOs = OS.getCurrentOs();
      if (Arrays.asList(oses).contains(currentOs)) {
         throw new SkipException("Skipping test on " + currentOs);
      }
   }

   /**
    * Use within a {@code @Test} method to skip that method on all versions of Java equal or greater
    * to the one specified.
    * Use in a {@code @BeforeClass} method to skip all methods in a class on some JDKs.
    */
   public static void skipSinceJDK(int major) {
      int version = getJDKVersion();
      if (version >= major) {
         throw new SkipException("Skipping test on JDK " + version);
      }
   }

   /**
    * Use within a {@code @Test} method to skip that method on all versions of Java less than the one specified.
    * Use in a {@code @BeforeClass} method to skip all methods in a class on some JDKs.
    */
   public static void skipBeforeJDK(int major) {
      int version = getJDKVersion();
      if (version < major) {
         throw new SkipException("Skipping test on JDK " + version);
      }
   }

   private static int getJDKVersion() {
      String[] parts = System.getProperty("java.version").split("\\.");
      int version = Integer.parseInt(parts[0]);
      if (version == 1)
         version = Integer.parseInt(parts[1]);
      return version;
   }
}
