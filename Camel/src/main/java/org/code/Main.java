package org.code;

import org.code.components.Camel;
import org.code.components.Colours;
import org.code.components.Game;
import org.code.components.GameObject;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //test0();
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //pysio_test();
        //test6();
        game1();
    }

    private static void game1(){
        Game game = new Game(2);

        game.setCamel(Colours.white, 12);
        game.setCamel(Colours.yellow, 13);
        game.setCamel(Colours.orange, 11);
        game.setCamel(Colours.green, 14);
        game.setCamel(Colours.blue, 13);

        game.makePredictions();

        game.setSpecialField(15, -1);
        game.setSpecialField(15, -1);

        game.makePredictions();

        //moves

        game.releaseAllSpecialFields();
        game.releaseAllCamels();
    }

    private static void test6(){
        Game game = new Game(2);

        game.setCamel(Colours.white, 15);
        game.setCamel(Colours.yellow, 13);
        game.setCamel(Colours.orange, 11);
        game.setCamel(Colours.green, 14);
        game.setCamel(Colours.blue, 13);

        game.setSpecialField(15, -1);

        /*game.moveCamel(Colours.yellow, 1);
        game.moveCamel(Colours.white, 1);
        game.moveCamel(Colours.orange, 2);
        game.moveCamel(Colours.green, 2);
        game.moveCamel(Colours.blue, 3);*/

        game.makePredictions();
    }

    private static void pysio_test(){
        Game game = new Game(2);

        game.setCamel(Colours.white, 1);
        game.setCamel(Colours.yellow, 2);
        game.setCamel(Colours.orange, 2);
        game.setCamel(Colours.green, 3);
        game.setCamel(Colours.blue, 1);

        game.setSpecialField(5, -1);

        game.moveCamel(Colours.yellow, 1);
        game.moveCamel(Colours.white, 1);
        game.moveCamel(Colours.orange, 2);
        game.moveCamel(Colours.green, 2);
        game.moveCamel(Colours.blue, 3);

        System.out.println(game.getWinner());

        //game.makePredictions();

        game.releaseAllSpecialFields();
    }

    public static void test5(){
        Game game = new Game(2);

        game.setCamel(Colours.white, 1);
        game.setCamel(Colours.yellow, 1);
        game.setCamel(Colours.orange, 1);
        game.setCamel(Colours.green, 1);
        game.setCamel(Colours.blue, 1);

        game.setSpecialField(2, 1);
        game.setSpecialField(4, -1);

        game.makePredictions();
    }

    public static void test4(){
        Game game = new Game(2);

        game.setCamel(Colours.white, 1);
        game.setCamel(Colours.yellow, 1);
        game.setCamel(Colours.orange, 1);
        game.setCamel(Colours.green, 1);
        game.setCamel(Colours.blue, 1);

        game.makePredictions();
    }

    public static void test3(){
        Camel camel1 = new Camel(Colours.white);
        Camel camel2 = new Camel(Colours.green);
        camel1.setUp(camel2);
        camel2.setDown(camel1);

        System.out.println(camel1);
        System.out.println(camel2);

        Camel camel = camel1;
        System.out.println(camel);
        camel = camel.getUp();
        System.out.println(camel);

        System.out.println(camel1);
        System.out.println(camel2);
    }

    public static void test2(){
        /*Camel camel1 = new Camel(Colours.white);
        camel1.setField(10);
        System.out.println(camel1);
        System.out.println(camel1.getField());

        ArrayList<Camel> camels = new ArrayList<>();
        camels.add(camel1);

        Camel camel2 = camel1.copy();
        System.out.println(camel2);
        System.out.println(camel2.getField());

        camel1.setField(20);

        camel1 = camel2;
        System.out.println(camel1);
        System.out.println(camel1.getField());

        //list remembers the first element, it didn't swap them
        //thus, it cannot be used
        System.out.println(camels.get(0));
        System.out.println(camels.get(0).getField());*/
    }
    public static void test1(){
        Game game = new Game(2);

        game.setCamel(Colours.white, 1);
        game.setCamel(Colours.yellow, 1);
        game.setCamel(Colours.orange, 1);
        game.setCamel(Colours.green, 1);
        game.setCamel(Colours.blue, 2);

        game.moveCamel(Colours.green, 1);
        game.moveCamel(Colours.orange, 1);
        game.moveCamel(Colours.yellow, 1);
        game.moveCamel(Colours.white, 1);

        game.makePredictions();
    }

    public static void test0(){
        System.out.println("Hello world!");
        GameObject[] board;
        board = new GameObject[20];
        System.out.println(board[19]);
        System.out.println();

        Camel camel1 = new Camel(Colours.blue);
        Camel camel2 = new Camel(Colours.green);

        System.out.println(camel2);
        camel1.setUp(camel2);
        camel2 = camel1.getUp();
        System.out.println(camel2);
        System.out.println(camel1.getUp());
        System.out.println(camel1);

        System.out.println();
        ArrayList<Integer> l1 = new ArrayList<>();
        l1.add(1);
        l1.add(2);
        System.out.println(l1);
        ArrayList<Integer> l2 = (ArrayList<Integer>) l1.clone();
        System.out.println(l2);
        l2.remove(1);
        System.out.println(l2);
        System.out.println(l1);
    }
}