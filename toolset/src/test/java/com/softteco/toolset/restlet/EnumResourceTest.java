package com.softteco.toolset.restlet;

import com.softteco.toolset.bl.Assembler;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author serge
 */
public class EnumResourceTest {

    public EnumResourceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getEthnicities method, of class EnumResource.
     */
    @Test
    public void testGetValues() throws InstantiationException, IllegalAccessException {
        System.out.println("getValues");
        EnumResource instance = new EnumResource(TestEnum.class);
        List result = instance.getValues();
        assertEquals(TestEnum.values().length, result.size());
        for (TestEnum each : TestEnum.values()) {
            assertTrue(result.contains(each));
        }
    }
}
