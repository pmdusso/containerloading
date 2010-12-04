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
        Box myBox = new Box(new Vector3d(2, 2, 2), true, true, true);
        Vector3d pos = new Vector3d(0, 0, 8);
        Vector3d endPos = myContainer.FallBox(myBox, pos);
        //System.out.println("Box placed successfully at (" + endPos.x + "," + endPos.y + "," + endPos.z + ")\n\n");
        Testes();


        //CasoTeste1();
    }

    private static void Testes() {
        Container testContainer = new Container(new Vector3d(10, 8, 5));
        Box testBox = new Box(new Vector3d(6, 4, 3), true, true, true);
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

    private static void CasoTeste1() {

        Container myContainer = new Container(new Vector3d(100, 80, 50));

        Box type1 = new Box(new Vector3d(60, 40, 32), true, true, true);
        Box type2 = new Box(new Vector3d(98, 75, 55), true, true, true);
        Box type3 = new Box(new Vector3d(60, 59, 39), true, true, true);

        List<Box> lstBoxesOutside = new ArrayList<Box>();

        for (int i = 0; i < 64; i++) {
            lstBoxesOutside.add(type1);
        }
        for (int i = 0; i < 40; i++) {
            lstBoxesOutside.add(type2);
        }
        for (int i = 0; i < 64; i++) {
            lstBoxesOutside.add(type3);
        }

        try {
            HeuristicSearch hSearch = new HeuristicSearch(lstBoxesOutside, myContainer, 3);
            System.out.println("Volume total dentro do container: " + hSearch.Resolve());
            DesenhaContainer(hSearch.getContainer());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void DesenhaContainer(Container _ctn) {
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
    }
}
