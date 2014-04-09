package dk.keycore;

import javax.swing.*;

/**
 * Created by sudarshana on 3/15/14.
 */
public class ClassLoaderTest {

    public void testClassLoader()
    {
        final ClassLoader sysLoader = ClassLoader.getSystemClassLoader();
        System.out.println("System classloader is" + sysLoader);

        final ClassLoader classClassLoader = this.getClass().getClassLoader();
        System.out.println("Class class loader" + classClassLoader);
    }

    public static void main(String[] args) {
        final ClassLoaderTest obj = new ClassLoaderTest();

        obj.testClassLoader();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                obj.testClassLoader();
            }
        });


        final Thread t = new Thread()
        {
            public void run()
            {
                obj.testClassLoader();
            }
        };
        t.start();
    }
}
