/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containerloading;

import Model.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author otavio_zabaleta
 */
public class Main {

    //files: thpack 1..7
    private final static String FilesPath = "C:\\Ofiles\\";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Testes();
        //Testes2();
        CasoTesteOtavio();
        //Testes2();
        CasoTesteOtavio_2();
    }

    private static void Testes() {
        Container testContainer = new Container(new Vector3d(10, 8, 5));
        Box testBox = new Box(new Vector3d(6, 4, 3), true, true, true, 1);
        Box tempBox;
        Vector3d lastBoxInserted = new Vector3d(0, 0, 0);

        //insere a primeira caixa
        tempBox = testContainer.fitsIn(testBox, lastBoxInserted);
        if (tempBox != null);
        {
            if (testContainer.insertBox(tempBox)) {
                System.out.println("Caixa 1 inserida com sucesso.");
            }
        }

        lastBoxInserted = new Vector3d(
                tempBox.relativeCoordenates.x + tempBox.relativeDimensions.x,
                tempBox.relativeCoordenates.y,
                tempBox.relativeCoordenates.z);
        //insere segunda caixa
        tempBox = testContainer.fitsIn(testBox, lastBoxInserted);
        if (tempBox != null);
        {
            if (testContainer.insertBox(tempBox)) {
                System.out.println("Caixa 2 inserida com sucesso.");
            }
        }

        lastBoxInserted = new Vector3d(
                tempBox.relativeCoordenates.x + tempBox.relativeDimensions.x,
                tempBox.relativeCoordenates.y,
                tempBox.relativeCoordenates.z);
        //insere a terceira caixa
        tempBox = testContainer.fitsIn(testBox, lastBoxInserted);
        if (tempBox != null);
        {
            if (testContainer.insertBox(tempBox)) {
                System.out.println("Caixa 3 inserida com sucesso.");
            }
        }

        lastBoxInserted = new Vector3d(
                tempBox.relativeCoordenates.x + tempBox.relativeDimensions.x,
                tempBox.relativeCoordenates.y,
                tempBox.relativeCoordenates.z);

        DesenhaContainer(testContainer);

    }

    private static void Testes2() {
        Box testBox1 = new Box(new Vector3d(1, 1, 1), true, true, true, 1);
        Box testBox2 = new Box(new Vector3d(2, 2, 2), true, true, true, 2);
        Box testBox3 = new Box(new Vector3d(3, 3, 3), true, true, true, 3);
        List<Box> testList = new ArrayList<Box>();

        testList.add(testBox1);//1
        testList.add(testBox2);//8
        testList.add(testBox2);//8
        testList.add(testBox1);//1
        testList.add(testBox3);//27
        testList.add(testBox2);//8
        testList.add(testBox1);//1
        testList.add(testBox3);//27


        HeuristicSearch hSearch = new HeuristicSearch();
        Box resultBox = hSearch.melhorCaixa(testList);
        resultBox = hSearch.melhorCaixa(testList);


    }

    private static void CasoTesteOtavio() {

        Container testContainer = new Container(new Vector3d(10, 10, 10));
        List<Box> testListBox = new ArrayList<Box>();
        List<Box> listForTabu = new ArrayList<Box>(3);

        Box testBox = new Box(new Vector3d(1, 1, 1), true, true, true, 1);
//        listForTabu.add(testBox);
//        for (int i = 0; i < 1000; i++) {
//            testListBox.add(testBox);
//        }
        testBox = new Box(new Vector3d(2, 2, 2), true, true, true, 2);
        listForTabu.add(testBox);
        for (int i = 0; i < 1000; i++) {
            testListBox.add(testBox);
        }
//        testBox = new Box(new Vector3d(3, 3, 3), true, true, true, 3);
//        listForTabu.add(testBox);
//        for (int i = 0; i < 1000; i++) {
//            testListBox.add(testBox);
//        }
        try {
            HeuristicSearch hSearch = new HeuristicSearch(testListBox, testContainer, listForTabu);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            System.out.println("Total de caixas 1x1x1: " + hSearch.getNumeroDeCaixas(new Vector3d(1, 1, 1)));
            System.out.println("Total de caixas 2x2x2: " + hSearch.getNumeroDeCaixas(new Vector3d(2, 2, 2)));
            System.out.println("Total de caixas 3x3x3: " + hSearch.getNumeroDeCaixas(new Vector3d(3, 3, 3)));
            DesenhaContainer(hSearch.getContainer());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void CasoTesteOtavio_2() {

        InputReader reader = new InputReader("//home//otavio//temp//" + "br1.txt");
        reader.readInputBR(50);

        Container myContainer = reader.getContainer();
        List<Box> lstBoxesOutside = reader.getBoxes();
        List<Box> lstTypeBoxes = reader.getTypeBoxes();
        int containervol = myContainer.getX() * myContainer.getY() * myContainer.getZ();
        System.out.println("Container Volume: " + containervol);
        System.out.println("Container: (" + myContainer.getX() + "," + myContainer.getY() + "," + myContainer.getZ() + ")");
        System.out.println("nº de caixas na lista: " + lstBoxesOutside.size());
        System.out.println("nº de tipos de caixas na lista: " + lstTypeBoxes.size());

        try {
            HeuristicSearch hSearch = new HeuristicSearch(lstBoxesOutside, myContainer, lstTypeBoxes);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            System.out.println("Total de caixas 20x20x20: " + hSearch.getNumeroDeCaixas(new Vector3d(20, 20, 20)));
            System.out.println("Total de caixas 30x30x30: " + hSearch.getNumeroDeCaixas(new Vector3d(30, 30, 30)));
            System.out.println("Total de caixas 40x40x40: " + hSearch.getNumeroDeCaixas(new Vector3d(40, 40, 40)));
            if (hSearch.getNumeroDeCaixas(new Vector3d(1, 1, 1)) > 0) {
                System.out.println("Total de caixas 1x1x1: " + hSearch.getNumeroDeCaixas(new Vector3d(1, 1, 1)));
            }

            DesenhaContainer(hSearch.getContainer());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void CasoTeste1() {

        InputReader reader = new InputReader("//home//otavio//temp//" + "br1.txt");
        reader.readInput(50);

        Container myContainer = reader.getContainer();
        List<Box> lstBoxesOutside = reader.getBoxes();
        List<Box> lstTypeBoxes = reader.getTypeBoxes();
        int containervol = myContainer.getX() * myContainer.getY() * myContainer.getZ();
        System.out.println("Container Volume: " + containervol);
        System.out.println("nº de caixas na lista: " + lstBoxesOutside.size());
        System.out.println("nº de tipos de caixas na lista: " + lstTypeBoxes.size());

        try {
            HeuristicSearch hSearch = new HeuristicSearch(lstBoxesOutside, myContainer, lstTypeBoxes);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            System.out.println("Total de caixas 20x20x20: " + hSearch.getNumeroDeCaixas(new Vector3d(20, 20, 20)));
            System.out.println("Total de caixas 30x30x30: " + hSearch.getNumeroDeCaixas(new Vector3d(30, 30, 30)));
            System.out.println("Total de caixas 40x40x40: " + hSearch.getNumeroDeCaixas(new Vector3d(40, 40, 40)));
            if (hSearch.getNumeroDeCaixas(new Vector3d(1, 1, 1)) > 0) {
                System.out.println("Total de caixas 1x1x1: " + hSearch.getNumeroDeCaixas(new Vector3d(1, 1, 1)));
            }

            DesenhaContainer(hSearch.getContainer());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void DesenhaContainer(Container _ctn) {
        if (_ctn.getX() <= 50 && _ctn.getY() <= 50 && _ctn.getZ() <= 50) {
            System.out.print("Desenhando comada Z = 1\n");
            for (int i = 0; i < _ctn.getX(); i++) {
                for (int j = 0; j < _ctn.getY(); j++) {
                    System.out.print(_ctn.spMatrix[i][j][0] == true ? 1 : 0);
                }
                System.out.print("\n");
            }
            System.out.print("Desenhando comada Z = 2\n");
            for (int i = 0; i < _ctn.getX(); i++) {
                for (int j = 0; j < _ctn.getY(); j++) {
                    System.out.print(_ctn.spMatrix[i][j][1] == true ? 1 : 0);
                }
                System.out.print("\n");
            }
            System.out.print("Desenhando comada X = 1\n");
            for (int j = _ctn.getZ() - 1; j >= 0; j--) {
                for (int i = 0; i < _ctn.getY(); i++) {
                    System.out.print(_ctn.spMatrix[0][i][j] == true ? 1 : 0);
                }
                System.out.print("\n");
            }
            System.out.print("Desenhando comada X = 2\n");
            for (int j = _ctn.getZ() - 1; j >= 0; j--) {
                for (int i = 0; i < _ctn.getY(); i++) {
                    System.out.print(_ctn.spMatrix[1][i][j] == true ? 1 : 0);
                }
                System.out.print("\n");
            }
        } else {
            System.out.println("Container grande demais para ser desenhado.");
        }
    }
}
