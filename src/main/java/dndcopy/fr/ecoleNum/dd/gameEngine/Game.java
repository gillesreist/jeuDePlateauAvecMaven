package dndcopy.fr.ecoleNum.dd.gameEngine;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

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
        Class diceType = null;
        try {
            diceType = Class.forName("dndcopy.fr.ecoleNum.dd.gameComponents.dice."+diceConfig);
            dice = (Dice) diceType.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
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
        for (int i=0; i <64; i++) {
            if (i==45 || i==52 || i==56 || i== 62) {
                boardGame.add(new Dragon());
            } else if (i==10 || i==20 || i==25 || i== 32 || i==35 || i==36 || i== 37 || i==40 || i==44 || i== 47) {
                boardGame.add(new Wizard());
            } else if (i==3|| i==6 || i==9 || i==12 || i==15 || i==18 || i==21 || i==24 || i==27 || i==30) {
                boardGame.add(new Goblin());
            } else if (i==2|| i==5 || i==11 || i==22 || i==38) {
                boardGame.add(new Mace());
            } else if (i==19|| i==26 || i==42 || i==53) {
                boardGame.add(new Sword());
            } else if (i==1|| i==4 || i==8 || i==17 || i==23) {
                boardGame.add(new ThunderBolt());
            } else if (i==48|| i==49) {
                boardGame.add(new FireBolt());
            } else if (i==7|| i==13 || i==31 || i==33 || i==39 || i==43) {
                boardGame.add(new ClassicPotion());
            } else if (i==28|| i==41) {
                boardGame.add(new BigPotion());
            } else {
                boardGame.add(new EmptyCase());
            }
        }
        Collections.shuffle(boardGame);
        boardGame.set(63, new GiantCockroach());
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