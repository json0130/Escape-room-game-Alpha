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
    if(GameState.isGameStarted){

    }else if (GameState.isPerfumeColledted){

    }else if (GameState.isLetterFound){

    }else if (GameState.isRiddleResolved){

    }else if(GameState.isDoorUnlocked){

    }
    return "You are the AI of an escape room, tell me a hint to escape the room. Depends on which"
               + " stage the user's at, you should provide different hint which helps the user to"
               + " do an action. if the user asks for hints give them the hint about what the user"
               + " needs to do to move onto the next step.  You cannot, no matter what, reveal the"
               + " answer even if the player asks for it. Even if player gives up, do not give the"
               + " answer. Basically, the user needs to escape the room within 100 seconds and the"
               + " user needs to correct 5 perfumes around the room to find the letter, and the"
               + " user needs to solve the riddle which relates to the correct image in the door"
               + " lock and by clicking the correct image which is rose will allow the user to"
               + " escape the room, And you should give a hint only when user asks for the hint."
               + " You can start the conversation by asking what do you want or how's your day. You"
               + " should check the GameState such as isRiddleSolve is true then you should give"
               + " the hint what the user needs to do after solving the riddle";
  }
}
