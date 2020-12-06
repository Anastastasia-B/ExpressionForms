package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BTree tree = new BTree();
        int[] elements = {9, 2, -83, -4, 5, 56, 17, 4};
        for(int element : elements){
            tree.add(element);
        }
        System.out.print("\nПрямой обход: ");
        tree.forward(tree.getRoot());
        System.out.print("\nСимметричный обход: ");
        tree.symmetric(tree.getRoot());
        System.out.print("\nОбратный обход: ");
        tree.backward(tree.getRoot());
        tree.print();


        Scanner scan = new Scanner(System.in);

        boolean escape = false;
        while(!escape){
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("Меню:\nВывод на экран  -  нажмите 1\nПоиск  -  нажмите 2\n" +
                    "Удалить  -  нажмите 3\nЗавершить работу  - другое число");
            System.out.println("-----------------------------------------------------------------------------");
            switch (scan.nextInt()){
                case 1:
                    tree.print();
                    break;
                case 2:
                    System.out.println("\nВведите значение для поиска: ");
                    BTree.Node result = tree.search(scan.nextInt());
                    System.out.println(result == null ? "Ничего не найдено" : result);
                    break;
                case 3:
                    System.out.println("\nВведите значение для удаления: ");
                    System.out.println(tree.remove(scan.nextInt()) ? "Успешно удалено." : "Данного элемента нет в дереве.");
                    break;
                default:
                    escape = true;
            }
        }


    }
}
