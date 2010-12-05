/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containerloading;

import Model.*;
import java.util.AbstractList;
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
        // TODO code application logic here
        Container myContainer = new Container(new Vector3d(100, 100, 100));
        myContainer.spMatrix[0][0][0] = true;
        myContainer.spMatrix[0][0][1] = true;
        myContainer.spMatrix[0][0][2] = true;
        myContainer.spMatrix[0][0][3] = true;
        myContainer.spMatrix[0][0][4] = true;
        //myContainer.spMatrix[0][0][1] = true;
        Box myBox = new Box(new Vector3d(2, 2, 2), true, true, true, 1);
        Vector3d pos = new Vector3d(0, 0, 8);
        Vector3d endPos = myContainer.FallBox(myBox, pos);
        //System.out.println("Box placed successfully at (" + endPos.x + "," + endPos.y + "," + endPos.z + ")\n\n");
        //Testes();


        CasoTeste1();
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

    private static void CasoTesteOtavio() {

        Container testContainer = new Container(new Vector3d(10, 10, 10));
        Box testBox = new Box(new Vector3d(1, 1, 1), true, true, true, 1);
        List<Box> testListBox = new ArrayList<Box>();

        for (int i = 0; i < 1000; i++) {
            testListBox.add(testBox);
        }
        testBox = new Box(new Vector3d(2, 2, 2), true, true, true, 2);
        for (int i = 0; i < 1000; i++) {
            testListBox.add(testBox);
        }
        testBox = new Box(new Vector3d(3, 3, 3), true, true, true, 3);
        for (int i = 0; i < 1000; i++) {
            testListBox.add(testBox);
        }

        try {
            HeuristicSearch hSearch = new HeuristicSearch(testListBox, testContainer, 3);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            System.out.println("Total de caixas 1x1x1: " + hSearch.getNumeroDeCaixas(new Vector3d(60, 40, 32)));
            System.out.println("Total de caixas 2x2x2: " + hSearch.getNumeroDeCaixas(new Vector3d(98, 75, 55)));
            System.out.println("Total de caixas 3x3x3: " + hSearch.getNumeroDeCaixas(new Vector3d(60, 59, 39)));
            DesenhaContainer(hSearch.getContainer());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void CasoTeste1() {

        InputReader reader = new InputReader(FilesPath + "thpack1.txt");
        reader.readInput(50);

        Container myContainer = reader.getContainer();
        List<Box> lstBoxesOutside = reader.getBoxes();
        int boxTypes = reader.getBoxTypes();

        try {
            HeuristicSearch hSearch = new HeuristicSearch(lstBoxesOutside, myContainer, boxTypes);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            System.out.println("Total de caixas 60x40x32: " + hSearch.getNumeroDeCaixas(new Vector3d(60, 40, 32)));
            System.out.println("Total de caixas 98x75x55: " + hSearch.getNumeroDeCaixas(new Vector3d(98, 75, 55)));
            System.out.println("Total de caixas 60x59x39: " + hSearch.getNumeroDeCaixas(new Vector3d(60, 59, 39)));
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
