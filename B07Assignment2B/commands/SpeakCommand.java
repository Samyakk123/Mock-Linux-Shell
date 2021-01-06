// **********************************************************
// Assignment2:
// Student1:Divyam Patel
// UTORID user_name: pate1006
// UT Student #: 1006139698
// Author: Divyam Patel
//
// Student2: Samyak Mehta
// UTORID user_name: mehtas28
// UT Student #: 1006298542
// Author: Samyak Mehta
//
// Student3: Aryan Patel
// UTORID user_name: pate1065
// UT Student #: 1006273514
// Author: Aryan Patel
//
// Student4: None
// UTORID user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Scanner;
import filesystem.ErrorOutput;
import filesystem.CommandsInterface;

/**
 * 
 * Class responsible for speaking based on user input.
 *
 */
public class SpeakCommand extends Commands implements CommandsInterface {
  private String toSpeak = null;

  /**
   * Executes the man command.
   */
  protected void executeCommand() {

    // note: code got from lecture!
    try {
      System.setProperty("freetts.voices",
          "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");

      // Register's Engine
      Central.registerEngineCentral(
          "com.sun.speech.freetts" + "" + ".jsapi.FreeTTSEngineCentral");

      // Create's a Synthesizer
      Synthesizer synthesizer =
          Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

      // Allocate's synthesizer
      synthesizer.allocate();
      synthesizer.resume();

      // Speaks the given text until the queue is empty.
      synthesizer.speakPlainText(this.toSpeak, null);
      synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Continuously gets input from user to be spoken until they input "QUIT".
   */
  private void speakMoreInput() {
    this.toSpeak = "";
    Scanner in = new Scanner(System.in);
    boolean quitFound = false;
    String newUserInput = "";

    // Keeps asking for more input until the user types in "QUIT"
    while (!quitFound) {
      newUserInput = in.nextLine();
      quitFound = this.validateInput.validateSpeakCommand(newUserInput);

      // Until "QUIT" is not found, adds user input to a string to be spoken
      if (!quitFound) {
        this.toSpeak += " " + newUserInput;
      }

      else {
        int index = newUserInput.indexOf(" QUIT");
        if (newUserInput.equals("QUIT")) {
          index = 0;
        }
        this.toSpeak += " " + newUserInput.substring(0, index);
        break;
      }
    }
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {
    int length = super.getUserInput().length();

    // Sets userInput so that echo is removed
    super.setUserInput(super.getUserInput().substring(5, length).strip());
    this.toSpeak = super.getUserInput();
    boolean value = false;

    // Checks if the user only typed in "speak" without any parameter
    // If so, then calls the "speakMoreInput" method to get more input from user
    if (this.getUserInput().isEmpty()) {
      speakMoreInput();
    }

    else {

      // Checks if the input the user types in quotations has more quotations
      // within
      value = !this.validateInput.checkQuotation(this.getUserInput());
      if (value) {
        super.content = null;
        ErrorOutput.notValidInput(super.getUserInput());
      }
    }

    if (!value) {
      this.executeCommand();
    }

    return super.content;
  }
}
