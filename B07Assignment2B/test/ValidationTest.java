package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import filesystem.Validation;

public class ValidationTest {
  private Validation validate;

  @Before
  public void setUp() {
    validate = new Validation();

  }

  // test the checkQuotation method which ensures that there are no
  // extra quotations inside the outer quotes
  @Test
  public void testValidationForValidInputSurroundedByQuotation() {

    Boolean expectedOutput = true;
    assertEquals(this.validate.checkQuotation("\"work\""), expectedOutput);
  }

  // test checkQuotation for quotes inside the input (returns false because
  // there are quotes inside which means it is invalid)
  @Test
  public void testValidationForInValidInputSurroundedByQuotation() {

    Boolean expectedOutput = false;
    assertEquals(this.validate.checkQuotation("\"wor\"k\""), expectedOutput);
  }

  // check that the input is equal to quit without un necessary add ons
  @Test
  public void testValidationForValidInputForSpeakCommand() {
    Boolean expectedOutput = true;
    assertEquals(this.validate.validateSpeakCommand(" QUIT"), expectedOutput);
  }

  // test that it makes sure it is equal to QUIT exactly and no other un-necessary
  // parameters
  @Test
  public void testValidationForInValidInputForSpeakCommand() {
    Boolean expectedOutput = false;
    assertEquals(this.validate.validateSpeakCommand("\"QUIT  no  \""),
        expectedOutput);
  }

  // test validation chevkValidChars() to ensure there are no invalid characters
  @Test
  public void testValidationForValidCharacters() {
    boolean expectedOutput = true;
    assertEquals(this.validate.checkValidChars("command okok"), expectedOutput);
  }

  // test validation checkValidChars() to ensure it returns false if the name
  // contains invalid naming schemes
  @Test
  public void testValidationForInvalidCharacters() {
    boolean expectedOutput = false;
    assertEquals(this.validate.checkValidChars("command wo?r.ks"),
        expectedOutput);
  }

  // test validation checkNumberOfParameterOne() to make sure it returns false
  // with two parameters
  @Test
  public void testValidationForCheckNumberOfParameterOne() {
    boolean expectedOutput = false;
    assertEquals(this.validate.checkNumberOfParameterOne("command 1P 2P"),
        expectedOutput);
  }

  // check numberOfParametersZero() to make sure it works if no parameters
  // are provided
  @Test
  public void testValidationForCheckNumberOfParameterZero() {
    boolean expectedOutput = true;
    assertEquals(this.validate.checkNumberOfParameterZero("command"),
        expectedOutput);
  }

  // check checkNumberOfParametersZeroOrOne() with 3 parameters instead
  @Test
  public void testValidationForCheckNumberOfParameterZeroOrOne() {
    boolean expectedOutput = false;
    assertEquals(
        this.validate.checkNumberOfParameterZeroOrOne("command 1P 2P 3P"),
        expectedOutput);
  }

  // check checkEcHoCommand() to see if it returns 0 signifying echo type 1
  // i.e. just plain echo
  @Test
  public void testValidationForCheckEchoCommand() {
    int expectedOutput = 0;

    assertEquals(this.validate.checkEchoCommand("echo \"this and that\""),
        expectedOutput);
  }

  // check checkEchoCommand() to see if it returns a 2 signifying echo type 2
  @Test
  public void testValidationForCheckEchoCommandTwo() {
    int expectedOutput = 2;

    assertEquals(
        this.validate.checkEchoCommand("echo \"this and that\" >> file"),
        expectedOutput);

  }

  // check checkEcchoCommand() to see if it returns a 3 signifying echo type 3
  @Test
  public void testValidationForCheckEchoCommandThree() {
    int expectedOutput = 1;

    assertEquals(
        this.validate.checkEchoCommand("echo \"this and that\" > file"),
        expectedOutput);

  }

  // check checkEchoCommand() with no quotes around the parameter so returns
  // -1 (invalid)
  @Test
  public void testValidationForCheckEchoCommandFour() {
    int expectedOutput = -1;

    assertEquals(this.validate.checkEchoCommand("echo asdfasd"),
        expectedOutput);

  }

  // check validateCatCommand() if it returns false since cat requires atleast
  // one parameter
  @Test
  public void testValidationForInvalidCatCommandInput() {
    boolean expectedOutput = false;
    assertEquals(this.validate.validateCatCommand("cat"), expectedOutput);

  }


  // check validateCatCommand() if it returns true with one parameter
  @Test
  public void testValidationForValidCatCommandInput() {
    boolean expectedOutput = true;
    assertEquals(this.validate.validateCatCommand("cat yes"), expectedOutput);

  }

  // check validateFindCommand() to see if it works with multiple pathways
  @Test
  public void testValidationForValidFindCommandInput() {
    boolean expectedOutput = true;
    assertEquals(this.validate.validateFindCommand(
        "find one two three -type f -name \"name\" ", 8), expectedOutput);
  }


  // check returns false since there is no dash in front of type ( -type )
  public void testValidationForInvalidFindCommandInput() {
    boolean expectedOutput = false;
    assertEquals(this.validate.validateFindCommand(
        "find one two type f -name \"name\" ", 8), expectedOutput);
  }

}
