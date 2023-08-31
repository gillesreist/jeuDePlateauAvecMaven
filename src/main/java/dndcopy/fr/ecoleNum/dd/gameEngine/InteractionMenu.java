package dndcopy.fr.ecoleNum.dd.gameEngine;

import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment.AttackEquipment;
import dndcopy.fr.ecoleNum.dd.utilities.Utilities;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cette classe permet au joueur de faire des choix lors d'une intéraction avec une case */

public class InteractionMenu {

    Scanner keyboard;

    public InteractionMenu(){
        keyboard = new Scanner(System.in);
    }

    public boolean yesOrNo(){
        String key = "";
        while (!key.equals("y") && !key.equals("n")) {
            System.out.println("You must enter 'y' or 'n'.");
            key = keyboard.nextLine();
        }
        return key.equals("y");
    }

    public String userEntry(){
        return keyboard.nextLine();
    }

    /**
     * Cette méthode permet de récupérer la force d'une arme de l'inventaire.
     * @param inventory inventaire du personnage
     * @return force de l'arme sélectionnée ou 0.
     */
    public int getAttackLevelFromInventory(ArrayList<AttackEquipment> inventory) {
        if (!inventory.isEmpty()){
            if (inventory.size() == 1) {
                System.out.println("Do you want to use your "+inventory.get(0).getName());
                if (yesOrNo()) {
                    return inventory.get(0).getAttackLevel();
                }
            } else {
                System.out.println("Which one do you want to use to attack ?");
                System.out.println("0 - none");
                showInventory(inventory);
                String userEntry;
                int userChoice = chooseFromInventory(inventory);
                if (userChoice != 0) {
                    return inventory.get(userChoice-1).getAttackLevel();
                }
            }
        }
        return 0;
    }

    /**
     * Cette méthode permet d'enlever un objet de l'inventaire.
     * @param inventory
     */
    public void removeFromInventory(ArrayList<AttackEquipment> inventory) {
        System.out.println("Your backpack is full, what do you want to throw away ?");
        System.out.println("0 - nothing");
        showInventory(inventory);
        int userChoice = chooseFromInventory(inventory);
        if (userChoice != 0) {
            inventory.remove(userChoice-1);
        }
    }

    /**
     * Cette méthode affiche le contenu d'une liste de Bonus
     * @param inventory liste d'objets Bonus
     */
    public void showInventory(ArrayList<AttackEquipment> inventory) {
        for (int i= 0; i < inventory.size(); i++) {
            System.out.println((i+1)+" - "+inventory.get(i).getName());
        }
    }

    /**
     * Cette classe permet au joueur de choisir un objet parmi une liste
     * @param inventory
     * @return le choix du joueur.
     */
    public int chooseFromInventory(ArrayList<AttackEquipment> inventory) {
        String userEntry;
        int userChoice = -1;
        while(userChoice < 0 || userChoice > inventory.size()) {
            userEntry  = userEntry();
            userChoice = Utilities.toIntIfValid(userEntry);
        }
        return userChoice;
    }

}
