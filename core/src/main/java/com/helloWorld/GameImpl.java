package com.helloWorld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class GameImpl implements Game{

    // == constants ==
    private static final Logger log = LoggerFactory.getLogger(GameImpl.class);

    // == fields ==
    private final NumberGenerator numberGenerator;
    private final int guessCount;
    private int number;
    private int guess;
    private int smallest;
    private int biggest;
    private int remainingGuesses;
    private boolean validNumberRange = true;

    // == constructor ==


    public GameImpl(NumberGenerator numberGenerator, @GuessCount int guessCount) {
        this.numberGenerator = numberGenerator;
        this.guessCount = guessCount;
    }

    // == init ==
    @PostConstruct
    @Override
    public void reset() {
        guess = numberGenerator.getMinNumber();
        remainingGuesses = guessCount;
        smallest = numberGenerator.getMinNumber();
        biggest = numberGenerator.getMaxNumber();
        number = numberGenerator.next();
        log.debug("The number is {}", number);
    }

    @PreDestroy
    public void preDestroy() {
        log.info("in Game preDestroy()");
    }

    // == public methods ==

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getGuess() {
        return guess;
    }

    @Override
    public void setGuess(int guess) {
        this.guess = guess;
    }

    @Override
    public int getSmallest() {
        return smallest;
    }

    @Override
    public int getBiggest() {
        return biggest;
    }

    @Override
    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    @Override
    public int getGuessCount() {
        return guessCount;
    }

    @Override
    public void check() {

        checkValidNumberRange();

        if (validNumberRange) {
            if (guess > number) {
                biggest = guess -1;
            }

            if (guess < number) {
                smallest = guess + 1;
            }
        }

        remainingGuesses--;

    }

    @Override
    public boolean isValidNumberRange() {
        return validNumberRange;
    }

    @Override
    public boolean isGameWow() {
        return guess == number;
    }

    @Override
    public boolean isGameLost() {
        return !isGameWow() && remainingGuesses <= 0;
    }

    // == private methods ==
    private void checkValidNumberRange() {
        validNumberRange = (guess >= smallest) && (guess <= biggest);
    }
}
