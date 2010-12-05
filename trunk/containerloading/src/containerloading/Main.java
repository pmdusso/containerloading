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
    private final static String FilesPathWindows = "C:\\Ofiles\\";
    private final static String FilesPathLinux = "//home//otavio//temp//";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //BitWiseTests();
        //Testes();
        //Testes2();
        //CasoTesteOtavio();
        //Testes2();
        //CasoTesteOtavio_2();
        //CasoTesteRotacionaCaixaDaLista();

        ContainerLoading("br1.txt");
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

        listForTabu.add(new Box(new Vector3d(1, 1, 1), true, true, true, 1));
        for (int i = 0; i < 1000; i++) {
            testListBox.add(new Box(new Vector3d(1, 1, 1), true, true, true, 1));
        }
        listForTabu.add(new Box(new Vector3d(2, 2, 2), true, true, true, 2));
        for (int i = 0; i < 1000; i++) {
            testListBox.add(new Box(new Vector3d(2, 2, 2), true, true, true, 2));
        }
        listForTabu.add(new Box(new Vector3d(3, 3, 3), true, true, true, 3));
        for (int i = 0; i < 1000; i++) {
            testListBox.add(new Box(new Vector3d(3, 3, 3), true, true, true, 3));
        }
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

    private static void CasoTesteRotacionaCaixaDaLista() {
        Container testContainer = new Container(new Vector3d(10, 10, 10));
        List<Box> testListBox = new ArrayList<Box>();
        Box testBox;
        Box boxToRotate;

        for (int i = 0; i < 10; i++) {
            testBox = new Box(new Vector3d(1, 2, 3), true, true, true, 1);
            testListBox.add(testBox);
        }
        System.out.println(testListBox.size());
        for (Box box : testListBox) {
            System.out.println(box.relativeDimensions.x + "x"
                    + box.relativeDimensions.y + "x"
                    + box.relativeDimensions.z);
        }
        boxToRotate = testListBox.remove(5);
        System.out.println(testListBox.size());

        System.out.println("Rotou uma caixa.");
        boxToRotate.rotate();
        System.out.println(boxToRotate.relativeDimensions.x + "x"
                + boxToRotate.relativeDimensions.y + "x"
                + boxToRotate.relativeDimensions.z);

        testListBox.add(boxToRotate);
        System.out.println(testListBox.size());
        for (Box box : testListBox) {
            System.out.println(box.relativeDimensions.x + "x"
                    + box.relativeDimensions.y + "x"
                    + box.relativeDimensions.z);
        }



    }

    private static void CasoTesteOtavio_2() {

        InputReader reader = new InputReader(FilesPathWindows + "br1.txt");
        reader.readInputBR(50);

        Container myContainer = reader.getContainer();
        List<Box> lstBoxesOutside = reader.getBoxes();
        List<Box> lstTypeBoxes = reader.getTypeBoxes();
        System.out.println("Container Volume: " + myContainer.getVolume());
        System.out.println("Container: (" + myContainer.getX() + "," + myContainer.getY() + "," + myContainer.getZ() + ")");
        System.out.println("nº de caixas na lista: " + lstBoxesOutside.size());
        System.out.println("nº de tipos de caixas na lista: " + lstTypeBoxes.size());
        System.out.println(
                "Tipo: " + lstTypeBoxes.get(0).getBoxType() + " Tam: "
                + lstTypeBoxes.get(0).relativeDimensions.x + " x "
                + lstTypeBoxes.get(0).relativeDimensions.y + " x "
                + lstTypeBoxes.get(0).relativeDimensions.z + " x "
                + "Volume: " + lstTypeBoxes.get(0).getVolume());

        System.out.println(
                "Tipo: " + lstTypeBoxes.get(1).getBoxType() + " Tam: "
                + lstTypeBoxes.get(1).relativeDimensions.x + " x "
                + lstTypeBoxes.get(1).relativeDimensions.y + " x "
                + lstTypeBoxes.get(1).relativeDimensions.z + " x "
                + "Volume: " + lstTypeBoxes.get(1).getVolume());
        System.out.println(
                "Tipo: " + lstTypeBoxes.get(2).getBoxType() + " Tam: "
                + lstTypeBoxes.get(2).relativeDimensions.x + " x "
                + lstTypeBoxes.get(2).relativeDimensions.y + " x "
                + lstTypeBoxes.get(2).relativeDimensions.z + " x "
                + "Volume: " + lstTypeBoxes.get(2).getVolume());
        System.out.println("Processando");
        try {
            HeuristicSearch hSearch = new HeuristicSearch(lstBoxesOutside, myContainer, lstTypeBoxes);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            System.out.println("Total de caixas 40, 32, 64: " + hSearch.getNumeroDeCaixas(new Vector3d(lstTypeBoxes.get(0).relativeDimensions.x, lstTypeBoxes.get(0).relativeDimensions.y, lstTypeBoxes.get(0).relativeDimensions.z)));
            System.out.println("Total de caixas 75, 55, 40: " + hSearch.getNumeroDeCaixas(new Vector3d(lstTypeBoxes.get(1).relativeDimensions.x, lstTypeBoxes.get(1).relativeDimensions.y, lstTypeBoxes.get(1).relativeDimensions.z)));
            System.out.println("Total de caixas 59, 39, 64: " + hSearch.getNumeroDeCaixas(new Vector3d(lstTypeBoxes.get(2).relativeDimensions.x, lstTypeBoxes.get(2).relativeDimensions.y, lstTypeBoxes.get(2).relativeDimensions.z)));
            if (hSearch.getNumeroDeCaixas(new Vector3d(1, 1, 1)) > 0) {
                System.out.println("Total de caixas 1x1x1: " + hSearch.getNumeroDeCaixas(new Vector3d(1, 1, 1)));
            }

            DesenhaContainer(hSearch.getContainer());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void ContainerLoading(String _file) {

        System.out.println("===============================================");
        System.out.println(_file.toUpperCase());
        InputReader reader = new InputReader(FilesPathWindows + _file);
        reader.readInput(50);

        Container myContainer = reader.getContainer();
        List<Box> lstBoxesOutside = reader.getBoxes();
        List<Box> lstTypeBoxes = reader.getTypeBoxes();
        StringBuilder sb;

        System.out.println("Container Volume: " + myContainer.getVolume());
        System.out.println("Nº de caixas na lista: " + lstBoxesOutside.size());
        System.out.println("Nº de tipos de caixas na lista: " + lstTypeBoxes.size());

        try {
            HeuristicSearch hSearch = new HeuristicSearch(lstBoxesOutside, myContainer, lstTypeBoxes);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            for (Box box : lstTypeBoxes) {
                sb = new StringBuilder();
                sb.append("Total de caixas ");
                sb.append(box.relativeDimensions.x).append("x");
                sb.append(box.relativeDimensions.y).append("x");
                sb.append(box.relativeDimensions.z).append(": ");
                sb.append(hSearch.getNumeroDeCaixas(new Vector3d(
                        box.relativeDimensions.x,
                        box.relativeDimensions.y,
                        box.relativeDimensions.z)));
                System.out.println(sb.toString());
            }
            DesenhaContainer(hSearch.getContainer());
            System.out.println("===============================================");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void BitWiseTests() {
        Box testBox = new Box(new Vector3d(1, 2, 3), true, true, true, 0);
        System.out.println(testBox.relativeDimensions.x + "x"
                + testBox.relativeDimensions.y + "x"
                + testBox.relativeDimensions.z);
        testBox.rotate();
        System.out.println(testBox.relativeDimensions.x + "x"
                + testBox.relativeDimensions.y + "x"
                + testBox.relativeDimensions.z);
        testBox.rotate();
        System.out.println(testBox.relativeDimensions.x + "x"
                + testBox.relativeDimensions.y + "x"
                + testBox.relativeDimensions.z);
        testBox.rotate();
        System.out.println(testBox.relativeDimensions.x + "x"
                + testBox.relativeDimensions.y + "x"
                + testBox.relativeDimensions.z);
        testBox.rotate();
        System.out.println(testBox.relativeDimensions.x + "x"
                + testBox.relativeDimensions.y + "x"
                + testBox.relativeDimensions.z);
        testBox.rotate();
        System.out.println(testBox.relativeDimensions.x + "x"
                + testBox.relativeDimensions.y + "x"
                + testBox.relativeDimensions.z);
        testBox.rotate();
        System.out.println(testBox.relativeDimensions.x + "x"
                + testBox.relativeDimensions.y + "x"
                + testBox.relativeDimensions.z);


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
