package br.com.mpabegg.containerloading;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.mpabegg.containerloading.entities.Solution;
import br.com.mpabegg.containerloading.grasp.GRASP;
import br.com.mpabegg.containerloading.reader.InputReader;

public class Main {

    private static SimpleDateFormat hourFormatter = new SimpleDateFormat("HH:mm:ss");
    private static final String START_TIME = "Hora de inicio: %s";
    private static final String END_TIME = "Hora de fim: %s";
    private static final String TOTAL_TIME = "Tempo gasto (hh:mm:ss): %s";
    private static final String SOLUTION_VALUE = "Razão = %s";

    public static void main(String[] args) {
	InputReader reader = null;
	if (args.length > 0) {
	    for (String path : args) {
		try {
		    reader = new InputReader(path);
		    reader.readInput(50);

		    System.out.println("Arquivo : " + path);

		    Long startTime = System.currentTimeMillis();
		    System.out.println(String.format(START_TIME, hourFormatter.format(new Date(startTime))));

		    GRASP grasp = new GRASP(reader.getContainer(), reader.getBoxes(), reader.getBoxTypes(), reader.getSeed());
		    Solution solution = grasp.solve();

		    Long endTime = System.currentTimeMillis();
		    System.out.println(String.format(END_TIME, hourFormatter.format(new Date(endTime))));

		    System.out.println(String.format(SOLUTION_VALUE, (((float) solution.getValue() / (float) solution.getContainer().getVolume()) * 100) + "%"));

		    System.out.println("--------");
		} catch (Exception e) {
		    throw new RuntimeException(e.getMessage());
		}
	    }
	} else {
	    System.out.println("Nenhuma instancia entrada.");
	}
    }
}
