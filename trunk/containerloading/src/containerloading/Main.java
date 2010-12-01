/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package containerloading;

import Model.*;
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
        Container myContainer = new Container(new Vector3d(100,100,100));
        myContainer.spMatrix[0][0][0] = true;
        myContainer.spMatrix[0][0][1] = true;
        myContainer.spMatrix[0][0][2] = true;
        myContainer.spMatrix[0][0][3] = true;
        myContainer.spMatrix[0][0][4] = true;
        //myContainer.spMatrix[0][0][1] = true;
        Box myBox = new Box(new Vector3d(2,2,2), true, true, true);
        Vector3d pos = new Vector3d(0, 0, 8);
        Vector3d endPos = myContainer.FallBox(myBox, pos);
        System.out.println("Box placed successfully at (" + endPos.x + "," + endPos.y + "," + endPos.z + ")\n\n");
    }

}
