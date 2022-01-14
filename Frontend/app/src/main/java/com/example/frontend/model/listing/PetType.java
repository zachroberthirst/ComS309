package com.example.frontend.model.listing;

public enum PetType {
    DOG,
    CAT,
    BIRD,
    REPTILE,
    RABBIT,
    FISH,
    FERRET;

    public static int getVal(PetType type){
        switch (type){
            case DOG:
                return 0;
            case CAT:
                return 1;
            case BIRD:
                return 2;
            case REPTILE:
                return 3;
            case RABBIT:
                return 4;
            case FISH:
                return 5;
            case FERRET:
                return 6;
        }
        return 0;
    }
}
