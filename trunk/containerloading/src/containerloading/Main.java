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
    private final static String FilesPathLinux = "//home//otavio//temp//brs//";
   
    public static void main(String[] args) {

        ContainerLoadingLinux("br1.txt");
        ContainerLoadingLinux("br2.txt");
        ContainerLoadingLinux("br3.txt");
        ContainerLoadingLinux("br4.txt");
        ContainerLoadingLinux("br5.txt");
        ContainerLoadingLinux("br6.txt");
        ContainerLoadingLinux("br7.txt");
        ContainerLoadingLinux("br8.txt");
        ContainerLoadingLinux("br9.txt");
        ContainerLoadingLinux("br10.txt");     

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
        System.out.println("Container size: " + myContainer.getX() + " " + myContainer.getY() + " " + myContainer.getZ());
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

    private static void ContainerLoadingLinux(String _file) {

        System.out.println("===============================================");
        System.out.println(_file.toUpperCase());
        InputReader reader = new InputReader(FilesPathLinux + _file);
        reader.readInput(50);

        Container myContainer = reader.getContainer();
        List<Box> lstBoxesOutside = reader.getBoxes();
        List<Box> lstTypeBoxes = reader.getTypeBoxes();
        StringBuilder sb;

        System.out.println("Container Volume: " + myContainer.getVolume());
        System.out.println("Container size: " + myContainer.getX() + " " + myContainer.getY() + " " + myContainer.getZ());
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

}
