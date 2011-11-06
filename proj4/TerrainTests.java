import org.junit.* ;
import java.io.*;
import static org.junit.Assert.* ;

public class TerrainTests {

    static PgmImageInfo pgmInf;
  
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("TerrainTests");
    }
    @Before
    public void beforeEach() 
    throws  PgmReadException, IOException 
    {
        Terrain.pgmInf = new PgmImageInfo( "test.pgm" );
    }

    @Test
    public void testLine() {
        // derectly next should be visible
        assertTrue( Terrain.lineBetween(4, 4, 3, 3) );
        assertTrue( Terrain.lineBetween(4, 4, 3, 4) );
        assertTrue( Terrain.lineBetween(4, 4, 3, 5) );
        assertTrue( Terrain.lineBetween(4, 4, 4, 5) );
        assertTrue( Terrain.lineBetween(4, 4, 5, 5) );
        assertTrue( Terrain.lineBetween(4, 4, 5, 4) );
        assertTrue( Terrain.lineBetween(4, 4, 5, 3) );
        assertTrue( Terrain.lineBetween(4, 4, 4, 3) );
    }
    @Test
    public void testLine2() {
        assertFalse( Terrain.lineBetween(4, 4, 2, 2) );
        assertFalse( Terrain.lineBetween(4, 4, 3, 2) );
        assertFalse( Terrain.lineBetween(4, 4, 4, 2) );

        assertFalse( Terrain.lineBetween(4, 4, 2, 1) );
        assertFalse( Terrain.lineBetween(4, 4, 3, 1) );
        assertFalse( Terrain.lineBetween(4, 4, 4, 1) );

        assertFalse( Terrain.lineBetween(4, 4, 2, 0) );
        assertFalse( Terrain.lineBetween(4, 4, 3, 0) );
        assertFalse( Terrain.lineBetween(4, 4, 4, 0) );

        assertFalse( Terrain.lineBetween(4, 4, 0, 7) );
        assertTrue(  Terrain.lineBetween(4, 4, 1, 7) );
        assertFalse( Terrain.lineBetween(4, 4, 2, 7) );
        assertTrue(  Terrain.lineBetween(4, 4, 4, 7) );
    }
    @Test
    public void testLinePrb() {
        assertFalse( Terrain.lineBetween(4, 4, 5, 1) );
        assertFalse( Terrain.lineBetween(4, 4, 3, 7) );
    }
    @Test
    public void testLine0() {
        assertFalse( Terrain.lineBetween(0, 0, 2, 0) );
        assertTrue(  Terrain.lineBetween(0, 0, 3, 0) );
    }

}
