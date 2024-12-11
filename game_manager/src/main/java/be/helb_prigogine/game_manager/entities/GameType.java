package be.helb_prigogine.game_manager.entities;

public enum GameType {//Si on change les enums, on doit aller dans la db supprimer la contrainte et écrire la nouvelle sinon erreur
    DUO,
    TRIO,
    SQUAD
}
