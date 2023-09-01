package dndcopy.fr.ecoleNum.dd.gameEngine;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import dndcopy.fr.ecoleNum.dd.DB.Env;
import dndcopy.fr.ecoleNum.dd.character.Character;
import dndcopy.fr.ecoleNum.dd.exceptions.CharacterDeadException;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.Case;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.DeadFoe;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.EmptyCase;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.foe.*;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.*;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.Bonus;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment.FireBolt;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment.Mace;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment.Sword;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment.ThunderBolt;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.potions.BigPotion;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.potions.ClassicPotion;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.foe.*;
import dndcopy.fr.ecoleNum.dd.gameComponents.dice.Dice;
import dndcopy.fr.ecoleNum.dd.gameComponents.dice.D6;

/**
 * Cette classe va contrôler le déroulement du jeu
 */
public class Game {
    private Menu menu;
    private Character character;
    private int characterPosition;
    private Dice dice;
    private Dice fleeingDice;
    private ArrayList<Case> boardGame;
    private boolean run;
    private boolean gameInProgress;
    public Game() {
        menu = new Menu();
        boardGame = new ArrayList<>();
        character = null;
        //dice = new D6();
        fleeingDice = new D6();
        run = true;
        gameInProgress = false;
        Properties config = Config.getConfig();
        String diceConfig = config.getProperty("dice");
        Class<?> diceType;
        try {
            diceType = Class.forName("dndcopy.fr.ecoleNum.dd.gameComponents.dice."+diceConfig);
            dice = (Dice) diceType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * cette méthode permet de vérifier si le jeu est toujours en cours.
     * @return l'état du jeu
     */
    public boolean isRunning() {
        return run;
    }

    /**
     * cette méthode ajoute des objets cases dans la liste qui correspond au plateau de jeu.
     */
    private void createBoard() {

        String[] caseNames = {"EmptyCase", "bonus.attackEquipment.Mace", "bonus.attackEquipment.Sword", "bonus.attackEquipment.ThunderBolt", "bonus.attackEquipment.FireBolt", "bonus.potions.BigPotion", "bonus.potions.ClassicPotion", "foe.Goblin", "foe.Wizard", "foe.Dragon"};
        int[][] caseInfo = {{0, 0, 9, 0, 9, 19, 0, 0, 9, 19}, {15, 24, 38, 47, 56, 60, 70, 83, 95, 100}, {15, 5, 4, 5, 2, 2, 6, 10, 10, 4}};

        for (int i=0; i <63; i++) {
            boolean found = false;
            int chosenCaseType = -1;

            while (!found) {
                chosenCaseType = -1;
                int random = ThreadLocalRandom.current().nextInt(1, 101);


                int index = 0;
                while (index < caseNames.length && chosenCaseType == -1) {

                    if (random <= caseInfo[1][index]) {
                        chosenCaseType = index;
                    }
                    index++;
                }

                if (caseInfo[2][chosenCaseType] > 0 && i >= caseInfo[0][chosenCaseType]) {
                    caseInfo[2][chosenCaseType]--;
                    found = true;
                }
            }

            Case selectedCase;

            try {
                Class<?> className = Class.forName("dndcopy.fr.ecoleNum.dd.gameComponents.boardGame."+caseNames[chosenCaseType]);
                selectedCase = (Case) className.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            boardGame.add(selectedCase);
        }

        boardGame.add(new GiantCockroach());

    }

    /**
     * cette méthode est appelée au lancement du jeu, elle appelle le menu puis lance des parties.
     */
    public void start() {
        System.out.println("Welcome to the dungeon of Ragnarok !");
        while (isRunning()) {
            while (menu.isRunning()) {
                try {
                    switch (menu.getMenuState()) {
                        case "startMenu" :
                            run = menu.startMenu(character);
                            break;
                        case "characterSelection" :
                            character = menu.characterSelection(character);
                            break;
                        case "createCharacter" :
                            character = menu.createCharacter(character);
                            break;
                        default :
                            Method method = menu.getClass().getMethod(menu.getMenuState(), Character.class);
                            method.invoke(menu, character);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                    menu.resetMenu();
                }
            }
            if (run) {
                setGame();
                play();
            }
        }
        System.out.println("Goodbye.");
    }

    /**
     * Cette méthode permet au personnage d'effectuer un tour de jeu tant qu'il est en vie.
     */
    private void play() {
        gameInProgress = true;
        characterPosition = 0;
        System.out.println(character.getName()+ " is starting his journey !");
        try {
            while (gameInProgress) {
                    playATurn();
            }
        } catch(CharacterDeadException e) {
            gameInProgress = false;
        }

        System.out.println("Would you like to start another game ?");
        switch (menu.yesOrNo()) {
            case "y":
                break;
            case "n":
                menu.resetMenu();
                break;
        }
    }

    /**
     * Cette méthode permet au joueur d'effectuer des actions lors de sa partie.
     * @throws CharacterDeadException lorsque le personnage n'a plus de vie.
     */
    private void playATurn() throws CharacterDeadException {
        if (characterPosition!=boardGame.size()) {
            int choice = menu.nextAction();
            if (choice == 1) {
                dice.throwDice();
                moveForward();
            } else if (choice == 2) {
                menu.informations(character);
            } else {
                menu.useItem(character);
            }
        } else {
            System.out.println("Congratulations, you finished your adventure!");
            gameInProgress = false;
        }
    }

    /**
     * Cette méthode fait avancer le personnage sur le plateau et change les cases en fonction des actions du joueur.
     * @throws CharacterDeadException lorsque le personnage n'a plus de vie.
     */
    private void moveForward() throws CharacterDeadException {
        characterPosition += dice.getValue();
        if (characterPosition > boardGame.size()) {
            characterPosition = boardGame.size();
        }
        System.out.println("You threw a " + dice.getValue() + "\n" + character.getName() + " is on the case number " + characterPosition + "\n" + boardGame.get(characterPosition - 1));
        boardGame.get(characterPosition - 1).interaction(character);
        checkForDeadFoe();
        checkIfPickedUp();
        if (character.hasFleed()) {
            fleeing();
        }
    }

    /**
     * Cette méthode faire reculer le personnage et lance une intéraction avec la nouvelle case.
     * @throws CharacterDeadException lorsque le personnage n'a plus de vie.
     */
    private void fleeing() throws CharacterDeadException {
        while (character.hasFleed()) {
            fleeingDice.throwDice();
            characterPosition -= fleeingDice.getValue();
            if (characterPosition < 1) {
                characterPosition = 1;
            }
            System.out.println("You cowardly went to case "+ characterPosition + "\n" + boardGame.get(characterPosition - 1));
            character.setHasFleed(false);
            boardGame.get(characterPosition - 1).interaction(character);
            checkForDeadFoe();
            checkIfPickedUp();

        }
    }

    /**
     * Cette méthode remplace une case monstre par une case cadavre s'il n'a plus de point de vie.
     */
    private void checkForDeadFoe() {
        int caseNumber = characterPosition-1;
        Case foe = boardGame.get(caseNumber);
        if ( foe instanceof Foe) {
            if (((Foe) foe).getLifeLevel() <= 0) {
                boardGame.set(caseNumber, new DeadFoe());
            }
        }
    }

    /**
     * Cette méthode remplace une case bonus par une case vide si l'objet a été mis dans l'inventaire.
     */
    private  void checkIfPickedUp() {
        int caseNumber = characterPosition-1;
        Case bonus = boardGame.get(caseNumber);
        if ( bonus instanceof Bonus) {
            if (((Bonus) bonus).isInInventory()) {
                boardGame.set(caseNumber, new EmptyCase());
            }
        }
    }

    /**
     * Cette méthode remet la partie à 0 avant d'en lancer une nouvelle.
     */
    private void setGame() {
        character.setLifeLevel(character.getMinHealth());
        character.setAttackStrength(character.getMinStrength());
        boardGame.clear();
        character.clearAttackInventory();
        character.clearSatchel();
        createBoard();
    }
}