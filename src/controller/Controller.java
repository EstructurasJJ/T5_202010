package controller;

import java.util.ArrayList;
import java.util.Scanner;

import model.data_structures.Node;
import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	private Modelo modelo;
	private View view;
	public final static String RUTAGEOJASON = "./data/Comparendos_DEI_2018_Bogot�_D.C.geojson";
	public final static String JUEGUEMOS = "./data/Comparendos_DEI_2018_Bogot�_D.C_small.geojson";

	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		Comparendo[] arreglo = null;

		while( !fin )
		{
			view.printMenu();

			int option = lector.nextInt();

			int nHeap;
			String lista;

			int capIni;
			switch(option)
			{
			case 1:

				//Cargar el archivo
				
				System.out.println("Ingresar tama�o inicial de las tablas:");
				capIni = Integer.parseInt(lector.next());
				

				modelo.leerGeoJson(JUEGUEMOS, capIni);

				view.printMessage("Archivo GeoJSon Cargado");
				view.printMessage("Numero actual de comparendos " + modelo.darHashLineal().darDatos() + "\n----------");

				//Primer Comparendo

				//�ltimo Comparendo
				
				//Informaci�n adicional:
				
				System.out.println("N�mero de duplas en tabla hash LinearProbing: " + modelo.darHashLineal().darDatos());
				System.out.println("Tama�o final del arreglo  de tabla hash LinearProbing: " + modelo.darHashLineal().darCapacidad());
				System.out.println("Factor de carga en tabla hash LinearProbing: " + modelo.darHashLineal().darFactorCarga());
				System.out.println("N�mero de rehashes en tabla hash LinearProbing: " + modelo.darHashLineal().darRehashes() + "\n----------");

				break;

			case 2:
				
				System.out.println("Ingresar una fecha:");
				String fecha = lector.next();
				
				System.out.println("Ingresar una clase del vehiculo:");
				String clase = lector.next();
				
				System.out.println("Ingresar una infracci�n:");
				String infra = lector.next();
				
				modelo.busquedaLinealHash(fecha, clase, infra);

				break;

			case 3:
				
				ArrayList<Long> tablaTiempitos = modelo.analisisTablaLineal();
				
				long min = tablaTiempitos.get(0);
				long max = tablaTiempitos.get(0);
				long suma = 0;
				long total = tablaTiempitos.size();
				
				for (int i = 0; i < tablaTiempitos.size(); i++)
				{
					suma += tablaTiempitos.get(i);
					
					if(tablaTiempitos.get(i) < min)
					{
						min = tablaTiempitos.get(i);
					}
					if(tablaTiempitos.get(i) > max)
					{
						max = tablaTiempitos.get(i);
					}
				}
				
				double promedio = (int) suma/total;
				
				System.out.println("El m�ximo tiempo en obtener un dato (milisegundos) es: " + max);
				System.out.println("El m�nimo tiempo en obtener un dato (milisegundos) es: " + min);
				System.out.println("El tiempo promedio en obtener un dato (milisegundos) es:" + promedio);
				
				break;

			case 4:

				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;

				break;	

			default: 

				view.printMessage("--------- \n Opci�n Invalida !! \n---------");

				break;

			}
		}

	}	
}
