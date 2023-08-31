package dndcopy.fr.ecoleNum.dd.gameEngine;

import dndcopy.fr.ecoleNum.dd.DB.Hero;
import dndcopy.fr.ecoleNum.dd.character.Character;
import dndcopy.fr.ecoleNum.dd.character.Sorcerer;
import dndcopy.fr.ecoleNum.dd.character.Warrior;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.Bonus;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.potions.Potion;
import dndcopy.fr.ecoleNum.dd.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Cette classe va afficher les différents menus qui vont permettre au joueur d'intéragir avec le jeu.
 */
public class Menu {
    private boolean run;
    private String menuState;
    Scanner keyboard;
    public Menu() {
        menuState = "startMenu";
        run = true;
        keyboard = new Scanner(System.in);
    }

    /**
     * @return renvoie l'état du menu.
     */
    public String getMenuState() {
        return menuState;
    }

    /**
     * @return si le menu est actif.
     */
    public boolean isRunning() {
        return this.run;
    }

    /**
     * Cette méthode réactive le menu.
     */
    public void resetMenu() {
        this.run = true;
        menuState = "startMenu";
    }

    /**
     * Cette méthode est l'écran d'accueil du menu
     * @param character
     * @return interrupteur d'arrêt du jeu.
     */
    public boolean startMenu(Character character) {

        System.out.println("What would you like to do ?");
        if (character == null) {
            System.out.println("0 - Select an existing character");
            System.out.println("1 - Create a character");
        } else {
            System.out.println("1 - Go back to your character");
            System.out.println("2 - Start the adventure");
        }
        System.out.println("Q - quit the game");

        switch (keyboard.nextLine()) {

            case "0":
                menuState = "characterSelection";
                break;
            case "1":
                menuState = "characterCreation";
                break;
            case "2":
                if (character != null) {
                    this.run = false;
                }
                break;
            case "q":
                this.run = false;
                return false;
        }
        return true;
    }

    /**
     * Cette méthode permet de choisir un personnage depuis une base de donnée
     * @param character
     * @return le personnage choisi.
     */
    public Character characterSelection(Character character) {
        System.out.println("Choose one of the following characters");
        System.out.println("0 - none");
        Hero hero = new Hero();
        List<Character> characterList = hero.getHeroes();
        for (int i=0; i<characterList.size(); i++) {
            Character currentCharacter = characterList.get(i);
            System.out.print((i+1)+" - ");
            System.out.println(currentCharacter);
            String className = currentCharacter.getClass().getName();
            String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
            System.out.println(currentCharacter.getName()+" is a "+simpleClassName);
        }
        int choice = Utilities.toIntIfValid(keyboard.nextLine());
        switch (choice) {
            case 0 :
                menuState = "startMenu";
                return null;
            default :
                if (choice >= 1 && choice <= characterList.size()) {
                    menuState = "startMenu";
                    return characterList.get(choice-1);
                }
                return null;
        }
    }

    /**
     * Cette méthode appelle la création d'un personnage ou sa modification s'il existe déjà.
     * @param character
     */
    public void characterCreation(Character character) {
        if (character == null) {
            System.out.println("1 - Choose the class of your character");
        } else {
            System.out.println("1 - See your character informations");
            System.out.println("2 - Modify your character");
        }
        System.out.println("Q - return to main menu");

        switch (keyboard.nextLine()) {

            case "1":
                if (character == null) {
                    menuState = "createCharacter";
                } else {
                    menuState = "informations";
                }
                break;
            case "2":
                menuState = "modifyCharacter";
                break;
            case "q":
                menuState = "startMenu";
                break;
        }
    }

    /**
     * Cette méthode permet la création d'un personnage.
     * @param character
     * @return le personnage ainsi créé.
     */
    public Character createCharacter (Character character) {
        String characterClass = null;

        while (characterClass == null) {
            System.out.println("You want to create :");
            System.out.println("1 - A warrior");
            System.out.println("2 - A sorcerer");

            switch (keyboard.nextLine()) {
                case "1":
                    characterClass = "warrior";
                    break;
                case "2":
                    characterClass = "sorcerer";
                    break;
            }
        }

        System.out.println("Type the name of your character");
        String characterName = keyboard.nextLine();

        System.out.println("All may salute the mighty "+characterName+"!");
        if (characterClass.equals("warrior")) {
            character = new Warrior(characterName);
        } else {
            character = new Sorcerer(characterName);
        }
        keyboard.nextLine();
        menuState = "characterCreation";
        return character;
    }

    /**
     * Cette méthode affiche les informations du personnage créé ou sélectionné.
     * @param character
     */
    public void informations(Character character) {
        if (character == null) {
            System.out.println("You must create your character first.");
        } else {
            if (character instanceof Warrior) {
                System.out.println("Your character is a warrior named "+character.getName());
            } else {
                System.out.println("Your character is a sorcerer named "+character.getName());
            }
            System.out.println(character);
        }
        keyboard.nextLine();
        menuState = "characterCreation";
    }

    /**
     * Cette méthode permet de choisir quel paramètre de notre personnage nous voulons modifier.
     * @param character
     */
    public void modifyCharacter(Character character) {
        if (character == null) {
            System.out.println("You must create your character first.");
            keyboard.nextLine();
        } else {
            System.out.println("What do you want to change ?");
            System.out.println("1 - Your character Name");
            System.out.println("2 - Your character Health");
            System.out.println("3 - Your character Strength");
            System.out.println("Q - Nothing");
            switch (keyboard.nextLine()) {
                case "1":
                    menuState = "changeCharacterName";
                    break;
                case "2":
                    menuState = "changeCharacterHealth";
                    break;
                case "3":
                    menuState = "changeCharacterStrength";
                    break;
                case "q":
                    menuState = "characterCreation";
                    break;
            }
        }
    }

