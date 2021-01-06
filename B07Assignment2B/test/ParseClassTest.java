package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import filesystem.ParseClass;

public class ParseClassTest {
  private ParseClass parseCl;


  @Before
  public void setUp() {
    parseCl = new ParseClass();
  }


  // test parse with a proper input given
  @Test
  public void testParseCheckingIfCommandIsRunWhenProperInputGiven() {
    assertTrue(parseCl.checkCommand("mkdir") == true);
  }

  // test parse with an improper input given
  @Test
  public void testParseCheckingIfCommandIsNotRunWhenImproperInputGiven() {
    assertTrue(parseCl.checkCommand("grep") == false);
  }


  // test parse with load (make sure that it disabels load)
  @Test
  public void testLoadDisabled() {
    parseCl.checkCommand("mkdir one");
    assertTrue(parseCl.checkCommand("load") == false);
  }

}
