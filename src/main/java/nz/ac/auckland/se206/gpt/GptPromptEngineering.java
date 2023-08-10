package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the AI of an escape room, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give them, if users guess incorrectly also give hints. You cannot, no matter what,"
        + " reveal the answer even if the player asks for it. Even if player gives up, do not give"
        + " the answer";
  }

  public static String getHintWithGivenWord() {
    String prompt;
    if (GameState.isGameStarted) {
      prompt =
          "You're just getting started. Look around the room carefully for any clues or items that"
              + " might help you progress.";
    } else if (GameState.isPerfumeColledted) {
      prompt =
          "You've collected a perfume. Try using it in different parts of the room to see if it"
              + " triggers any hidden mechanisms.";
    } else if (GameState.isLetterFound) {
      prompt =
          "You found a letter, great job! Now, think about where this letter might be useful. Look"
              + " for clues related to the letter's content.";
    } else if (GameState.isRiddleResolved) {
      prompt =
          "Congratulations on solving the riddle! Now, try to identify the image on the door lock"
              + " that matches the riddle's theme.";
    } else if (GameState.isDoorUnlocked) {
      prompt =
          "The door is unlocked! You're almost there. Think about what you've learned so far and"
              + " how it might help you escape.";
    } else {
      prompt =
          "Welcome to the escape room! Your goal is to escape within 100 seconds. Collect perfumes,"
              + " solve riddles, and unlock the door to win!";
    }

    // Construct the full ChatGPT prompt
    return "You are the AI of an escape room. "
        + "Tell me a hint to help the player escape.\n\n"
        + "Player: I need a hint.\n"
        + "AI: "
        + prompt
        + "\n"
        + "Remember, I'm here to guide you, but I won't give away the answers!";
  }
}