    /**
     * Cette méthode permet de changer le nom du personnage.
     * @param character
     */
    public void changeCharacterName(Character character) {
        System.out.println("Type the new name of your character.");
        character.setName(keyboard.nextLine());
        System.out.println("Your characters name is now "+ character.getName());
        keyboard.nextLine();
        menuState = "modifyCharacter";
    }

    /**
     * Cette méthode permet de changer la vie du personnage.
     * @param character
     */
    public void changeCharacterHealth(Character character) {
        System.out.println("Type the new health of your character between "+character.getMinHealth()+" and "+character.getMaxHealth());
        System.out.println("If you chose something other than "+character.getMinHealth()+" you are weak...");
        int userEntryInt = Utilities.toIntIfValid(keyboard.nextLine());
        if (userEntryInt <= character.getMaxHealth() && userEntryInt >= character.getMinHealth() ) {
            character.setLifeLevel(userEntryInt);
            System.out.println("Your characters health is now "+ character.getLifeLevel());
            keyboard.nextLine();
            menuState = "modifyCharacter";
        }
    }

    /**
     * Cette méthode permet de changer la force du personnage.
     * @param character
     */
    public void changeCharacterStrength(Character character) {
        System.out.println("Type the new strength of your character between "+character.getMinStrength()+" and "+character.getMaxStrength());
        System.out.println("If you chose something other than "+character.getMinStrength()+" you are a cheater...");
        int userEntryInt = Utilities.toIntIfValid(keyboard.nextLine());
        if (userEntryInt <= character.getMaxStrength() && userEntryInt >= character.getMinStrength() ) {
            character.setAttackStrength(userEntryInt);
            System.out.println("Your characters strength is now " + character.getAttackStrength());
            keyboard.nextLine();
            menuState = "modifyCharacter";
        }
    }

    /**
     * Cette méthode permet d'attendre une action du joueur avant d'exécuter la suite du logiciel.
     */
    public void waitForPlayer(){
        keyboard.nextLine();
    }

    /**
     * Cette méthode force le joueur à rentrer une réponse oui ou non.
     * @return oui ou non
     */
    public String yesOrNo(){
        String key = "";
        while (!key.equals("y") && !key.equals("n")) {
            System.out.println("You must enter 'y' or 'n'.");
            key = keyboard.nextLine();
        }
        return key;
    }

    /**
     * Cette méthode propose plusieurs choix au joueur au début de chacun de ses tours.
     * @return le choix du joueur
     */
    public int nextAction() {
        int choice = -1;
        System.out.println("What do you want to do next ?");
        System.out.println("1 - Throw the dice");
        System.out.println("2 - See your informations");
        System.out.println("3 - Use an item.");
        choice = Utilities.toIntIfValid(keyboard.nextLine());
        while (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("You must enter a valid choice.");
            choice = Utilities.toIntIfValid(keyboard.nextLine());
        }
        return choice;
    }

    /**
     * Cette méthode permet l'utilisation d'objets dans l'inventaire.
     * @param character
     */
    public void useItem(Character character) {
        ArrayList<Bonus> satchel = character.getSatchel();
        if (!satchel.isEmpty()){
            System.out.println("Which one do you want to use ?");
            System.out.println("0 - none");
            showInventory(satchel);
            int userChoice = chooseFromInventory(satchel);
            if (userChoice != 0) {
                Bonus bonus = satchel.get(userChoice-1);
                if (bonus instanceof Potion) {
                    if (character.getLifeLevel() < character.getMaxHealth()) {
                        System.out.println("You drink it and gain some energy.");
                        character.setLifeLevel(character.getLifeLevel() + ((Potion) bonus).getHealthRecovery());

                        if (character.getLifeLevel() > character.getMaxHealth()) {
                            character.setLifeLevel(character.getMaxHealth());
                            System.out.println("You got your energy to the max!");
                        }
                        System.out.println("Your life level is now " + character.getLifeLevel());
                        satchel.remove(userChoice-1);

                    } else {
                        System.out.println("You're feeling great, you don't need to drink this.");
                    }
                } else {
                    System.out.println("You don't know how to use that.");
                }
            }
        } else {
            System.out.println("Your satchel is empty.");
        }
    }

    /**
     * Cette méthode affiche le contenu d'une liste de Bonus
     * @param inventory liste d'objets Bonus
     */
    public void showInventory(ArrayList<Bonus> inventory) {
        for (int i= 0; i < inventory.size(); i++) {
            System.out.println((i+1)+" - "+inventory.get(i).getName());
        }
    }

    /**
     * Cette classe permet au joueur de choisir un objet parmi une liste
     * @param inventory
     * @return le choix du joueur.
     */
    public int chooseFromInventory(ArrayList<Bonus> inventory) {
        String userEntry;
        int userChoice = -1;
        while(userChoice < 0 || userChoice > inventory.size()) {
            userEntry  = userEntry();
            userChoice = Utilities.toIntIfValid(userEntry);
        }
        return userChoice;
    }

    /**
     * @return une chaîne de caractère entrée par le joueur     */
    public String userEntry(){
        return keyboard.nextLine();
    }

}
